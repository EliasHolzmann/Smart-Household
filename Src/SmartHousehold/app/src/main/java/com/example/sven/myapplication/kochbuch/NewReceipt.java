package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;
import com.example.sven.myapplication.kochbuch.model.Ingredient;
import com.example.sven.myapplication.kochbuch.model.LocalMeal;
import com.example.sven.myapplication.kochbuch.model.Step;

import java.util.ArrayList;
import java.util.List;

public class NewReceipt extends AppCompatActivity {
    private Step[] stepArray;
    private Ingredient[] ingredientArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_receipt);


        final List<Ingredient> ingredientList = new ArrayList<Ingredient>();

        stepArray = Database.getInstance().getMeal(0).getSteps();
        ingredientArray = Database.getInstance().getMeal(0).getIngredients();

        ((ListView) findViewById(R.id.listIngredients)).setAdapter(new IngredientAdapter(getApplicationContext(), ingredientArray));
        ((ListView) findViewById(R.id.listSteps)).setAdapter(new StepAdapter(getApplicationContext(), stepArray));

        ListUtils.resizeListViewToMatchFullHeight(((ListView) findViewById(R.id.listSteps)));
        ListUtils.resizeListViewToMatchFullHeight(((ListView) findViewById(R.id.listIngredients)));

        final List<Step> stepList = new ArrayList<Step>();
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
}
