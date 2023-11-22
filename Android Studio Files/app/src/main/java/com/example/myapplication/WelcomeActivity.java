package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Welcome Activity that displays the welcome screen when users sign up, displays their username and account type
 *
 * @author Android Studio, Zachary Sikka, Zachary Kranabetter, Miguel Alvarez
 * @version 1.0
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");

        // Find the TextViews in the UI layout
        TextView usernameTextView = findViewById(R.id.usernameText);
        TextView accountTypeTextView = findViewById(R.id.userRole);

        //setting the UI components(TextViews) to display the username and account type
        usernameTextView.setText(username);
        accountTypeTextView.setText(accountType);

        Button logoutButton = findViewById(R.id.logoutButton2);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginHomePageActivity.class);
                startActivity(intent);
            }
        });


    }

}