package com.example.myapplication.participant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantSearchEventFragement extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ClubsRef = rootRef.child("Club Manager");

    Spinner spinnerClubNames; // Stores the drop down for club name filter

    // Stuff for the dynamic event list
    List<SearchEvent> searchEvents;
    ListView listSearchEvents;
    SearchEventList searchEventAdapter;

    public ParticipantSearchEventFragement() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_search, container, false);

        Bundle args = getArguments();
        String username = args != null ? args.getString("username") : "N/A";

        // Find drop down element from xml
        spinnerClubNames = view.findViewById(R.id.ClubDropDown);

        // Create the dynamic list of joinable events
        // This just reuses the ActiveEvent class which was used for the club manager home page
        searchEvents = new ArrayList<SearchEvent>();
        listSearchEvents = view.findViewById(R.id.eventSearchList);
        searchEventAdapter = new SearchEventList(getActivity(), searchEvents, username);
        listSearchEvents.setAdapter(searchEventAdapter);



        populateDropDown();

        return view;
    }

    public void onStart() {
        super.onStart();

        // If the database references failed, skip displaying the active event list
        if (ClubsRef == null) {
            Log.d("debug", "failed to load db");
            return;
        }

        // Attach ValueEventListener to active events under the manager account in Firebase
        ClubsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                searchEvents.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {

                    // Load the events from the club account
                    DataSnapshot clubEventsRef = childSnapshot.child("Events");
                    String clubName = childSnapshot.child("clubname").getValue(String.class);

                    for (DataSnapshot clubEvent : clubEventsRef.getChildren()) {

                        // Get Strings
                        String eventTypeString = clubEvent.getKey();
                        String eventName = clubEvent.child("Name").getValue(String.class);

                        // Try and add the event to the dynamic list
                        if (eventTypeString != null) {
                            SearchEvent activeEvent = new SearchEvent(eventName, eventTypeString, clubName);
                            searchEvents.add(activeEvent);
                        }
                    }
                }
                searchEventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
            }
        });
    }

    public void populateDropDown() {
        // Clear existing items
        spinnerClubNames.setAdapter(null);

        //spinnerClubNames.setAdapter(null);

        ClubsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // List to hold drop down items
                    List<String> clubNames = new ArrayList<String>();

                    // give it a default String of "Club Name"
                    clubNames.add("Club Name");

                    // Populate drop down from database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String clubName = snapshot.child("clubname").getValue(String.class);
                        if (clubName != null) {
                            clubNames.add(clubName);
                        }
                    }

                    // Populate the spinner with items from the database
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, clubNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClubNames.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(getContext(), "Error retrieving event types: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
