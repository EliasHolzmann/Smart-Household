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
  * In this singleton, all network communication is happening.
  */
public class Database {

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

    /**
     * Returns all meals known to the database.
     * @return all meals known to the database.
     */
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

    /**
     * Returns all meals which match a specified string.
     * If the string contains characters which are neither alphanumeric nor a space, the result will be the same as getMeals().
     * @param searchString the string to be searched for
     * @return array of all meals which names contain searchString
     */
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

    /**
     * Returns all steps which are part of the given meal
     * @param meal the meal which steps should be loaded
     * @return the steps which where loaded from the database
     */
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

    /**
     * Returns all ingredients which are part of the given meal
     * @param meal the meal which ingredients should be loaded
     * @return the ingredients which where loaded from the database
     */
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

    /**
     * Returns the meal which has a specific meal ID.
     * @param mealId the meal ID
     * @return the meal which has a specific meal ID
     */
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

    /**
     * Adds a new meal to the database
     * @param meal the new (Local-)Meal
     */
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

    /**
     * Flushes the complete database. Helpful for testing purposes.
     */
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
