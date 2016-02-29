package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sven.myapplication.R;

public class NewReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);

        Ingredient ingredients[] = new Ingredient[3];
        ingredients[0] = new Ingredient("200 g", "Mehl");
        ingredients[1] = new Ingredient("500 g", "Hefe");
        ingredients[2] = new Ingredient("2 l", "Wasser");

        LinearLayout listIngredients = (LinearLayout) findViewById(R.id.listIngredients);

        for (int i = 0; i < ingredients.length; i++) {
            final int finalI = i;
            Ingredient ingredient = ingredients[i];
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

        Step steps[] = new Step[3];
        steps[0] = new Step("Ofen vorheizen", "Ofen auf 200° stellen");
        steps[1] = new Step("Nudeln kochen", 540);
        steps[2] = new Step("Geschnetzeltes anbraten", 830);

        LinearLayout listSteps = (LinearLayout) findViewById(R.id.listSteps);

        for (int i = 0; i < steps.length; i++) {
            final int finalI = i;
            Step step = steps[i];
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
    }
}
