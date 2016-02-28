package com.example.sven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Einkaufsliste_Hauptscreen extends AppCompatActivity {

    private SmartHouseholdOpenHandler openHandler;
    private SmartHouseholdOpenHandler dbHandler;
    private CursorAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste__hauptscreen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        openHandler = new SmartHouseholdOpenHandler(this);
        openHandler.insert("Test");

        ca = new SmartHouseholdAdapter(this);
        dbHandler = new SmartHouseholdOpenHandler(this);
        ca.changeCursor(dbHandler.query());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(ca);

        Button neueliste_button = (Button) findViewById(R.id.button);

        neueliste_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Einkaufsliste_Hauptscreen.this, Wocheneinkauf.class);
                Einkaufsliste_Hauptscreen.this.startActivity(myIntent);

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
