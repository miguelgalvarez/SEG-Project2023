package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventManagement extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management_activity);

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
    public void NewEvent(View view){
        Intent intent = new Intent(getApplicationContext(), NewEventActivity.class);
        startActivity(intent);
    }
}
