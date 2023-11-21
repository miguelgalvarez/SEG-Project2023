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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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


    //LinearLayout layout = findViewById(R.id.layout);

    //Spinner spinner = (Spinner) findViewById(R.id.ddMenu);
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypeRef = rootRef.child("Event Type");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);


        backButton = findViewById(R.id.backButtonCreateEvent);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //Spinner spinner = (Spinner) findViewById(R.id.dropdownMenuCE);
    //String eventType = spinner.getSelectedItem().toString();

    /*public void select(View view) {

        //check whats selected
        DatabaseReference currentEvent = eventTypeRef.child(spinner.getSelectedItem().toString());

        DatabaseReference currentEventDetails = currentEvent.child("eventDetails");


        //check if it has distance true
        //create a distance text field

        if (currentEventDetails.child("Distance").toString().equals("true")) {
            TextView distanceTextView = new TextView(CreateEvent.this);
            distanceTextView.setText("Distance: ");
            layout.addView(distanceTextView);
        }

        DataSnapshot eventSnapshot;

        List<EventTypeList> eventList;
    }*/

}
