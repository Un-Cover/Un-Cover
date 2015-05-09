package com.example.brandon.uncover;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class EntryPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Spinner uncoverSpinner = (Spinner) findViewById(R.id.uncoverSpinner);
        Spinner visibilitySpinner = (Spinner) findViewById(R.id.visibilitySpinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.distances,
                android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        uncoverSpinner.setAdapter(adapter1);
        visibilitySpinner.setAdapter(adapter1);

        Button publishButton = (Button) findViewById(R.id.publish_button);
        publishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText desc = (EditText) findViewById(R.id.descText);
                String description = desc.getText().toString();

                EditText contentEdit = (EditText) findViewById(R.id.contentText);
                String content = contentEdit.getText().toString();

                ParseObject Entry = new ParseObject("Entry");
                Entry.put("Name", "Leroy Jenkins");
                Entry.put("Description", description);
                Entry.put("Content", content);
                int[] curTime = getTimeOfPosting();
                String timeOfPosting = curTime[0] + "/" + curTime[1] + "/" + curTime[2] + "; "
                        + curTime[3] + ":" + curTime[4];
                Entry.put("TimeOfPosting", timeOfPosting);

                Entry.saveInBackground();

                desc.setText("");
                contentEdit.setText("");

                Toast.makeText(EntryPage.this, "Post has been sent!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EntryPage.this, Map.class);
                startActivity(intent);
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
        time[0] = calendar.get(Calendar.DAY_OF_MONTH);
        time[1] = calendar.get(Calendar.MONTH);
        time[2] = calendar.get(Calendar.YEAR);
        time[3] = calendar.get(Calendar.HOUR_OF_DAY);
        time[4] = calendar.get(Calendar.MINUTE);
        return time;
    }
}
