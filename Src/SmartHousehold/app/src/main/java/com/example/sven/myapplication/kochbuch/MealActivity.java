package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This activity is invoked by Kochbuch activity. It represents one specific meal.
 * Displayed are all ingredients and a button to add those ingredients to the shopping cart.
 * At the bottom, there is one button to start cooking which launches CookingActivity.
 * This activity gets a meal ID via CookingActivity.EXTRA_MEAL_ID. This meal is the meal that is represented here.
 *
 * NOTE: This activity doesn't work with abstract Meal objects because it needs an ID field.
 * It is therefore designed for DatabaseMeals.
 */
public class MealActivity extends AppCompatActivity {

    public static final String EXTRA_INGREDIENTS = "EXTRA_INGREDIENTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final String mealId = getIntent().getStringExtra(CookingActivity.EXTRA_MEAL_ID);
        if (mealId == null) {
            throw new RuntimeException("MealActivity must be started with extra EXTRA_MEAL_ID");
        }

        setTitle(Database.getInstance().getMeal(mealId).getName());

        findViewById(R.id.mealActivityAddToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddToCartActivity.class);

                intent.putExtra(EXTRA_INGREDIENTS, new ArrayList(Arrays.asList(Database.getInstance().getMeal(mealId).getIngredients())));

                startActivity(intent);
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
