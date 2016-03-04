package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Ingredient;
import com.example.sven.myapplication.kochbuch.model.LocalMeal;
import com.example.sven.myapplication.kochbuch.model.Step;

import java.util.ArrayList;
import java.util.List;

public class NewReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);


        final List<Ingredient> ingredientList = new ArrayList<Ingredient>();

        LinearLayout listIngredients = (LinearLayout) findViewById(R.id.listIngredients);

        for (int i = 0; i < ingredientList.size(); i++) {
            final int finalI = i;
            Ingredient ingredient = ingredientList.get(i);
            View element = getLayoutInflater().inflate(R.layout.ingredient_list_item, null);
            ((TextView) element.findViewById(R.id.ingredientListAmount)).setText(ingredient.amount);
            ((TextView) element.findViewById(R.id.ingredientListName)).setText(ingredient.name);

            element.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("SvensApplication", "Clicked on ingredient item " + finalI);
                }
            });
            listIngredients.addView(element);
        }

        final List<Step> stepList = new ArrayList<Step>();

        LinearLayout listSteps = (LinearLayout) findViewById(R.id.listSteps);

        for (int i = 0; i < stepList.size(); i++) {
            final int finalI = i;
            Step step = stepList.get(i);
            View element = getLayoutInflater().inflate(R.layout.step_list_item, null);
            String minutes = step.lengthSeconds / 60 + "";
            String seconds = step.lengthSeconds % 60 + "";
            if (seconds.length() == 1) {
                seconds = "0" + seconds;
            }
            ((TextView) element.findViewById(R.id.stepListLength)).setText(step.lengthSeconds == -1 ? "" : (minutes + ":" + seconds));
            ((TextView) element.findViewById(R.id.stepListName)).setText(step.title);

            element.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("SvensApplication", "Clicked on step item " + finalI);
                    startActivity(new Intent(getApplicationContext(), NewStep.class));
                }
            });
            listSteps.addView(element);
        }

        findViewById(R.id.NewReceiptSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Kochbuch.EXTRA_MEAL, new LocalMeal((((TextView) findViewById(R.id.newReceiptName)).getText().toString()), stepList, ingredientList));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
