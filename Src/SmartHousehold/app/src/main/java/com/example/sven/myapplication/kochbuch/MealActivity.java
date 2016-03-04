package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        final int mealId = getIntent().getIntExtra(CookingActivity.EXTRA_MEAL_ID, -1);
        if (mealId == -1) {
            throw new RuntimeException("MealActivity must be started with extra EXTRA_MEAL_ID");
        }

        setTitle(Database.getInstance().getMeal(mealId).getName());

        findViewById(R.id.mealActivityAddToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not yet implemented :)", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.mealActivityStartCooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CookingActivity.class);

                intent.putExtra(CookingActivity.EXTRA_MEAL_ID, mealId);

                startActivity(intent);
            }
        });

        ((ListView) findViewById(R.id.mealActivityIngredientsList)).setAdapter(new IngredientAdapter(getApplicationContext(), Database.getInstance().getMeal(mealId).getIngredients()));
    }
}
