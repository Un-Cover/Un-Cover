package com.CS499.btsmith.uncover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.CS499.btsmith.uncover.Data.Entry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class EntryPage extends ActionBarActivity {

    private double myLat;
    private double myLong;
    private Location thisSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Spinner uncoverSpinner = (Spinner) findViewById(R.id.uncoverSpinner);
        Spinner visibilitySpinner = (Spinner) findViewById(R.id.visibilitySpinner);

        Bundle imported = getIntent().getExtras();
        myLat = imported.getDouble("Latitude");
        myLong = imported.getDouble("Longitude");
        thisSpot = (Location) imported.getParcelable("Location");

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.distances,
                android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        uncoverSpinner.setAdapter(adapter1);
        visibilitySpinner.setAdapter(adapter1);

        //Publishing button
        Button publishButton = (Button) findViewById(R.id.publish_button);
        publishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText namespace = (EditText) findViewById(R.id.username);
                String username = namespace.getText().toString();

                EditText desc = (EditText) findViewById(R.id.descText);
                String description = desc.getText().toString();

                EditText contentEdit = (EditText) findViewById(R.id.contentText);
                String content = contentEdit.getText().toString();

                Entry entry = new Entry();
                entry.setName(username);
                entry.setDescription(description);
                entry.setContent(content);
                int[] curTime = getTimeOfPosting();
                String timeOfPosting = curTime[0] + "/" + curTime[1] + "/" + curTime[2] + "; "
                        + curTime[3] + ":" + curTime[4];
                entry.setTime(timeOfPosting);
                entry.initializeViews();

                Intent intent = new Intent(EntryPage.this, Map.class);

                Toast.makeText(EntryPage.this, ("Latitude: " + thisSpot.getLatitude() + " Longitude: " + thisSpot.getLongitude()), Toast.LENGTH_SHORT).show();
                ParseGeoPoint point = new ParseGeoPoint(myLat, myLong);
                entry.setPosition(point);
                entry.saveInBackground();
                startActivity(intent);

                desc.setText("");
                contentEdit.setText("");
            }
        }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void returnToDash(View view)
    {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public int[] getTimeOfPosting()
    {
        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
        // if no ids were returned, something is wrong. get out.
        if (ids.length == 0)
            System.exit(0);

        // begin output
        System.out.println("Current Time");

        // create a Pacific Standard Time time zone
        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

        // set up rules for Daylight Saving Time
        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        // create a GregorianCalendar with the Pacific Daylight time zone
        // and the current date and time
        Calendar calendar = new GregorianCalendar(pdt);
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        int[] time = new int[5];
        time[0] = calendar.get(Calendar.MONTH) + 1;
        time[1] = calendar.get(Calendar.DAY_OF_MONTH);
        time[2] = calendar.get(Calendar.YEAR);
        time[3] = calendar.get(Calendar.HOUR_OF_DAY) + 1;
        time[4] = calendar.get(Calendar.MINUTE);
        return time;
    }
}
