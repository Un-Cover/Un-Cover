package com.CS499.btsmith.uncover;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.*;
import java.util.List;
import com.CS499.btsmith.uncover.Data.Entry;
import com.parse.ParseGeoPoint;

/**
 * Created by Brandon on 5/4/2015.
 */
public class parseArrayAdapter extends ArrayAdapter<Entry>
{
    private List<Entry> entries;
    private Context context;
    private Location myLoc;
    private ParseGeoPoint myPoint;

    public parseArrayAdapter(Context context, List<Entry> values, Location myLoc)
    {
        super(context, R.layout.entry_rows, values);
        this.context = context;
        this.entries = values;
        this.myLoc = myLoc;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.entry_rows, parent, false);
        myPoint = new ParseGeoPoint(myLoc.getLatitude(), myLoc.getLongitude());

        TextView descText = (TextView) rowView.findViewById(R.id.itemDescription);
        descText.setText(entries.get(position).getDescription());

        TextView tagsText = (TextView) rowView.findViewById(R.id.itemTags);
        //TODO fill in this section when tags are implemented

        TextView usernameText = (TextView) rowView.findViewById(R.id.itemUsername);
        usernameText.setText(entries.get(position).getName());

        TextView dateText = (TextView) rowView.findViewById(R.id.itemDate);
        dateText.setText(entries.get(position).getTime());

        TextView distanceText = (TextView) rowView.findViewById(R.id.itemDistance);
        distanceText.setText(findDistance(myPoint, entries.get(position).getParseGeoPoint("Location")) + " mi");

        //TODO add conditional for ImageButton for Uncoverable
        ImageButton uncover = (ImageButton) rowView.findViewById(R.id.uButton);
        uncover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You clicked!", Toast.LENGTH_SHORT).show();

                //Build bundle to look at detailed_view activity using neocache's data
                Bundle bundle = new Bundle();
                bundle.putString("objectID", entries.get(position).getObjectId());
                bundle.putString("distance", findDistance(myPoint, entries.get(position).getParseGeoPoint("Location")));
                bundle.putParcelable("Location", myLoc);

                Intent intent = new Intent(context, detailed_view.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

    public String findDistance(ParseGeoPoint myLocation, ParseGeoPoint otherLocation)
    {
        String result = "0";
        double distance = myLocation.distanceInMilesTo(otherLocation);
        result = truncateDecimal(distance,2).toString();
        return result;
    }

    public BigDecimal truncateDecimal(double number, int limit)
    {
        if(number > 0)
        {
            BigDecimal result = new BigDecimal((String.valueOf(number)));
            return result.setScale(limit, BigDecimal.ROUND_FLOOR);
        }
        else
        {
            BigDecimal result = new BigDecimal(String.valueOf(number));
            return result.setScale(limit, BigDecimal.ROUND_CEILING);
        }
    }
}
