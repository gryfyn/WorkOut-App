package com.example.keepfit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManageWorkoutActivity extends AppCompatActivity {

    private EditText workoutNameEditText;
    private EditText workoutDescriptionEditText;
    private Button addWorkoutButton;
    private Button updateWorkoutButton;
    private Button deleteWorkoutButton;
    private DatabaseHelper databaseHelper;
    private Workout currentWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_workout);

        workoutNameEditText = findViewById(R.id.workoutNameEditText);
        workoutDescriptionEditText = findViewById(R.id.workoutDescriptionEditText);
        addWorkoutButton = findViewById(R.id.addWorkoutButton);
        updateWorkoutButton = findViewById(R.id.updateWorkoutButton);
        deleteWorkoutButton = findViewById(R.id.deleteWorkoutButton);
        databaseHelper = new DatabaseHelper(this);

        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = workoutNameEditText.getText().toString();
                String description = workoutDescriptionEditText.getText().toString();

                if (isValidData(name)) {
                    Workout workout = new Workout(-1, name, description);
                    long insertedId = databaseHelper.addWorkout(workout);

                    if (insertedId != -1) {
                        workout.setId((int) insertedId);
                        currentWorkout = workout;
                        Toast.makeText(ManageWorkoutActivity.this, "Workout added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManageWorkoutActivity.this, "Failed to add workout.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        updateWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWorkout != null) {
                    String name = workoutNameEditText.getText().toString();
                    String description = workoutDescriptionEditText.getText().toString();

                    if (isValidData(name)) {
                        currentWorkout.setName(name);
                        currentWorkout.setDescription(description);
                        int rowsAffected = databaseHelper.updateWorkout(currentWorkout);

                        if (rowsAffected > 0) {
                            Toast.makeText(ManageWorkoutActivity.this, "Workout updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManageWorkoutActivity.this, "Failed to update workout.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWorkout != null) {
                    databaseHelper.deleteWorkout(currentWorkout.getId());
                    currentWorkout = null;
                    clearFields();
                    Toast.makeText(ManageWorkoutActivity.this, "Workout deleted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        workoutNameEditText.setText("");
        workoutDescriptionEditText.setText("");
    }

    private boolean isValidData(String name) {
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a valid name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
