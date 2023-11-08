package com.example.myapplication;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    DatabaseReference databaseEventTypes;

    ListView listViewEvents;
    List<EventType> eventTypeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event_activity);

        databaseEventTypes = FirebaseDatabase.getInstance().getReference("Event Type");
        eventTypeList = new ArrayList<>();

        listViewEvents = findViewById(R.id.eventTypeList);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEventTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                eventTypeList.clear();

                for(DataSnapshot postSnapshot: datasnapshot.getChildren()) {
                    String eventTypeString = postSnapshot.getValue(String.class);

                    EventType eventType = EventType.fromString(eventTypeString);

                    eventTypeList.add(eventType);
                }

                EventTypeList eventAdapter = new EventTypeList(EditEventActivity.this, eventTypeList);

                listViewEvents.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
