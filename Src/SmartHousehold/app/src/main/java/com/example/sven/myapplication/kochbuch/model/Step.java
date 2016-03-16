package com.example.sven.myapplication.kochbuch.model;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;

/**
 * Represents a step in a meal.
 * This class is used for LocalMeal as well as DatabaseMeal.
 */
public class Step implements Serializable {
    public final String title;
    public final String description;
    public final int lengthSeconds;   // -1 represents no length given

    public Step(String title) {
        this.title = title;
        this.description = null;
        this.lengthSeconds = -1;
    }

    public Step(String title, String description) {
        this.title = title;
        this.description = description;
        this.lengthSeconds = -1;
    }

    public Step(String title, int lengthSeconds) {
        this.title = title;
        this.description = null;
        this.lengthSeconds = lengthSeconds;
    }

    public Step(String title, String description, int lengthSeconds) {
        this.title = title;
        this.description = description;
        this.lengthSeconds = lengthSeconds;
    }

    public String getTimeSecondsString() {
        String minutes = lengthSeconds / 60 + "";
        String seconds = lengthSeconds % 60 + "";
        while (seconds.length() < 2) {
            seconds = 0 + seconds;
        }

        return minutes + ":" + seconds;
    }

    /**
     * Reads a step from a JSON object which was received form the RESTed API.
     * The JsonReader cursor should be BEFORE BEGIN_OBJECT
     * @param jsonReader the JsonReader object which contains the Step object
     * @return A new step which was constructed with the help of the JsonReader object
     */
    protected static Step buildFromJson(JsonReader jsonReader) throws IOException {
        String name = "";
        String description = "";
        int lengthSeconds = -1;
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                case "name":
                    name = jsonReader.nextString();
                    break;
                case "description":
                    description = jsonReader.nextString();
                    break;
                case "lengthSeconds":
                    lengthSeconds = jsonReader.nextInt();
                    break;
                default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return new Step(name, description, lengthSeconds);
    }

    /**
     * Writes this object to a JsonWriter.
     * @param jsonWriter the JsonWriter which will receive this JSON object
     */
    public void writeToJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(title);
        jsonWriter.name("description");
        jsonWriter.value(description);
        jsonWriter.name("lengthSeconds");
        jsonWriter.value(lengthSeconds);
        jsonWriter.endObject();
    }
}
