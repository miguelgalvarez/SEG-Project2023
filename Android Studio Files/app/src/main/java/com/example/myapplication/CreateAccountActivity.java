package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Create Account Activity that displays the main signup screen for users to create an account
 *
 * @author Android Studio, Zachary Sikka, Zachary Kranabetter, Migeul Alvarez
 * @version 1.0
 */

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText password;
    private Account newAccount;
    boolean isClubAccount = false;
    boolean isParticipantAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialize EditText views
        firstName = findViewById(R.id.firstNamefield);
        lastName = findViewById(R.id.lastNameField);
        email = findViewById(R.id.editTextTextEmailAddress);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
    }

    /**
     * Method that handles writing the new account to the firebase database
     *
     */
    public void writeDatabase(String accountType) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(accountType);

        DatabaseReference newUserRef = usersRef.child(username.getText().toString());

        newUserRef.child("usertype").setValue(newAccount.getAccountType().toString(), new DatabaseReference.CompletionListener() {
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

        // Create a new "Events" node under the user's node
        newUserRef.child("Events").setValue("", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateAccountActivity.this, "Failed to write events: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Method that is mapped to the create club account radio button
     *
     * @param view gives information about the UI components
     */
    public void CreateClubAccount(View view) {
        isClubAccount = true;
        isParticipantAccount = false;

    }

    /**
     * Method that is mapped to the create participant account radio button
     *
     * @param view gives information about the UI components
     */
    public void CreateParticipantAccount(View view) {
        isClubAccount = false;
        isParticipantAccount = true;
    }

    /**
     * Method that is mapped to the create account button, handles all validation of fields and navigating to welcome page
     *
     * @param view gives information about the UI components
     */
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

            if (isClubAccount == true) {

                newAccount = new ClubManagerAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), AccountType.CLUB_MANAGER);
                writeDatabase("Club Manager");
                Intent intent = new Intent(this, WelcomeClubManagerActivity.class);
                intent.putExtra("username", newAccount.getUsername());
                intent.putExtra("accountType", newAccount.getAccountType().toString());
                startActivity(intent);


            } else {

                newAccount = new ParticipantAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), AccountType.PARTICIPANT);
                writeDatabase("Participant");
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra("username", newAccount.getUsername());
                intent.putExtra("accountType", newAccount.getAccountType().toString());
                startActivity(intent);


            }

        }

    }


}
