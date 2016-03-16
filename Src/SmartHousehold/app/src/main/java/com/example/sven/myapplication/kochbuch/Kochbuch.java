package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;
import com.example.sven.myapplication.kochbuch.model.DatabaseMeal;
import com.example.sven.myapplication.kochbuch.model.LocalMeal;
import com.example.sven.myapplication.kochbuch.model.Meal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main activity of cooking module. Has a ListView of all meals and a button for adding new meals.
 * Long-pressing the button will reset the database (useful for testing purposes).
 */
public class Kochbuch extends AppCompatActivity {
    private List<DatabaseMeal> meals = new ArrayList<>();

    public static final String EXTRA_MEAL = "EXTRA_MEAL";
    public static final int REQUEST_CODE_NEW_RECEIPT = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kochbuch);

        reloadMealsListView();

        ((ListView) findViewById(R.id.meals)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MealActivity.class);
                intent.putExtra(com.example.sven.myapplication.kochbuch.CookingActivity.EXTRA_MEAL_ID, meals.get(position).getId());
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.buttonNewMeal)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewReceipt.class), REQUEST_CODE_NEW_RECEIPT);
            }
        });

        ((Button) findViewById(R.id.buttonNewMeal)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Database.getInstance().flushDatabase();
                reloadMealsListView();
                return true;
            }
        });

        ((SearchView) findViewById(R.id.receiptSearch)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                reloadMealsListView();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadMealsListView();
    }

    /**
     * On change of this.meals, this method must be called to reload the ListView.
     */
    private void reloadMealsListView() {
        if (((SearchView) findViewById(R.id.receiptSearch)).getQuery().length() == 0) {
            meals.clear();
            meals.addAll(Arrays.asList(Database.getInstance().getMeals()));
        } else {
            meals.clear();
            meals.addAll(Arrays.asList(Database.getInstance().getMeals(((SearchView) findViewById(R.id.receiptSearch)).getQuery().toString())));
        }
        ArrayAdapter mealsAdapter = new MealAdapter(getApplicationContext(), meals);
        // NOTE: ListView.invalidate() will NOT do what you want!!!
        ((ListView) findViewById(R.id.meals)).setAdapter(mealsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NEW_RECEIPT) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Rezept wurde NICHT gespeichert, bitte auf Speichern drücken.", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                Database.getInstance().newReceipt((LocalMeal) data.getSerializableExtra(EXTRA_MEAL));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
