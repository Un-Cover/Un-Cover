package com.example.brandon.uncover;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.parse.ParseObject;

import java.util.List;
import com.example.brandon.uncover.Data.Entry;

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
    public View getView(final int position, View convertView, ViewGroup parent)
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

        return rowView;
    }
}
