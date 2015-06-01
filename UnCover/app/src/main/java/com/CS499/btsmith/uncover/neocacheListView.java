package com.CS499.btsmith.uncover;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.CS499.btsmith.uncover.Data.Entry;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;


public class neocacheListView extends ListActivity {

    private ParseQuery<Entry> entries = ParseQuery.getQuery(Entry.class);
    private ListView listview;
    private parseArrayAdapter adapter;
    private Location location;
    private Button prevButton;
    private Button nextButton;
    private TextView pageNumber;
    private int increment;

    public int ITEMS_PER_PAGE = 10;
    public int TOTAL_ENTRIES;
    public int PAGES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neocache_list_view);
        listview = (ListView) findViewById(android.R.id.list);

        //Populate ListView Here
        location = (Location) getIntent().getExtras().getParcelable("Location");
        prevButton = (Button) findViewById(R.id.previousPage);
        nextButton = (Button) findViewById(R.id.nextPage);
        pageNumber = (TextView) findViewById(R.id.pageNumber);

        prevButton.setEnabled(false);
        checkEnable();

        try
        {
            TOTAL_ENTRIES = entries.count();
            int mod = TOTAL_ENTRIES%ITEMS_PER_PAGE;
            mod = mod==0?0:1;
            PAGES = TOTAL_ENTRIES/ITEMS_PER_PAGE + mod;
        }
        catch(ParseException exception)
        {
            Toast.makeText(neocacheListView.this, "OOPS WE MESSED UP", Toast.LENGTH_SHORT).show();
        }

        increment = 0;
        loadList(increment);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(neocacheListView.this, "YOU CLICKED", Toast.LENGTH_SHORT).show();
            }
        });

        Button backToMap = (Button) findViewById(R.id.listToMap);
        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(neocacheListView.this, Map.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment++;
                checkEnable();
                loadList(increment);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment--;
                checkEnable();
                loadList(increment);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_neocache_list_view, menu);
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

    private void checkEnable()
    {
        if(increment+1 == PAGES)
        {
            nextButton.setEnabled(false);
        }
        else if(increment == 0)
        {
            prevButton.setEnabled(false);
        }
        else
        {
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
        }
    }

    private void loadList(int number)
    {
        //load parse query 10 items at a time
        pageNumber.setText("Page " + (number+1) + " of " + PAGES);

        //query data in for loop and instantiate parseArrayAdapter here

        entries.setSkip(number*10);
        entries.setLimit(10);
        entries.findInBackground(new FindCallback<Entry>() {
            @Override
            public void done(List<Entry> parseObjects, ParseException e)
            {
                try
                {
                    adapter = new parseArrayAdapter(neocacheListView.this, entries.find(), location);
                    listview.setAdapter(adapter);
                    TOTAL_ENTRIES = entries.count();
                    int mod = TOTAL_ENTRIES%ITEMS_PER_PAGE;
                    mod = mod==0?0:1;
                    PAGES = TOTAL_ENTRIES/ITEMS_PER_PAGE + mod;
                }
                catch(ParseException exception)
                {
                    Toast.makeText(neocacheListView.this, "OOPS WE MESSED UP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
