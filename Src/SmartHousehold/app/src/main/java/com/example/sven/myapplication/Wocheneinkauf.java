package com.example.sven.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Wocheneinkauf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wocheneinkauf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
}
