package com.CS499.btsmith.uncover.Data;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
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

    public String getContent()
    {
        return getString("Content");
    }

    public ParseGeoPoint getPosition()
    {
        return getParseGeoPoint("Location");
    }

    public void setDescription(String description)
    {
        put("Description", description);
    }

    public void setContent(String content)
    {
        put("Content", content);
    }

    public String getTime()
    {
        return getString("TimeOfPosting");
    }

    public void setTime(String timeOfPosting)
    {
        put("TimeOfPosting", timeOfPosting);
    }

    public void setPosition(ParseGeoPoint location)
    {
        put("Location", location);
    }

    public Number getViews()
    {
        return getNumber("uncovers");
    }

    public void setViews(Number currentViews)
    {
        put("uncovers", (Number)((int)currentViews + 1));
    }

    public void initializeViews()
    {
        put("uncovers", (Number) 0);
    }
}
