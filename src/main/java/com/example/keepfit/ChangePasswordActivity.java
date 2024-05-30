package com.example.keepfit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private Button changePasswordButton;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the current user's data from the Intent extras or SharedPreferences
        // For this example, let's assume we have passed the current user's email as an extra
        String currentUserEmail = getIntent().getStringExtra("currentUserEmail");
        currentUser = databaseHelper.getUserByEmail(currentUserEmail);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform password change
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (currentPassword.equals(currentUser.getPassword())) {
                    // Assuming you have a setPassword() method in the User class
                    currentUser.setPassword(newPassword);

                    // Update the user's password in the database
                    // Assuming you have a updateUser() method in the DatabaseHelper class
                    databaseHelper.updateUser(currentUser);

                    Toast.makeText(ChangePasswordActivity.this, "Password changed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Incorrect current password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
