package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;
import com.example.sven.myapplication.kochbuch.model.Ingredient;
import com.example.sven.myapplication.kochbuch.model.LocalMeal;
import com.example.sven.myapplication.kochbuch.model.Meal;
import com.example.sven.myapplication.kochbuch.model.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewReceipt extends AppCompatActivity {
    public static final String EXTRA_INGREDIENT = "EXTRA_INGREDIENT";
    private static final int REQUEST_ADD_INGREDIENT = 2;
    private List<Step> stepArray;
    private List<Ingredient> ingredientArray;

    public static final int REQUEST_ADD_STEP = 1;
    public static final String EXTRA_STEP = "EXTRA_STEP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final List<Ingredient> ingredientList = new ArrayList<Ingredient>();

        stepArray = new ArrayList<>();
        ingredientArray = new ArrayList<>();

        ((ListView) findViewById(R.id.listIngredients)).setAdapter(new IngredientAdapter(getApplicationContext(), ingredientArray));
        ((ListView) findViewById(R.id.listSteps)).setAdapter(new StepAdapter(getApplicationContext(), stepArray));

        ListUtils.resizeListViewToMatchFullHeight(((ListView) findViewById(R.id.listSteps)));
        ListUtils.resizeListViewToMatchFullHeight(((ListView) findViewById(R.id.listIngredients)));

        final List<Step> stepList = new ArrayList<Step>();

        findViewById(R.id.newStepButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewStep.class), REQUEST_ADD_STEP);
            }
        });

        findViewById(R.id.newIngredientButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewIngredient.class), REQUEST_ADD_INGREDIENT);
            }
        });

        findViewById(R.id.newReceiptSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meal meal = new LocalMeal(((TextView) findViewById(R.id.newReceiptName)).getText().toString());
                for (Step step : stepArray) {
                    meal.addStep(step);
                }

                for (Ingredient ingredient : ingredientArray) {
                    try {
                        meal.addIngredient(ingredient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent returner = new Intent();
                returner.putExtra(Kochbuch.EXTRA_MEAL, meal);
                setResult(RESULT_OK, returner);

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_STEP) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Schritt wurde NICHT gespeichert, bitte auf Speichern drücken.", Toast.LENGTH_LONG).show();
                return;
            }
            Step step = (Step) data.getSerializableExtra(EXTRA_STEP);
            stepArray.add(step);
            ((ListView) findViewById(R.id.listSteps)).setAdapter(new StepAdapter(getApplicationContext(), stepArray));
            ListUtils.resizeListViewToMatchFullHeight(((ListView) findViewById(R.id.listSteps)));
        } else if (requestCode == REQUEST_ADD_INGREDIENT) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Zutat wurde NICHT gespeichert, bitte auf Speichern drücken.", Toast.LENGTH_LONG).show();
                return;
            }
            Ingredient ingredient = (Ingredient) data.getSerializableExtra(EXTRA_INGREDIENT);
            ingredientArray.add(ingredient);
            ((ListView) findViewById(R.id.listIngredients)).setAdapter(new IngredientAdapter(getApplicationContext(), ingredientArray));
            ListUtils.resizeListViewToMatchFullHeight(((ListView) findViewById(R.id.listIngredients)));
        }
    }

    public static class ListUtils {
        public static void resizeListViewToMatchFullHeight(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = height + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
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
