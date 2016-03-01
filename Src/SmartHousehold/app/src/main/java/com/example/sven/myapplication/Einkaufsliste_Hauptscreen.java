package com.example.sven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

    /*
    Handler für die Datenbankconnection wird geöffnet
     */

    private SmartHouseholdOpenHandler openHandler;
    private SmartHouseholdOpenHandler dbHandler;
    private CursorAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste__hauptscreen);

        /*
        Return Button wird in die Action Bar hinzugefügt
         */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /*
        Der Adapter für die ListView wird erzeugt und über den Cursor mit Daten gefüllt
         */

        ca = new SmartHouseholdAdapter(this);
        dbHandler = new SmartHouseholdOpenHandler(this);
        ca.changeCursor(dbHandler.query());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(ca);

        Button neueliste_button = (Button) findViewById(R.id.button);

        neueliste_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Beim betätigen des Buttons um eine neue Einkaufsliste anzulegen wird ein insert Befehl auf die Tabelle
                ausgeführt und auf eine neue Activity zum Bearbeiten genau dieser Einkaufsliste geöffnet
                 */
                openHandler = new SmartHouseholdOpenHandler(getApplicationContext());
                long id = openHandler.insert("Unbenannte Liste");

                Intent myIntent = new Intent(Einkaufsliste_Hauptscreen.this, Wocheneinkauf.class);
                myIntent.putExtra("LISTENID", id);
                myIntent.putExtra("BEZEICHNUNG", "Unbenannte Liste");
                Einkaufsliste_Hauptscreen.this.startActivity(myIntent);

            }
        });

    }

    /*
    Funktion für den Return Button in der Actionbar
     */

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
        Update der Liste wenn zum Screen zurückgekehrt wird. Die Datenbankeinträge könnten sich geändert haben
         */

        ca = new SmartHouseholdAdapter(this);
        dbHandler = new SmartHouseholdOpenHandler(this);
        ca.changeCursor(dbHandler.query());

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(ca);
    }

}
