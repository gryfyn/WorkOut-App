package com.example.keepfit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        nameEditText = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the current user's data from the Intent extras or SharedPreferences
        // For this example, let's assume we have passed the current user's email as an extra
        String currentUserEmail = getIntent().getStringExtra("currentUserEmail");
        currentUser = databaseHelper.getUserByEmail(currentUserEmail);

        if (currentUser != null) {
            nameEditText.setText(currentUser.getName());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the updated profile data to the database
                String newName = nameEditText.getText().toString();

                // Update the user's name in the user object
                currentUser.setName(newName);

                // Update the user's profile data in the database
                databaseHelper.updateUser(currentUser);

                Toast.makeText(UpdateProfileActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
