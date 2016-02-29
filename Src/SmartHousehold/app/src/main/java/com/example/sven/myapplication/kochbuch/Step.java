package com.example.sven.myapplication.kochbuch;

/**
 * Created by elias on 28.02.16.
 */
public class Step {
    protected String title;
    protected String description;
    protected int lengthSeconds;

    protected Step(String title) {
        this.title = title;
        this.description = null;
        this.lengthSeconds = -1;
    }

    protected Step(String title, String description) {
        this.title = title;
        this.description = description;
        this.lengthSeconds = -1;
    }

    protected Step(String title, int lengthSeconds) {
        this.title = title;
        this.description = null;
        this.lengthSeconds = lengthSeconds;
    }

    protected Step(String title, String description, int lengthSeconds) {
        this.title = title;
        this.description = description;
        this.lengthSeconds = lengthSeconds;
    }
}
