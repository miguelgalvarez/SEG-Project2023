package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.net.Uri;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void AdminLogin(View view) {

        EditText adminPassword = (EditText) findViewById(R.id.usernameHint);
        EditText adminUsername = (EditText) findViewById(R.id.passwordHint);

        //Application Context and Activity
        if (adminPassword.getText().toString().equals("Admin") && adminUsername.getText().toString().equals("Admin")) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivityForResult (intent,0);
        }
    }
    public void Login(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult (intent,0);

    }

    public void CreateAccount(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivityForResult (intent,0);

    }

    public void createAccount(String email, String username, String Password) {
    }
}