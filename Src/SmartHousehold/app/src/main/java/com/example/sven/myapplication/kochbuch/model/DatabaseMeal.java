package com.example.sven.myapplication.kochbuch.model;

import android.util.JsonReader;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by elias on 26.02.16.
 */
public class DatabaseMeal extends Meal implements Serializable {
    protected String id;

    protected DatabaseMeal(String id, String name) {
        super(name);
        this.id = id;
    }

    protected static DatabaseMeal buildMealFromJson(JsonReader jsonReader) throws IOException {
        String id = null;
        String name = "";

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String propertyName = jsonReader.nextName();

            switch (propertyName) {
                case "_id":
                    id = jsonReader.nextString();
                    break;
                case "name":
                    name = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }

        jsonReader.endObject();

        assert(id != null);

        return new DatabaseMeal(id, name);
    }

    public String getId() {
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

    public void addIngredient(Ingredient ingredient) throws IOException {
        Database.getInstance().addIngredient(this, ingredient);
    }
}
