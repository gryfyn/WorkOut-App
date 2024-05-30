package com.example.keepfit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ExerciseLibraryActivity extends AppCompatActivity {

    private ListView exerciseListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_library);

        exerciseListView = findViewById(R.id.exerciseListView);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve all exercises from the database
        List<Exercise> exercises = databaseHelper.getAllExercises();

        // Display exercises in the ListView
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exercises);
        exerciseListView.setAdapter(adapter);
    }
}
