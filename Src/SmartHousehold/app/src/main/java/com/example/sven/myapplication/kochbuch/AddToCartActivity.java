package com.example.sven.myapplication.kochbuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.SmartHouseholdAdapter;
import com.example.sven.myapplication.SmartHouseholdAdapterAdd;
import com.example.sven.myapplication.SmartHouseholdOpenHandler;
import com.example.sven.myapplication.kochbuch.model.Ingredient;

import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    private SmartHouseholdOpenHandler dbHandler;
    private CursorAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Einkaufslisten");

        List<Ingredient> ingredientList = (List<Ingredient>) getIntent().getSerializableExtra(MealActivity.EXTRA_INGREDIENTS);

        Log.i("name: ", ingredientList.get(0).name.toString());
        Log.i("price: ", String.valueOf(ingredientList.get(0).price));
        Log.i("priceType:  ", ingredientList.get(0).priceType);
        Log.i("amount: ", String.valueOf(ingredientList.get(0).amount));
        Log.i("amountType:  ", ingredientList.get(0).amountType);


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

        ca = new SmartHouseholdAdapterAdd(this, ingredientList);
        dbHandler = new SmartHouseholdOpenHandler(this);
        ca.changeCursor(dbHandler.query());

        ListView listView = (ListView) findViewById(R.id.addToCartIngredientList);
        listView.setAdapter(ca);

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
