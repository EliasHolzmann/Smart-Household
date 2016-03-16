package com.example.sven.myapplication.kochbuch.model;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Represents an abstract meal.
 * This class is implemented by LocalMeal as well as DatabaseMeal.
 */
public abstract class Meal implements Serializable {
    protected String name;

    public Meal(String name) {
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

    /**
     * Adds an ingredient at the end of the ingredient list.
     * @param ingredient the ingredient which should be added
     * @throws IOException iff there is a problem with networking
     */
    public abstract void addIngredient(Ingredient ingredient) throws IOException;

    /**
     * Writes this object to a JsonWriter.
     * @param jsonWriter the JsonWriter which will receive this JSON object
     */
    public void writeToJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("name");
        jsonWriter.value(getName());

        jsonWriter.name("ingredients");
        jsonWriter.beginArray();
        for (Ingredient ingredient : getIngredients()) {
            ingredient.writeToJson(jsonWriter);
        }
        jsonWriter.endArray();

        jsonWriter.name("steps");
        jsonWriter.beginArray();
        for (Step step : getSteps()) {
            step.writeToJson(jsonWriter);
        }
        jsonWriter.endArray();

        jsonWriter.endObject();
    }
}
