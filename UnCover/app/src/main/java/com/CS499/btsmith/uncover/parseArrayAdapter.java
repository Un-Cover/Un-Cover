package com.CS499.btsmith.uncover;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import java.util.List;
import com.CS499.btsmith.uncover.Data.Entry;

/**
 * Created by Brandon on 5/4/2015.
 */
public class parseArrayAdapter extends ArrayAdapter<Entry>
{
    private List<Entry> entries;
    private Context context;

    public parseArrayAdapter(Context context, List<Entry> values)
    {
        super(context, R.layout.entry_rows, values);
        this.context = context;
        this.entries = values;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.entry_rows, parent, false);

        TextView descText = (TextView) rowView.findViewById(R.id.itemDescription);
        descText.setText(entries.get(position).getDescription());

        TextView tagsText = (TextView) rowView.findViewById(R.id.itemTags);
        //TODO fill in this section when tags are implemented

        TextView usernameText = (TextView) rowView.findViewById(R.id.itemUsername);
        usernameText.setText(entries.get(position).getName());

        TextView dateText = (TextView) rowView.findViewById(R.id.itemDate);
        dateText.setText(entries.get(position).getTime());

        TextView distanceText = (TextView) rowView.findViewById(R.id.itemDistance);
        //TODO fill this section when distance implemented

        //TODO add conditional for ImageButton for Uncoverable
        ImageButton uncover = (ImageButton) rowView.findViewById(R.id.uButton);
        uncover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You clicked!", Toast.LENGTH_SHORT).show();
                //instantiate fragment view

                new neocacheDetail();
            }
        });
        return rowView;
    }

    public void changeFragment()
    {

    }
}
