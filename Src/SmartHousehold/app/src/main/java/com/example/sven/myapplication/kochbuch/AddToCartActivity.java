package com.example.sven.myapplication.kochbuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Ingredient;

import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        List<Ingredient> ingredientList = (List<Ingredient>) getIntent().getSerializableExtra(MealActivity.EXTRA_INGREDIENTS);

        ((ListView) findViewById(R.id.addToCartIngredientList)).setAdapter(new IngredientAdapter(this, ingredientList));

        /*
         * Unter ingredientList liegt Liste aller Ingredients.
         *
         * Relevante Propertys:
         * name (String)
         * amount (int)
         * amountType (String)
         * price (int; in Cent!)
         * priceType (String)
         */
    }
}
