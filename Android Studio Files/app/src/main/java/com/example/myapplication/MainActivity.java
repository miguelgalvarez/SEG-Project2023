package com.example.myapplication;

import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.net.Uri;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Main Activity that display the main login/signup screen for our cycling app
 *
 * @author Android Studio, Zachary Sikka, Zachary Kranabetter, Migeul Alvarez
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        if (adminPassword.getText().toString().equals("admin") && adminUsername.getText().toString().equals("admin")) {
            // intent is for the club/participant account type
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            // intent1 is for the admin account type
            Intent intent1 = new Intent(getApplicationContext(), WelcomeAdminActivity.class);
            intent1.putExtra("username", newAccount.getUsername());
            intent1.putExtra("accountType", newAccount.getAccountType());
            // will redirect admin to the admin home page if the user account type is an admin
            // if it is a normal club or participant user, then redirect to the current home page
            // for now will just have a static login thing
            startActivity(intent1);

        } else {

            //error message that displays at the bottom of the screen
            Toast toast = Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT);
            toast.show();
        }


        if (adminUsername.getText().toString().isEmpty()) {

            adminUsername.setError("Please enter a username");

        }

        if (adminPassword.getText().toString().isEmpty()) {

            adminPassword.setError("Please enter a password");
        }
    }

    /**
     * Method that represents the action when pressing the Create Account button
     * On-Click method that will be executed when the user clicks on the register button
     * @param view gives information about the UI components
     */
    public void CreateAccountLink(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(intent);
    }
    
}