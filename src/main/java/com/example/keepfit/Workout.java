package com.example.keepfit;

import android.database.Cursor;

public class Workout implements DataParser<Workout> {
    private int id;
    private String name;
    private String description;

    public Workout(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Workout parseData(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_WORKOUT_ID));
        String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_WORKOUT_NAME));
        String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_WORKOUT_DESCRIPTION));
        return new Workout(id, name, description);
    }

    // Override the toString method to display the workout name in the ListView
    @Override
    public String toString() {
        return name;
    }
}
