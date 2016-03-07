package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Ingredient;
import com.example.sven.myapplication.kochbuch.model.Step;

public class NewIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] types = new String[]{
                "g",
                "kg",
                "l",
                "ml",
                "stck"
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);
        ((Spinner) findViewById(R.id.newIngredientAmountType)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types));
        ((Spinner) findViewById(R.id.newIngredientPriceType)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types));

        findViewById(R.id.newIngredientSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.newIngredientName)).getText().toString();
                String amountStr = ((EditText) findViewById(R.id.newIngredientAmount)).getText().toString();
                String amountType = ((Spinner) findViewById(R.id.newIngredientAmountType)).getSelectedItem().toString();
                String priceStr = ((EditText) findViewById(R.id.newIngredientPrice)).getText().toString();
                String priceType = ((Spinner) findViewById(R.id.newIngredientPriceType)).getSelectedItem().toString();

                int amount = Integer.parseInt(amountStr);
                int price = (int) (Double.parseDouble(amountStr) * 100 + 0.5);

                Intent intent = new Intent();

                intent.putExtra(NewReceipt.EXTRA_INGREDIENT, new Ingredient(amount, amountType, name, price, priceType));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
