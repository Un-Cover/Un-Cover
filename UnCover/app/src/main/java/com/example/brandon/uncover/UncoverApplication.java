package com.example.brandon.uncover;

import android.app.Application;

import com.example.brandon.uncover.Data.Entry;
import com.example.brandon.uncover.Data.Notification;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Brandon on 4/27/2015.
 */
public class UncoverApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Entry.class);
        ParseObject.registerSubclass(Notification.class);
        Parse.initialize(this, "XTW8ual1EVBTxq0FqlT0w68MNIT83UZZ0XshgHGF", "uWxZgWTn2RSyVmslCH76XdZhh9U4lhyjdVc9By69");
    }


}
