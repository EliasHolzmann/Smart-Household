package com.example.sven.myapplication;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;

public class Einkaufsliste_Hauptscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkaufsliste__hauptscreen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.setIconifiedByDefault(true);
        sv.setIconified(false);
    }
}