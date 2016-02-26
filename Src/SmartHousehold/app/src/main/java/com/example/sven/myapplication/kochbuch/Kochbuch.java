package com.example.sven.myapplication.kochbuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sven.myapplication.R;

public class Kochbuch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kochbuch);

        Meal meals[] = new Meal[5];
        meals[0] = new Meal("Schnitzel", 240);
        meals[1] = new Meal("Döner", 300);
        meals[2] = new Meal("Pommes", 130);
        meals[3] = new Meal("Cola", 150);
        meals[4] = new Meal("Eiersalat", 80);

        ArrayAdapter mealsAdapter = new MealAdapter(this, meals);
        ((ListView) findViewById(R.id.meals)).setAdapter(mealsAdapter);

    }
}
