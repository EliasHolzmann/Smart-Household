package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;
import com.example.sven.myapplication.kochbuch.model.DatabaseMeal;
import com.example.sven.myapplication.kochbuch.model.Meal;

public class Kochbuch extends AppCompatActivity {

    private static final String EXTRA_MEAL = "EXTRA_MEAL";
    private static final int REQUEST_CODE_NEW_RECEIPT = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kochbuch);

        final DatabaseMeal meals[] = Database.getInstance().getMeals();

        ArrayAdapter mealsAdapter = new MealAdapter(this, meals);
        ((ListView) findViewById(R.id.meals)).setAdapter(mealsAdapter);
        ((ListView) findViewById(R.id.meals)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MealActivity.class);
                intent.putExtra(MealActivity.EXTRA_MEAL_ID, meals[position].getId());
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.buttonNewMeal)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewReceipt.class), REQUEST_CODE_NEW_RECEIPT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NEW_RECEIPT) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Rezept wurde NICHT gespeichert, bitte auf Speichern drücken.", Toast.LENGTH_LONG).show();
                return;
            }
            Database.getInstance().newReceipt((Meal) data.getSerializableExtra(EXTRA_MEAL));
        }
    }
}
