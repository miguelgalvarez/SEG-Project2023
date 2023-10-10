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

import android.util.Patterns;

public class CreatingAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_account);
    }

    public boolean validate(View view){
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        if (!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            return true;
        }
        return false;
    }

    public void CreateAccount(View view) {

        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        if (!email.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivityForResult(intent, 0);
        } else {
            email.setHint("Please enter a password");
            email.setHintTextColor(rgb(255,0,0));
        }


    }
}