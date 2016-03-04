package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias on 26.02.16.
 */
public class LocalMeal extends Meal {
    ArrayList<Step> steps = new ArrayList<Step>();
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    protected LocalMeal(String name) {
        super(name);
    }

    @Override
    public Step[] getSteps() {
        return (Step[]) steps.toArray();
    }

    @Override
    public void addStep(Step step) {
        steps.add(step);
    }

    @Override
    public Ingredient[] getIngredients() {
        return (Ingredient[]) ingredients.toArray();
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}
