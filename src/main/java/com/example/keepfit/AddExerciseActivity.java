package com.example.keepfit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddExerciseActivity extends AppCompatActivity {

    private EditText setsEditText;
    private EditText repsEditText;
    private EditText weightsEditText;
    private Button addExerciseButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        setsEditText = findViewById(R.id.setsEditText);
        repsEditText = findViewById(R.id.repsEditText);
        weightsEditText = findViewById(R.id.weightsEditText);
        addExerciseButton = findViewById(R.id.addExerciseButton);
        databaseHelper = new DatabaseHelper(this);

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String setsString = setsEditText.getText().toString();
                String repsString = repsEditText.getText().toString();
                String weightsString = weightsEditText.getText().toString();

                // Validate the input data
                if (isValidData(setsString, repsString, weightsString)) {
                    int sets = Integer.parseInt(setsString);
                    int reps = Integer.parseInt(repsString);
                    double weights = Double.parseDouble(weightsString);

                    // Save the exercise data to the database
                    ExerciseData exerciseData = new ExerciseData(sets, reps, weights);
                    long insertedId = databaseHelper.addExerciseData(exerciseData);

                    if (insertedId != -1) {
                        Toast.makeText(AddExerciseActivity.this, "Exercise data added!", Toast.LENGTH_SHORT).show();
                        // Optionally, you can navigate back to the previous activity after adding the data
                        finish();
                    } else {
                        Toast.makeText(AddExerciseActivity.this, "Failed to add exercise data.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Data validation function
    private boolean isValidData(String sets, String reps, String weights) {
        // Perform your data validation checks here
        // For example, check if the fields are not empty and are valid numbers
        if (sets.isEmpty() || reps.isEmpty() || weights.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int setsValue = Integer.parseInt(sets);
            int repsValue = Integer.parseInt(reps);
            double weightsValue = Double.parseDouble(weights);

            // Add more validation checks if required
            // For instance, you can check if sets, reps, and weights are greater than 0

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
