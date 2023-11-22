package com.example.myapplication;

import static android.graphics.Color.rgb;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Login Activity that display the main login/signup screen for our cycling app
 *
 * @author Android Studio, Zachary Sikka, Zachary Kranabetter, Migeul Alvarez
 * @version 1.0
 */
public class LoginHomePageActivity extends AppCompatActivity {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference clubManagerRef = rootRef.child("Club Manager");

    DatabaseReference participantRef = rootRef.child("Participant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home_page);
    }


    /**
     * Method that represents the action when pressing the login button
     *
     * @param view gives information about the UI components
     */
    public void AdminLogin(View view) {

        Account newAccount = new AdministratorAccount("admin", "admin", "ADMIN", "ADMIN", "ADMIN@admin.com", AccountType.ADMINISTRATOR);
        EditText adminUsername = (EditText) findViewById(R.id.usernameHint);
        EditText adminPassword = (EditText) findViewById(R.id.passwordHint);

        String username = adminUsername.getText().toString();
        String password = adminPassword.getText().toString();

        if (username.isEmpty()) {
            adminUsername.setError("Please enter a username");
        }

        if (password.isEmpty()) {
            adminPassword.setError("Please enter a password");
        }

        if (!username.isEmpty() && !password.isEmpty()) {
            adminLogin(username, password, success -> {
                if (!success) {
                    clubManagerLogin(username, password, success1 -> {
                        if (!success1) {
                            participantLogin(username, password, success2 -> {
                                if (!success2) {
                                    Toast.makeText(getBaseContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }

    }

    public void clubManagerLogin(String clubManagerUsername, String clubManagerPassword, LoginCallBack callback){
        clubManagerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild(clubManagerUsername)) {
                        if (dataSnapshot.child(clubManagerUsername).child("password").getValue(String.class).equals(clubManagerPassword)) {
                            login(clubManagerUsername, "Club Manager");
                            Toast.makeText(getBaseContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            callback.onLoginResult(true);
                        } else {
                            callback.onLoginResult(false);
                        }
                    } else {
                        callback.onLoginResult(false);
                    }

                } else {
                    Log.d("firebase", "No club managers found");
                    callback.onLoginResult(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onLoginResult(false);
            }
        });

    }


    public void participantLogin(String participantUsername, String participantPassword, LoginCallBack callback){

        participantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild(participantUsername)) {
                        if (dataSnapshot.child(participantUsername).child("password").getValue(String.class).equals(participantPassword)) {
                            login(participantUsername, "Participant");
                            Toast.makeText(getBaseContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            callback.onLoginResult(true);
                        } else {
                            callback.onLoginResult(false);
                        }
                    } else {
                        callback.onLoginResult(false);
                    }

                } else {
                    Log.d("firebase", "no participants found");
                    callback.onLoginResult(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onLoginResult(false);
            }
        });

    }



    public void adminLogin(String adminUsername, String adminPassword, LoginCallBack callback){

        if (adminUsername.equals("admin") && adminPassword.equals("admin")) {
            login(adminUsername, "Admin");
            Toast.makeText(getBaseContext(), "Login successful!", Toast.LENGTH_SHORT).show();
            callback.onLoginResult(true);
        } else {
            callback.onLoginResult(false);
        }
    }




    public void login(String username, String accountType) {
        Intent intent;
        if (accountType.equals("Club Manager")) {
            intent = new Intent(this, WelcomeClubManagerActivity.class);
        } else if (accountType.equals("Participant")){
            intent = new Intent(this, WelcomeActivity.class);
        } else {
            intent = new Intent(this, AdminActivity.class);
        }

        intent.putExtra("username", username);
        intent.putExtra("accountType", accountType);
        startActivity(intent);
    }

    /**
     * Method that represents the action when pressing the Create Account button
     * On-Click method that will be executed when the user clicks on the register button
     * @param view gives information about the UI components
     */
    public void CreateAccountLink(View view) {

        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        //Intent intent = new Intent(getApplicationContext(), WelcomeClubManagerActivity.class);
        startActivity(intent);
    }
    
}