package com.example.sven.myapplication.kochbuch.model;

import java.io.Serializable;

/**
 * Created by elias on 28.02.16.
 */
public class Step implements Serializable {
    public final String title;
    public final String description;
    public final int lengthSeconds;

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

    public String getTimeSecondsString() {
        String minutes = lengthSeconds / 60 + "";
        String seconds = lengthSeconds % 60 + "";
        while (seconds.length() < 2) {
            seconds = 0 + seconds;
        }

        return minutes + ":" + seconds;
    }
}
