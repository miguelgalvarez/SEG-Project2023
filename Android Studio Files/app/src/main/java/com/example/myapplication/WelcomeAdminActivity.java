package com.example.myapplication;

import android.os.Bundle;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeAdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_admin_activity);

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
    // method that will serve as the OnClick to bring the user to the delete account page
    public void DeleteAccount(View view){
        Intent intent = new Intent(getApplicationContext(), DeleteAccount.class);
        startActivity(intent);
    }
    // method that will serve as the OnClick to bring the user to the event management page
    public void EventManagement(View view){
        Intent intent = new Intent(getApplicationContext(), EventManagement.class);
        startActivity(intent);
    }
}
