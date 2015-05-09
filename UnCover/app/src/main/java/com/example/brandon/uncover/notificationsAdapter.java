package com.example.brandon.uncover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.brandon.uncover.Data.Notification;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Brandon on 5/8/2015.
 */
public class notificationsAdapter extends ArrayAdapter<Notification>
{
    private List<Notification> entries;
    private Context context;

    public notificationsAdapter(Context context, List<Notification> values)
    {
        super(context, R.layout.notification_layout, values);
        this.context = context;
        entries = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.notification_layout, parent, false);

        //set notification owner and notification type
        TextView actionText = (TextView) rowView.findViewById(R.id.notificationType);
        actionText.setText(entries.get(position).getAction());

        TextView username = (TextView) rowView.findViewById(R.id.notificationUsername);
        actionText.setText(entries.get(position).getName());

        TextView date = (TextView) rowView.findViewById(R.id.notificationDate);
        date.setText(entries.get(position).getDate());

        //then set notification text
        TextView notificationText = (TextView) rowView.findViewById(R.id.notificationText);
        if(actionText.getText().equals("Like"))
        {
            notificationText.setText(username.getText() + " has liked your geocache!");
        }
        else if(actionText.getText().equals("Reply"))
        {
            notificationText.setText(username.getText() + " replied to your geocache..." + entries.get(position).getReply());
        }
        else
        {
            notificationText.setText("Error with notification, type mismatch. OOPS.");
        }

        return rowView;
    }

}
