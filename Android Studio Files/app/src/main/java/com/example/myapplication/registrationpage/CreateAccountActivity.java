package com.example.myapplication.registrationpage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Patterns;
import android.widget.Toast;

import com.example.myapplication.Account;
import com.example.myapplication.AccountType;
import com.example.myapplication.R;
import com.example.myapplication.clubmanager.ClubManagerAccount;
import com.example.myapplication.clubmanager.ClubManagerActivity;
import com.example.myapplication.loginpage.LoginHomePageActivity;
import com.example.myapplication.participant.ParticipantAccount;
import com.example.myapplication.participant.ParticipantActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private EditText clubName;
    private Account newAccount;
    boolean isClubAccount = false;
    boolean isParticipantAccount = false;
    Button registerButton;

    FloatingActionButton backButton;

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
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButtonRegistration);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginHomePageActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountPress(v);
            }
        });
    }

    /**
     * Method that handles writing the new account to the firebase database
     *
     */
    public void writeDatabase(String accountType) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference(accountType);

        DatabaseReference newUserRef = usersRef.child(username.getText().toString());

        if (accountType.equals("Participant")) {
            newUserRef.child("Joined Club").setValue("No club", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error
                        Toast.makeText(CreateAccountActivity.this, "Failed to write: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (accountType.equals(AccountType.CLUB_MANAGER)) {
            // Create a new "club name" node under the user's node
            newUserRef.child("clubname").setValue(clubName.getText().toString(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error
                        Toast.makeText(CreateAccountActivity.this, "Failed to write events: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

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
        newUserRef.child("JoinedEvents").setValue("", new DatabaseReference.CompletionListener() {
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

                showManagerNameDialog();

            } else {

                newAccount = new ParticipantAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), AccountType.PARTICIPANT);
                writeDatabase("Participant");
                Intent intent = new Intent(this, ParticipantActivity.class);
                intent.putExtra("username", newAccount.getUsername());
                intent.putExtra("accountType", newAccount.getAccountType().toString());
                startActivity(intent);

            }

        }

    }

    public void showManagerNameDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.club_name_popup_window, null);

        clubName = dialogView.findViewById(R.id.managerNameInput);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.setCancelable(false); // Prevents dismissing the dialog on back press or touch outside
        dialog.show();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String managerName = clubName.getText().toString();
                if (!managerName.isEmpty()) {
                    displayToast();
                    // Proceed with account creation and start the next activity
                    newAccount = new ClubManagerAccount(username.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), AccountType.CLUB_MANAGER);
                    writeDatabase("Club Manager");
                    Intent intent = new Intent(CreateAccountActivity.this, ClubManagerActivity.class);
                    intent.putExtra("username", newAccount.getUsername());
                    intent.putExtra("accountType", newAccount.getAccountType().toString());
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    clubName.setError("Please enter a club manager name");
                }
            }
        });
    }

    public void displayToast() {
        Toast.makeText(this, "Club Account Created!", Toast.LENGTH_SHORT).show();
    }




}
