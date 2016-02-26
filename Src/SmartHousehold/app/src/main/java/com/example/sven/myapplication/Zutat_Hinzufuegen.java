package com.example.sven.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Zutat_Hinzufuegen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zutat__hinzufuegen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner2);
        sItems2.setAdapter(adapter2);

    }
}
