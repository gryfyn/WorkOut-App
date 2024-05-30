package com.example.keepfit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private ListView workoutHistoryListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        workoutHistoryListView = findViewById(R.id.workoutHistoryListView);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve workout history from the database
        List<Workout> workoutHistory = databaseHelper.getAllWorkouts();

        // Display workout history in the ListView
        ArrayAdapter<Workout> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workoutHistory);
        workoutHistoryListView.setAdapter(adapter);
    }
}
