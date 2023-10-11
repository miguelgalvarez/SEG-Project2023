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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void AdminLogin(View view) {

        EditText adminUsername = (EditText) findViewById(R.id.usernameHint);
        EditText adminPassword = (EditText) findViewById(R.id.passwordHint);

        //Application Context and Activity
        if (adminPassword.getText().toString().equals("Admin") && adminUsername.getText().toString().equals("Admin")) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivityForResult (intent,0);
        }

        if (adminUsername.getText().toString().equals("")) {
            adminUsername.setHint("Please enter a username");
            adminUsername.setHintTextColor(rgb(255,0,0));

        }

        if (adminPassword.getText().toString().equals("")) {
            adminPassword.setHint("Please enter a password");
            adminPassword.setHintTextColor(rgb(255,0,0));
        }
    }

    public void CreateAccountLink(View view) {
        //Application Context and Activity
        Log.d("Testing", "right method was run");
        Intent intent = new Intent(getApplicationContext(), CreatingAccountActivity.class);
        startActivityForResult (intent,0);
    }
    
}