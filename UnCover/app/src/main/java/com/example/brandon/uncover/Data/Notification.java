package com.example.brandon.uncover.Data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Brandon on 5/8/2015.
 */
@ParseClassName("Notification")
public class Notification extends ParseObject
{
    public Notification() {}

    public String getName()
    {
        return getString("Name");
    }

    public String getDate()
    {
        return getString("DateOccurred");
    }

    public String getAction()
    {
        return getString("Action");
    }

    public String getReply()
    {
        return getString("Reply");
    }

    public void setName(String name)
    {
        put("Name", name);
    }

    public void setDate(String date)
    {
        put("DateOccurred", date);
    }

    public void setAction(String action)
    {
        put("Action", action);
    }

    public void setReply(String reply)
    {
        put("Reply", reply);
    }
}
