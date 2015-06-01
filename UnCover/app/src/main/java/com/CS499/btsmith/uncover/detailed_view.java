package com.CS499.btsmith.uncover;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.CS499.btsmith.uncover.Data.Entry;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class detailed_view extends ActionBarActivity {

    private String objectID;
    private TextView desc;
    private TextView content;
    private TextView author;
    private TextView distance;
    private TextView date;
    private TextView uncoversAmount;
    private Bundle imported;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        imported = getIntent().getExtras();
        objectID = imported.getString("objectID");

        desc = (TextView) findViewById(R.id.neocacheDesc);
        content = (TextView) findViewById(R.id.neocacheContent);
        author = (TextView) findViewById(R.id.neocacheOwner);
        distance = (TextView) findViewById(R.id.neocacheDistance);
        date = (TextView) findViewById(R.id.neocacheDate);
        uncoversAmount = (TextView) findViewById(R.id.uncovers_amount);

        ParseQuery<Entry> query = new ParseQuery<Entry>(Entry.class);
        //query.whereEqualTo("objectID", objectID);
        query.getInBackground(objectID, new GetCallback<Entry>()
        {
            @Override
            public void done(Entry entry, ParseException e)
            {
                if(e == null)
                {
                    desc.setText(entry.getDescription());
                    content.setText(entry.getContent());
                    author.setText(entry.getName());
                    distance.setText(imported.getString("distance") + " mi");
                    date.setText(entry.getTime());
                    uncoversAmount.setText(entry.getViews().toString() + " views");

                    //update views to +1
                    Integer newVal = (Integer) entry.getViews();
                    entry.setViews(newVal + 1);
                    entry.saveInBackground();
                }
            }
        });

        Button back = (Button) findViewById(R.id.neocacheBack);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(detailed_view.this, neocacheListView.class);
                //add list place to bundle so users can go back to where they were looking
                Bundle bundle = new Bundle();
                bundle.putParcelable("Location", imported.getParcelable("Location"));
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_view, menu);
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
}
