package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment screen for the admin event management which shows the active event types
 * and allows the admin to edit, delete, and add event types
 *
 * @author Zachary Sikka
 * @version 1.0
 */
public class AdminEventManagementFragment extends Fragment {

    // Firebase database references
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypesRef = rootRef.child("Event Type");

    // UI elements
    ListView listViewEvents;
    List<EventType> eventTypeList;
    EventTypeList eventAdapter;
    DataSnapshot eventTypeSnapshot;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_event_management, container, false);

        // Setup 'New Event Type' button and its click listener
        Button myButton = view.findViewById(R.id.NewEventTypeButtonView);
        myButton.setOnClickListener(v -> goToNextFragment());

        // Initialize the event type list and adapter
        eventTypeList = new ArrayList<>();
        listViewEvents = view.findViewById(R.id.eventTypeList);
        eventAdapter = new EventTypeList(getActivity(), eventTypeList);
        listViewEvents.setAdapter(eventAdapter);

        // Set up item click listener for list view
        listViewEvents.setOnItemClickListener((adapterView, view1, position, l) -> {
            int index = 0;
            for (DataSnapshot childSnapshot : eventTypeSnapshot.getChildren()) {
                if (index == position) {
                    DatabaseReference eventTypeCurrentRef = childSnapshot.getRef();
                    eventTypeCurrentRef.removeValue();
                }
                index++;
            }
        });

        // Setup listener for editing event types
        eventAdapter.setEventTypeListListener(eventTypeName -> {
            EditEventTypeFragment fragment = new EditEventTypeFragment();
            Bundle args = new Bundle();
            args.putString("eventTypeName", eventTypeName);
            fragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentHolderView, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Attach a ValueEventListener to listen for changes in the Firebase database
        eventTypesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                // Update the eventTypeList with the latest data
                eventTypeSnapshot = datasnapshot;
                eventTypeList.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
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
                // Handle potential errors
            }
        });
    }

    // Method to navigate to the 'NewEventTypeFragment'
    public void goToNextFragment() {
        if (isAdded()) {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragmentHolderView, new NewEventTypeFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
