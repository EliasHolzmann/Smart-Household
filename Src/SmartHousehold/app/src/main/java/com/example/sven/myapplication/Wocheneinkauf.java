package com.example.sven.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Wocheneinkauf extends AppCompatActivity {

    /*
    Klasse für das Bearbeiten einer speziellen Einkaufsliste
     */

    private SmartHouseholdOpenHandler dbHandler;
    private CursorAdapter ca;
    private EinkaufslisteOpenHandler dbHandler_einkauf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wocheneinkauf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /*
        Es wurde über die extras des Activityübergangs die Daten mit der Bezeichnung der Einkaufsliste
        und der ID übergeben um auf die zugehörigen Zutaten zuzugreifen
         */

        Bundle extras = getIntent().getExtras();
        final long id = extras.getLong("LISTENID");
        final String bez = extras.getString("BEZEICHNUNG");

        setTitle(bez);

        Log.d(Wocheneinkauf.class.getSimpleName(), "ID=" + id);

        EditText titel = (EditText) findViewById(R.id.editText4);
        titel.setText(bez);

        /*
        Alle Zutaten werden ausgelesen über den Adapter und in die ListView gegeben
         */

        ca = new EinkaufslisteAdapter(this);
        dbHandler_einkauf = new EinkaufslisteOpenHandler(this);
        ca.changeCursor(dbHandler_einkauf.query(id));

        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(ca);

        Button einkaufsliste_button = (Button) findViewById(R.id.button2);

        einkaufsliste_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Der Button zum Hinzufügen von Zutaten öffnet die dementsprechende Activity und gibt die ID mit,
                zu welcher die Zutat gehört (Einkaufsliste <-> Zutat)
                 */

                Intent myIntent = new Intent(Wocheneinkauf.this, Zutat_Hinzufuegen.class);
                myIntent.putExtra("LISTENID", id);
                Wocheneinkauf.this.startActivity(myIntent);

            }
        });

        Button speichern_button = (Button) findViewById(R.id.button3);

        speichern_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Der Speichern Button speichert lediglich die Bezeichnung der Einkaufsliste
                 */

                EditText titel = (EditText) findViewById(R.id.editText4);
                String titelValue = String.valueOf(titel.getText());

                if(titelValue.isEmpty()){
                    titelValue="";
                }

                dbHandler = new SmartHouseholdOpenHandler(getApplicationContext());
                dbHandler.updateEinkaufsliste(id, titelValue);

                finish();

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

    @Override
    public void onResume() {
        super.onResume();

        /*
        Falls zur Aktivity aus einer anderen zurückgekehrt wird, wird der Adapter neu mit Daten befüllt um den aktuellen
        Datenbankstand zu haben.
         */

        Bundle extras = getIntent().getExtras();
        final long id = extras.getLong("LISTENID");
        final String bez = extras.getString("BEZEICHNUNG");

        ca = new EinkaufslisteAdapter(this);
        dbHandler_einkauf = new EinkaufslisteOpenHandler(this);
        ca.changeCursor(dbHandler_einkauf.query(id));

        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(ca);
    }

}
