package com.example.sven.myapplication;

/**
 * Created by Sven on 03.03.2016.
 */
public class ToDoItem {

    @com.google.gson.annotations.SerializedName("text")
    private String mText;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("complete")
    private boolean mComplete;

    public ToDoItem() {

    }

    @Override
    public String toString() {
        return getText();
    }

    public ToDoItem(String text, String id) {
        this.setText(text);
        this.setId(id);
    }

    public String getText() {
        return mText;
    }

    public final void setText(String text) {
        mText = text;
    }

    public String getId() {
        return mId;
    }

    public final void setId(String id) {
        mId = id;
    }

    public boolean isComplete() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ToDoItem && ((ToDoItem) o).mId == mId;
    }
}