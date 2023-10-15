package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Patterns;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.google.firebase.database.FireBaseDatabase;
//import com.google.firebase.database.DataBaseReference;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText password;
    private String accountType;

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

    public void writeDatabase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        DatabaseReference newUserRef = usersRef.child(username.getText().toString());

        newUserRef.child("usertype").setValue(accountType, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateAccountActivity.this, "Failed to write usertype: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUserRef.child("email").setValue(email.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateAccountActivity.this, "Failed to write email: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUserRef.child("password").setValue(password.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateAccountActivity.this, "Failed to write password: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newUserRef.child("username").setValue(username.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateAccountActivity.this, "Failed to write username: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


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

        if (isClubAccount == false && isParticipantAccount == false) {
            flag = false;
            Toast toast = Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (flag == true) {

            Account newAccount;
            if (isClubAccount == true) {

                newAccount = new ClubManagerAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), AccountType.CLUB_MANAGER);

            } else {

                newAccount = new ParticipantAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), AccountType.PARTICIPANT);

            }

            writeDatabase();

            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("username", newAccount.getUsername());
            intent.putExtra("accountType", newAccount.getAccountType().toString());
            startActivity(intent);
        }



    }


}
