package com.example.keepfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FitnessAppDB.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name"; // Add name column

    private static final String TABLE_EXERCISES = "exercises";
    private static final String COLUMN_SETS = "sets";
    private static final String COLUMN_REPS = "reps";
    private static final String COLUMN_WEIGHTS = "weights";

    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_WORKOUT_ID = "workout_id";
    public static final String COLUMN_WORKOUT_NAME = "workout_name";
    public static final String COLUMN_WORKOUT_DESCRIPTION = "workout_description";

    private static final String TABLE_EXERCISE = "exercises";
    private static final String COLUMN_EXERCISE_ID = "exercise_id";
    private static final String COLUMN_EXERCISE_NAME = "exercise_name";
    private static final String COLUMN_EXERCISE_DESCRIPTION = "exercise_description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NAME + " TEXT" // Add name column
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        String CREATE_WORKOUTS_TABLE = "CREATE TABLE " + TABLE_WORKOUTS + "("
                + COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_WORKOUT_NAME + " TEXT,"
                + COLUMN_WORKOUT_DESCRIPTION + " TEXT"
                + ")";
        db.execSQL(CREATE_WORKOUTS_TABLE);

        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "("
                + COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_EXERCISE_NAME + " TEXT,"
                + COLUMN_EXERCISE_DESCRIPTION + " TEXT"
                + ")";
        db.execSQL(CREATE_EXERCISES_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        User user = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            user = new User(id, email, password, name);
        }

        cursor.close();
        db.close();

        return user;
    }


    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PASSWORD, user.getPassword());
        db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // ... (rest of the code remains the same)

    public long addExerciseData(ExerciseData exerciseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SETS, exerciseData.getSets());
        values.put(COLUMN_REPS, exerciseData.getReps());
        values.put(COLUMN_WEIGHTS, exerciseData.getWeights());

        long insertedId = db.insert(TABLE_EXERCISES, null, values);
        db.close();

        return insertedId;
    }
    // Add methods for managing workout data

    public long addWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_NAME, workout.getName());
        values.put(COLUMN_WORKOUT_DESCRIPTION, workout.getDescription());

        long insertedId = db.insert(TABLE_WORKOUTS, null, values);
        db.close();

        return insertedId;
    }

    public int updateWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_NAME, workout.getName());
        values.put(COLUMN_WORKOUT_DESCRIPTION, workout.getDescription());

        int rowsAffected = db.update(TABLE_WORKOUTS, values, COLUMN_WORKOUT_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});
        db.close();

        return rowsAffected;
    }

    public void deleteWorkout(int workoutId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKOUTS, COLUMN_WORKOUT_ID + " = ?", new String[]{String.valueOf(workoutId)});
        db.close();
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_WORKOUTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_WORKOUT_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_WORKOUT_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_WORKOUT_DESCRIPTION));

                Workout workout = new Workout(id, name, description);
                workouts.add(workout);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return workouts;
    }
    public <T> List<T> getAllData(String tableName, String[] columns, DataParser<T> dataParser) {
        List<T> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(tableName, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                T data = dataParser.parseData(cursor);
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dataList;
    }

    public long addExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAME, exercise.getName());
        values.put(COLUMN_EXERCISE_DESCRIPTION, exercise.getDescription());

        long insertedId = db.insert(TABLE_EXERCISE, null, values);
        db.close();

        return insertedId;
    }

    public int updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE_NAME, exercise.getName());
        values.put(COLUMN_EXERCISE_DESCRIPTION, exercise.getDescription());

        int rowsAffected = db.update(TABLE_EXERCISE, values, COLUMN_EXERCISE_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});
        db.close();

        return rowsAffected;
    }

    public void deleteExercise(int exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISE, COLUMN_EXERCISE_ID + " = ?", new String[]{String.valueOf(exerciseId)});
        db.close();
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_EXERCISE_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_DESCRIPTION));

                Exercise exercise = new Exercise(id, name, description);
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exercises;
    }


}
