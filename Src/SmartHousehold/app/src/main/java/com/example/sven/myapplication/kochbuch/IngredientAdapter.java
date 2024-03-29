package com.example.sven.myapplication.kochbuch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Ingredient;

import java.util.List;

/**
 * This ArrayAdapter offers an Adapter for usage in ListViews with a item view developed specifically for steps.
 */
public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    public IngredientAdapter(Context context, Ingredient[] ingredient) {
        super(context, 0, ingredient);
    }

    public IngredientAdapter(Context context, List<Ingredient> ingredient) {
        super(context, 0, ingredient);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ingredient ingredient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        }
        // Lookup view for data population
        TextView ingredientName = (TextView) convertView.findViewById(R.id.ingredientListName);
        TextView ingredientAmount = (TextView) convertView.findViewById(R.id.ingredientListAmount);
        // Populate the data into the template view using the data object
        ingredientName.setText(ingredient.name);
        ingredientAmount.setText(ingredient.amount + " " + ingredient.amountType);
        // Return the completed view to render on screen
        return convertView;
    }

}
