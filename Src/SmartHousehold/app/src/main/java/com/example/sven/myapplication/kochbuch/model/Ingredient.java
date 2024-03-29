package com.example.sven.myapplication.kochbuch.model;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Represents an abstract meal.
 * This class is implemented by LocalMeal as well as DatabaseMeal.
 */
public class Ingredient implements Serializable {
    public final int amount;
    public final String amountType;
    public final String name;
    public final int price;
    public final String priceType;
    public Ingredient(int amount, String amountType, String name, int price, String priceType) {
        this.amount = amount;
        this.amountType = amountType;
        this.name = name;
        this.price = price;
        this.priceType = priceType;
    }

    /**
     * Reads an ingredient from a JSON object which was received form the RESTed API.
     * The JsonReader cursor should be BEFORE BEGIN_OBJECT
     * @param jsonReader the JsonReader object which contains the Ingredient object
     * @return A new ingredient which was constructed with the help of the JsonReader object
     */
    protected static Ingredient buildFromJson(JsonReader jsonReader) throws IOException {
        String name = "";
        int amount = 0;
        String amountType = "";
        int price = 0;
        String priceType = "";
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "name":
                    name = jsonReader.nextString();
                    break;
                case "amount":
                    amount = jsonReader.nextInt();
                    break;
                case "amountType":
                    amountType = jsonReader.nextString();
                    break;
                case "price":
                    price = jsonReader.nextInt();
                    break;
                case "priceType":
                    priceType = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return new Ingredient(amount, amountType, name, price, priceType);
    }

    /**
     * Writes this object to a JsonWriter.
     * @param jsonWriter the JsonWriter which will receive this JSON object
     */
    public void writeToJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(name);
        jsonWriter.name("amount");
        jsonWriter.value(amount);
        jsonWriter.name("amountType");
        jsonWriter.value(amountType);
        jsonWriter.name("price");
        jsonWriter.value(price);
        jsonWriter.name("priceType");
        jsonWriter.value(priceType);
        jsonWriter.endObject();
    }

}
