package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;

/**
 * Created by elias on 26.02.16.
 */
public class DatabaseMeal extends Meal implements Serializable {
    protected int id;
    protected String name;
    protected int price;

    protected DatabaseMeal(int id, String name) {
        super(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        super.setName(name);
        Database.getInstance().update(this);
    }

    public Step[] getSteps() {
        return Database.getInstance().getSteps(this);
    }

    public void addStep(Step step) {
        Database.getInstance().addStep(this, step);
    }

    public Ingredient[] getIngredients() {
        return Database.getInstance().getIngredients(this);
    }

    public void addIngredient(Ingredient ingredient) {
        Database.getInstance().addIngredient(this, ingredient);
    }
}
