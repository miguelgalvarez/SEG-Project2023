package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypesRef = rootRef.child("Event Type");
    ListView listViewEvents;
    List<EventType> eventTypeList;

    DataSnapshot evenTypeSnapshot;
    EventTypeList eventAdapter;

    FloatingActionButton backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event_activity);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        eventTypeList = new ArrayList<>();

        listViewEvents = findViewById(R.id.eventTypeList);

        eventAdapter = new EventTypeList(this, eventTypeList);

        listViewEvents.setAdapter(eventAdapter);

        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int index = 0;
                for (DataSnapshot childSnapshot : evenTypeSnapshot.getChildren()) {
                    if (index == position) {
                        DatabaseReference evenTypeCurrentRef = childSnapshot.getRef();
                        evenTypeCurrentRef.removeValue();
                    }
                    index++;
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        eventTypesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                evenTypeSnapshot = datasnapshot;

                eventTypeList.clear();

                for(DataSnapshot childSnapshot: datasnapshot.getChildren()) {

                    String eventTypeString = childSnapshot.getKey();

                    if (eventTypeString != null) {
                        EventType eventType = EventType.fromString(eventTypeString);
                        eventTypeList.add(eventType);
                    }
                }

                eventAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
