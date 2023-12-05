package com.example.myapplication.participant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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

public class ParticipantSearchEventFragment extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ClubsRef = rootRef.child("Club Manager");

    Spinner clubNameSpinner; // Stores the drop down for club name filter
    List<String> clubNames; // Stores the options for the club name drop down
    Spinner eventTypeSpinner; // Stores the drop down for event type filter
    List<String> eventTypes; // Stores the options for the event type drop down

    // Stuff for the dynamic event list
    List<SearchEvent> allSearchEvents;
    List<SearchEvent> shownSearchEvents;
    ListView listSearchEvents;
    SearchEventList searchEventAdapter;

    // Holds the current search fields
    String searchFilter;
    String clubNameFilter;
    String eventTypeFilter;
    String username;


    public ParticipantSearchEventFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_search, container, false);

        Bundle args = getArguments();
        username = args != null ? args.getString("username") : "N/A";

        // Create the dynamic list of joinable events
        // This just reuses the ActiveEvent class which was used for the club manager home page
        allSearchEvents = new ArrayList<SearchEvent>();
        shownSearchEvents = new ArrayList<SearchEvent>();
        listSearchEvents = view.findViewById(R.id.eventSearchList);
        searchEventAdapter = new SearchEventList(getActivity(), shownSearchEvents, username);
        listSearchEvents.setAdapter(searchEventAdapter);

        // Setup listener for editing event types
        searchEventAdapter.setEventJoinListener(viewHolder  -> {
            ParticipantJoinEventFragment fragment = new ParticipantJoinEventFragment();
            Bundle outArgs = new Bundle();
            outArgs.putString("eventName", viewHolder.eventName.getText().toString());
            outArgs.putString("eventType", viewHolder.eventType.getText().toString());
            outArgs.putString("clubName", viewHolder.clubName.getText().toString());
            outArgs.putString("clubUsername", viewHolder.clubUsername);
            outArgs.putString("username", username);
            fragment.setArguments(outArgs);

            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentHolderViewParticipant, fragment)
                .addToBackStack(null)
                .commit();
        });

        // Set up for the search bar functionality
        SearchView searchView = view.findViewById(R.id.searchbar);
        searchFilter = "";

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query
                // Nothing right now
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text changes
                // newText contains the updated query text

                searchFilter = newText;

                populateList();

                return true; // Return true to indicate that the query text change event has been handled
            }
        });

        // Set up the drop down menus
        clubNameSpinner = view.findViewById(R.id.ClubDropDown);
        eventTypeSpinner = view.findViewById(R.id.EventTypeDropDown);

        populateDropDowns();

        clubNameFilter = "Club Name";
        eventTypeFilter = "Event Type";

        clubNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Called when an item is selected
                // position is the index of the selected item in the spinnerOptions array

                clubNameFilter = clubNames.get(position);

                populateList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Called when nothing is selected
            }
        });

        eventTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Called when an item is selected
                // position is the index of the selected item in the spinnerOptions array

                eventTypeFilter = eventTypes.get(position);

                populateList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Called when nothing is selected
            }
        });

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
                allSearchEvents.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {

                    String clubUsername = childSnapshot.getKey();
                    Log.d("DEBUG", "username is " + clubUsername);
                    // Load the events from the club account
                    DataSnapshot clubEventsRef = childSnapshot.child("Events");
                    String clubName = childSnapshot.child("clubname").getValue(String.class);

                    for (DataSnapshot clubEvent : clubEventsRef.getChildren()) {

                        // Get Strings
                        String eventTypeString = clubEvent.getKey();
                        String eventName = clubEvent.child("Name").getValue(String.class);

                        // Try and add the event to the allSearchEvents list
                        if (eventTypeString != null) {
                            SearchEvent activeEvent = new SearchEvent(eventName, eventTypeString, clubName, clubUsername, username);
                            allSearchEvents.add(activeEvent);
                        }
                    }
                }

                // Call the populate list function when the data is fetched
                populateList();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
            }
        });
    }

    public void populateList() {

        // Reset the list
        shownSearchEvents.clear();

        // Conditionally add all the search events to the shown list
        for (SearchEvent event : allSearchEvents) {

            if (!clubNameFilter.equals("Club Name")) {
                try {
                    if (!event.getClubName().equals(clubNameFilter)) {
                        continue;
                    }

                // Happens when the event has no club name, shouldn't happen in actual use
                } catch (Exception e) {
                    shownSearchEvents.add(event);
                    continue;
                }
            }

            if (!eventTypeFilter.equals("Event Type")) {
                try {
                    if (!event.getEventType().equals(eventTypeFilter)) {
                        continue;
                    }

                // Happens when the event has no event type, shouldn't happen in actual use
                } catch (Exception e) {
                    shownSearchEvents.add(event);
                    continue;
                }
            }

            // If theres nothing in the search, add the item and skip the rest of the loop code
            if (searchFilter.length() == 0) {
                shownSearchEvents.add(event);
                continue;
            }

            char[] eventNameChars;
            char[] searchFieldChars;

            try {
                eventNameChars = event.getEventName().toCharArray();
                searchFieldChars = searchFilter.toCharArray();

            // This happens when the event doesn't have a name, so just show it anyway
            // This shouldn't ever happen unless the event was added manually in the database
            } catch (Exception e) {
                shownSearchEvents.add(event);
                continue;
            }

            // Search cannot be valid if its longer than the result
            if (searchFieldChars.length > eventNameChars.length) continue;

            // The search must check every char where the search string can fit
            int iterations = eventNameChars.length - searchFieldChars.length;

            // Need to check this many chars per iteration
            int subIterations = searchFieldChars.length;

            for (int i = 0; i <= iterations; i++) {
                Boolean containsSearch = true;

                // Check each char of the search against each char of the event name ( from a different offset every outer loop iteration )
                // If anything isn't equal, skip this iteration ( the iteration of the changing offset, so move to a different offset )
                for (int j = i; j < (i + subIterations); j++) {
                    if (eventNameChars[j] != searchFieldChars[j - i]) {
                        containsSearch = false;
                        break;
                    }
                }

                if (containsSearch) {
                    shownSearchEvents.add(event);
                    break;
                }
            }
        }

        // Update UI list
        searchEventAdapter.notifyDataSetChanged();
    }

    public void populateDropDowns() {

        /* ----- For club name drop down ----- */
        // Clear existing items
        clubNameSpinner.setAdapter(null);

        ClubsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // List to hold drop down items
                    clubNames = new ArrayList<String>();

                    // give it a default String of "Club Name"
                    clubNames.add("Club Name");

                    // Populate list from database
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String clubName = snapshot.child("clubname").getValue(String.class);
                        if (clubName != null) {
                            clubNames.add(clubName);
                        }
                    }

                    // Populate the spinner with items from the list
                    ArrayAdapter<String> clubNameAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, clubNames);
                    clubNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    clubNameSpinner.setAdapter(clubNameAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(getContext(), "Error retrieving event types: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /* ----- For event type drop down ----- */

        eventTypeSpinner.setAdapter(null);

        eventTypes = new ArrayList<String>();

        eventTypes.add("Event Type"); // Default option, filters nothing
        eventTypes.add("Time Trial");
        eventTypes.add("Hill Climb");
        eventTypes.add("Road Stage Race");
        eventTypes.add("Road Race");
        eventTypes.add("Group Ride");

        ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, eventTypes);
        eventTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventTypeSpinner.setAdapter(eventTypeAdapter);

    }

}
