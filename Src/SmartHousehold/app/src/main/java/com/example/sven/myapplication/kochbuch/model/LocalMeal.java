package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias on 26.02.16.
 */
public class LocalMeal extends Meal implements Serializable {
    List<Step> steps = new ArrayList<Step>();
    List<Ingredient> ingredients = new ArrayList<Ingredient>();

    public LocalMeal(String name) {
        super(name);
    }

    public LocalMeal(String name, List<Step> steps, List<Ingredient> ingredients) {
        super(name);
        this.steps = steps;
        this.ingredients = ingredients;
    }

    @Override
    public Step[] getSteps() {
        return steps.toArray(new Step[steps.size()]);
    }

    @Override
    public void addStep(Step step) {
        steps.add(step);
    }

    @Override
    public Ingredient[] getIngredients() {
        return ingredients.toArray(new Ingredient[ingredients.size()]);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}
