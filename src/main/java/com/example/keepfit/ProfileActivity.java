package com.example.keepfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView emailTextView;
    private Button updateProfileButton;
    private Button changePasswordButton;
    private Button logoutButton;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        emailTextView = findViewById(R.id.emailTextView);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        logoutButton = findViewById(R.id.logoutButton);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the current user's data from the Intent extras or SharedPreferences
        // For this example, let's assume we have passed the current user's email as an extra
        String currentUserEmail = getIntent().getStringExtra("currentUserEmail");
        currentUser = databaseHelper.getUserByEmail(currentUserEmail);

        if (currentUser != null) {
            emailTextView.setText(currentUser.getEmail());
        }

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the UpdateProfileActivity
                Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                intent.putExtra("currentUserEmail", currentUser.getEmail());
                startActivity(intent);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the ChangePasswordActivity
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                intent.putExtra("currentUserEmail", currentUser.getEmail());
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout action
                // For this example, we simply finish the activity to simulate logout
                Toast.makeText(ProfileActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
