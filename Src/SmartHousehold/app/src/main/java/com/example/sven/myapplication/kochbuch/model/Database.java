package com.example.sven.myapplication.kochbuch.model;

import android.os.StrictMode;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elias on 03.03.16.
 */
public class Database {
/*
 * Todo list
 * add ability to add new/edit steps/ingredients in newReceipt activity
 * remove reference to mealId 0 (currently breaks on Database.flushDatabase()
 * talk with Sven about interface for communication with cart
 * optional: make saved receipts editable
 */

    private List<DatabaseMeal> meals = new ArrayList<DatabaseMeal>();
    private Map<String, List<Ingredient>> ingredientMap = new HashMap<String, List<Ingredient>>();
    private Map<String, List<Step>> stepMap = new HashMap<String, List<Step>>();

    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public DatabaseMeal[] getMeals() {
        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));

            List<DatabaseMeal> meals = new ArrayList<>();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                meals.add(DatabaseMeal.buildMealFromJson(jsonReader));
            }

            return meals.toArray(new DatabaseMeal[meals.size()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //return new DatabaseMeal[]{};
        }
    }

    public DatabaseMeal[] getMeals(String searchString) {

        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe" + (searchString.matches("[a-zA-Z0-9 ]+") ? "/search/" +  searchString : ""));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));

            List<DatabaseMeal> meals = new ArrayList<>();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                meals.add(DatabaseMeal.buildMealFromJson(jsonReader));
            }

            return meals.toArray(new DatabaseMeal[meals.size()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //return new DatabaseMeal[]{};
        }
    }

    protected Step[] getSteps(DatabaseMeal meal) {

        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe/" + meal.id);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));

            List<Step> steps = new ArrayList<>();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String str = jsonReader.nextName();
                if (!str.equals("steps")) {
                    jsonReader.skipValue();
                    continue;
                }

                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    steps.add(Step.buildFromJson(jsonReader));
                }
                jsonReader.endArray();
            }
            jsonReader.endObject();

            return steps.toArray(new Step[steps.size()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //return new DatabaseMeal[]{};
        }
    }

    protected Ingredient[] getIngredients(DatabaseMeal meal) {

        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe/" + meal.id);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));

            List<Ingredient> ingredients = new ArrayList<>();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String str = jsonReader.nextName();
                if (!str.equals("ingredients")) {
                    jsonReader.skipValue();
                    continue;
                }

                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    ingredients.add(Ingredient.buildFromJson(jsonReader));
                }
                jsonReader.endArray();
            }
            jsonReader.endObject();

            return ingredients.toArray(new Ingredient[ingredients.size()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //return new DatabaseMeal[]{};
        }
    }

    protected void addStep(DatabaseMeal meal, Step step) {
        throw new RuntimeException("Not yet implemented");
    }

    protected void addIngredient(DatabaseMeal meal, Ingredient ingredient) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(new BufferedWriter(stringWriter));
        ingredient.writeToJson(jsonWriter);
        jsonWriter.flush();


        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe/" + meal.id + "/add/ingredient");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());

            printWriter.print(stringWriter.toString());

            printWriter.flush();
            printWriter.close();
            urlConnection.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void update(Meal meal) {
        throw new RuntimeException("Not yet implemented");
    }

    public Meal getMeal(String mealId) {
        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe/" + mealId);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            JsonReader jsonReader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));
            return DatabaseMeal.buildMealFromJson(jsonReader);

        } catch (IOException e) {
            throw new RuntimeException(e);
            //return new DatabaseMeal[]{};
        }
    }

    public void newReceipt(LocalMeal meal) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(new BufferedWriter(stringWriter));
        meal.writeToJson(jsonWriter);
        jsonWriter.flush();

        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(stringWriter.toString().length()));


            urlConnection.setDoOutput(true);

            PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());

            printWriter.print(stringWriter.toString());

            printWriter.flush();
            printWriter.close();
            urlConnection.connect();

            urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flushDatabase() {

        URL url;
        try {
            url = new URL("http://8xw9x6dayy2cc9sl.myfritz.net/recipe/flush");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");

            urlConnection.connect();

            urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
