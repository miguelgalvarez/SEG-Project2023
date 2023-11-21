package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeClubManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_club_manager_actvity);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");

        // Find the TextViews in the UI layout
        TextView usernameTextView = findViewById(R.id.usernameText);
        TextView accountTypeTextView = findViewById(R.id.userRole);

        //setting the UI components(TextViews) to display the username and account type
        usernameTextView.setText(username);
        accountTypeTextView.setText(accountType);
    }

    //method that will serve as the OnClick to bring the user to the create event page
    public void CreateEvent(View view){
        Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
        Intent get = getIntent();
        intent.putExtra("username", get.getStringExtra("username"));
        startActivity(intent);
    }

    //method that will serve as the OnClick to bring the user to the Edit Profile page
    public void EditProfile(View view){
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
        Intent get = getIntent();
        intent.putExtra("username", get.getStringExtra("username"));
        startActivity(intent);
    }

}
