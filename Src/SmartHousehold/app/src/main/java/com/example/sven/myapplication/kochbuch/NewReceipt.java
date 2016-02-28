package com.example.sven.myapplication.kochbuch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.sven.myapplication.R;

public class NewReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);

        String ingredients[] = new String[10];

        ingredients[0] = "Mehl";
        ingredients[1] = "Ei";
        ingredients[2] = "Zucker";
        ingredients[3] = "Milch";
        ingredients[4] = "Hefe";
        ingredients[5] = "Salz";
        ingredients[6] = "Wasser";
        ingredients[7] = "Öl";
        ingredients[8] = "Essig";
        ingredients[9] = "Zucker";

        ((AutoCompleteTextView) findViewById(R.id.newReceiptIngredient)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ingredients));
    }
}
