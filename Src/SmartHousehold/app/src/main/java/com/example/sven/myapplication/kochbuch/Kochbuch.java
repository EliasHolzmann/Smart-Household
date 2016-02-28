package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sven.myapplication.R;

public class Kochbuch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kochbuch);

        Meal meals[] = new Meal[10];
        meals[0] = new Meal("Schnitzel", 240);
        meals[1] = new Meal("Döner", 300);
        meals[2] = new Meal("Pommes", 130);
        meals[3] = new Meal("Cola", 150);
        meals[4] = new Meal("Eiersalat", 80);
        meals[5] = new Meal("Muscheln", 350);
        meals[6] = new Meal("Sushi", 1200);
        meals[7] = new Meal("Pizza", 150);
        meals[8] = new Meal("Brot", 30);
        meals[9] = new Meal("Nudeln", 80);

        ArrayAdapter mealsAdapter = new MealAdapter(this, meals);
        ((ListView) findViewById(R.id.meals)).setAdapter(mealsAdapter);

        ((Button) findViewById(R.id.buttonNewMeal)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewReceipt.class));
            }
        });
    }
}
