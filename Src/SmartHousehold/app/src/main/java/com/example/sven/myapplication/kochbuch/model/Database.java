package com.example.sven.myapplication.kochbuch.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elias on 03.03.16.
 */
public class Database {
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }

    public DatabaseMeal[] getMeals() {
        DatabaseMeal[] meals = new DatabaseMeal[10];
        for (int i = 0; i < 10; i++) {
            meals[i] = new DatabaseMeal(i, "Testessen " + i);
        }
        return meals;
    }

    protected Step[] getSteps(DatabaseMeal meal) {
        int mealId = meal.id;
        Step[] steps = new Step[3];
        for (int i = 0; i < 3; i++) {
            steps[i] = new Step("Schrittname " + i, "MealID " + mealId, (i == 1) ? -1 : 60);
        }
        return steps;
    }

    protected Ingredient[] getIngredients(DatabaseMeal meal) {
        int mealId = meal.id;
        Ingredient[] ingredients = new Ingredient[3];
        for (int i = 0; i < 3; i++) {
            ingredients[i] = new Ingredient(100 * i, 1, "Blaubeeren " + i, 90 * i, 1);
        }
        return ingredients;
    }

    protected void addStep(Meal meal, Step step) {
        throw new RuntimeException("Not yet implemented");
    }

    protected void addIngredient(Meal meal, Ingredient ingredient) {
        throw new RuntimeException("Not yet implemented");
    }

    protected void update(Meal meal) {
        throw new RuntimeException("Not yet implemented");
    }

    public Meal getMeal(int mealId) {
        return new DatabaseMeal(mealId, "Testessen " + mealId);
    }

    public void newReceipt(Meal meal) {
        throw new RuntimeException("Not yet implemented");
    }
}
