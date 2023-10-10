package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


    }

    public void CreateAccount(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult (intent,0);

    }
}