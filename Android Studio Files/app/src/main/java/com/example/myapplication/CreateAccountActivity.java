package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Patterns;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize your EditText views inside the onCreate method
        firstName = findViewById(R.id.firstNamefield);
        lastName = findViewById(R.id.lastNameField);
        email = findViewById(R.id.editTextTextEmailAddress);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
    }

    public boolean validate(View view) {
        if (!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            return true;
        }
        return false;
    }

    public boolean CreateAccount(View view) {
        if (firstName.getText().toString().isEmpty()) {
            firstName.setError("Please enter a first name");
            return false;
        }

        if (lastName.getText().toString().isEmpty()) {
            lastName.setError("Please enter a last name");
            return false;
        }

        if (username.getText().toString().isEmpty()) {
            username.setError("Please enter a username");
            return false;
        }

        if (!(!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())) {
            email.setError("Please enter a valid email");
            return false;
        }

        // If all fields are valid, return true
        return true;
    }

    // The rest of your code (account creation, intent, etc.) can remain unchanged.

    boolean isClubAccount = false;
    boolean isParticipantAccount = false;

    public void CreateClubAccount(View view) {
        isClubAccount = true;
        isParticipantAccount = false;

    }

    public void CreateParticipantAccount(View view) {
        isClubAccount = false;
        isParticipantAccount = true;
    }

    public void createAccountPress(View view) {

        boolean flag = true;

        if (firstName.getText().toString().isEmpty()) {
            firstName.setError("Please enter a first name");
            flag = false;
        }

        if (lastName.getText().toString().isEmpty()) {
            lastName.setError("Please enter a last name");
            flag = false;
        }

        if (username.getText().toString().isEmpty()) {
            username.setError("Please enter a username");
            flag = false;
        }

        if (!(!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())) {
            email.setError("Please enter a valid email");
            flag = false;
        }

        if (flag == true) {

            if (isClubAccount == true) {
                ClubManagerAccount managerAccount = new ClubManagerAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), "Club Manager");
            } else if (isParticipantAccount == true) {
                ParticipantAccount participantAccount = new ParticipantAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), "Club Manager");
            }

            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivityForResult (intent,0);
        }



    }


}