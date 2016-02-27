package com.example.sven.myapplication.kochbuch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sven.myapplication.R;

import java.util.ArrayList;

/**
 * Created by elias on 26.02.16.
 */
public class MealAdapter extends ArrayAdapter<Meal> {

    public MealAdapter(Context context, Meal[] meal) {
        super(context, 0, meal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Meal meal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meal_list_item, parent, false);
        }
        // Lookup view for data population
        TextView mealName = (TextView) convertView.findViewById(R.id.mealListName);
        TextView mealPrice = (TextView) convertView.findViewById(R.id.mealListPrice);
        // Populate the data into the template view using the data object
        mealName.setText(meal.name);
        String euro = "" + meal.price / 100;
        String cent = "" + meal.price % 100;
        if (cent.length() == 1) {
            cent = "0" + cent;
        }
        mealPrice.setText(euro + "," + cent +  " €");
        // Return the completed view to render on screen
        return convertView;
    }

}
