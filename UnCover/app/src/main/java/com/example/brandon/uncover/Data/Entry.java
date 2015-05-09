package com.example.brandon.uncover.Data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Brandon on 5/5/2015.
 */
@ParseClassName("Entry")
public class Entry extends ParseObject
{
    public Entry() {

    }

    public String getName() {
        return getString("Name");
    }

    public void setName(String name) {
        put("Name", name);
    }

    public String getDescription()
    {
        return getString("Description");
    }

    public void setDescription(String description)
    {
        put("Description", description);
    }

    public String getContent()
    {
        return getString("Content");
    }

    public void setContent(String content)
    {
        put("Content", content);
    }

    public String getTime()
    {
        return getString("TimeOfPosting");
    }
}
