package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;

/**
 * Created by elias on 26.02.16.
 */
public abstract class Meal implements Serializable {
    protected String name;

    protected Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Database.getInstance().update(this);
    }

    public abstract Step[] getSteps();
    public abstract void addStep(Step step);
    public abstract Ingredient[] getIngredients();
    public abstract void addIngredient(Ingredient ingredient);
}
