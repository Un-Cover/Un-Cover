package com.CS499.btsmith.uncover;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.CS499.btsmith.uncover.Data.Notification;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.List;

public class Dashboard extends ActionBarActivity {

    private notificationsAdapter nAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final ListView listview = (ListView) findViewById(android.R.id.list);

        final ParseQuery<Notification> noteQuery = ParseQuery.getQuery(Notification.class);
        noteQuery.findInBackground(new FindCallback<Notification>() {
            @Override
            public void done(List<Notification> notifications, com.parse.ParseException e) {
                try
                {
                    nAdapter = new notificationsAdapter(Dashboard.this, noteQuery.find());
                }
                catch(com.parse.ParseException exception)
                {
                    Toast.makeText(Dashboard.this, "OOPS WE MESSED UP", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
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

    public void newEntry(View view)
    {
        Intent intent = new Intent(this, EntryPage.class);
        startActivity(intent);
    }

    public void returnToSplash(View view)
    {
        Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);
    }

    public void dashToMap(View view)
    {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }
}
