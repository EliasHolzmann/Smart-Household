package com.example.sven.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Zutat_Hinzufuegen extends AppCompatActivity {

    private EinkaufslisteOpenHandler openHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zutat__hinzufuegen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        final long id = extras.getLong("LISTENID");

        /*
        Die beiden Spinner erzeugen die Dropdownmenüs mit den jeweiligen Einheiten
         */

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("g");
        spinnerArray.add("kg");
        spinnerArray.add("stck");
        spinnerArray.add("mg");
        spinnerArray.add("ml");
        spinnerArray.add("l");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("pro g");
        spinnerArray2.add("pro kg");
        spinnerArray2.add("pro stck");
        spinnerArray2.add("pro mg");
        spinnerArray2.add("pro ml");
        spinnerArray2.add("pro l");
        spinnerArray2.add("insgesamt");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner2);
        sItems2.setAdapter(adapter2);

        Button hinzufuegen = (Button) findViewById(R.id.button4);

        hinzufuegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Nach dem betätigen des Hinzufügen Buttons, wird der Datensatz hinzugefügt und in die Datenbank geschrieben
                 */

                EditText name = (EditText) findViewById(R.id.newReceiptName);
                EditText menge = (EditText) findViewById(R.id.editText2);
                EditText preis = (EditText) findViewById(R.id.editText3);

                Spinner spinnermenge = (Spinner) findViewById(R.id.spinner);
                Spinner spinnerpreis = (Spinner) findViewById(R.id.spinner2);

                String mengeString = menge.getText().toString();

                String preisString = preis.getText().toString();

                String nameValue = String.valueOf(name.getText());

                if(nameValue.isEmpty()){
                    nameValue="";
                }
                if(mengeString.isEmpty()){
                    mengeString="";
                }
                if(preisString.isEmpty()){
                    preisString="";
                }

                openHandler = new EinkaufslisteOpenHandler(getApplicationContext());
                openHandler.insert(id, String.valueOf(name.getText()), mengeString, spinnermenge.getSelectedItem().toString(), preisString, "", spinnerpreis.getSelectedItem().toString());

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
}
