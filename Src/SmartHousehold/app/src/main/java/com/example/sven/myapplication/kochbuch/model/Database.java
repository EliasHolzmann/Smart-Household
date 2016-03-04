package com.example.sven.myapplication.kochbuch.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elias on 03.03.16.
 */
public class Database {
    private List<DatabaseMeal> meals = new ArrayList<DatabaseMeal>();
    private Map<Integer, List<Ingredient>> ingredientMap = new HashMap<Integer, List<Ingredient>>();
    private Map<Integer, List<Step>> stepMap = new HashMap<Integer, List<Step>>();

    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
        for (int i = 0; i < 10; i++) {
            meals.add(new DatabaseMeal(i, "Testessen " + i));

            List<Step> stepList = new ArrayList<Step>();
            for (int j = 0; j < 3; j++) {
                stepList.add(new Step("Schrittname " + j, "MealID " + i, (j == 1) ? -1 : 60));
            }
            stepMap.put(i, stepList);

            List<Ingredient> ingredientList = new ArrayList<Ingredient>();
            for (int j = 0; j < 3; j++) {
                ingredientList.add(new Ingredient(100 * i, 1, "Blaubeeren " + i, 90 * i, 1));
            }
            ingredientMap.put(i, ingredientList);
        }
    }

    public DatabaseMeal[] getMeals() {
        return meals.toArray(new DatabaseMeal[meals.size()]);
    }

    protected Step[] getSteps(DatabaseMeal meal) {
        return stepMap.get(meal.id).toArray(new Step[stepMap.get(meal.id).size()]);
    }

    protected Ingredient[] getIngredients(DatabaseMeal meal) {
        return ingredientMap.get(meal.id).toArray(new Ingredient[ingredientMap.get(meal.id).size()]);
    }

    protected void addStep(DatabaseMeal meal, Step step) {
        stepMap.get(meal.id).add(step);
    }

    protected void addIngredient(DatabaseMeal meal, Ingredient ingredient) {
        ingredientMap.get(meal.id).add(ingredient);
    }

    protected void update(Meal meal) {
        // not needed in this fake db handle
    }

    public Meal getMeal(int mealId) {
        for (DatabaseMeal meal : meals) {
            if (meal.id == mealId) {
                return meal;
            }
        }

        throw new IllegalArgumentException("mealId " + mealId + " not registered");
    }

    public void newReceipt(Meal meal) {
        int id = (int) (Math.random() * 100000);      // Well, id calculation is a little dirty, but it's only mockup, so who cares?
        meals.add(new DatabaseMeal(id, meal.getName()));
        stepMap.put(id, new ArrayList<Step>(Arrays.asList(meal.getSteps())));
        ingredientMap.put(id, new ArrayList<Ingredient>(Arrays.asList(meal.getIngredients())));
    }
}
