package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CreateEvent extends AppCompatActivity {

    FloatingActionButton backButton;
    LinearLayout layout;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypeRef = rootRef.child("Event Type");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);

        layout = findViewById(R.id.layout);

        backButton = findViewById(R.id.backButtonCreateEvent);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Find the Spinner by ID
        Spinner spinner = findViewById(R.id.ddMenu);

        // Set up the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                layout.removeAllViews();
                // Item selected from the Spinner
                String eventType = (String) parentView.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Selected Event Type: " + eventType, Toast.LENGTH_SHORT).show();

                //chage to real check
                if (1==1) {
                    EditText distanceText = new EditText(CreateEvent.this);
                    distanceText.setHint("Distance");
                    layout.addView(distanceText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if nothing is selected
            }
        });
    }
    public void submitButton(View view) {


        //does not work rn

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Club Manager");
        Intent intent = getIntent();


        DatabaseReference newUserRef = usersRef.child(intent.getStringExtra("username"));

        newUserRef.child("Event").setValue(instagram.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateEvent.this, "Failed to write Event: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        // back to last page
        onBackPressed();
    }
}
