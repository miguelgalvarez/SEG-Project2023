package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Fragment that represents the page where the admin can add new event types
 * @author zacharysikka
 * @version 1.0
 */
public class NewEventTypeFragment extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypesRef = rootRef.child("Event Type");
    FloatingActionButton backButton;
    boolean[] fieldList = new boolean[9];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event_type, container, false);
        for (int i = 0; i < 9; i++) {
            fieldList[i] = false;
        }

        backButton = view.findViewById(R.id.backButtonNewEvent);
        backButton.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });


        Button myButton = view.findViewById(R.id.submitButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = view.findViewById(R.id.dropdownMenuET);
                String eventType = spinner.getSelectedItem().toString();
                submitButton(eventType);
            }
        });

        for (int i = 0; i<fieldList.length;i++) {

            CheckBox[] checkBoxes = new CheckBox[]{
                    view.findViewById(R.id.checkBox1),
                    view.findViewById(R.id.checkBox2),
                    view.findViewById(R.id.checkBox3),
                    view.findViewById(R.id.checkBox4),
                    view.findViewById(R.id.checkBox5),
                    view.findViewById(R.id.checkBox6),
                    view.findViewById(R.id.checkBox7),
                    view.findViewById(R.id.checkBox8),
                    view.findViewById(R.id.checkBox9)
            };

            CheckBox checkBox = checkBoxes[i];

            final int index = i;
            checkBox.setOnClickListener(v -> {
                fieldList[index] = !fieldList[index];
                checkBox.setChecked(fieldList[index]);
            });
        }

        return view;
    }


    public void submitButton(String eventType) {
        EventType addedEvent;

        // Logic for addedEvent
        if (eventType.equals(EventType.TIME_TRIAL.getName())) {
            addedEvent = EventType.TIME_TRIAL;
        } else if (eventType.equals(EventType.HILL_CLIMB.getName())) {
            addedEvent = EventType.HILL_CLIMB;
        } else if (eventType.equals(EventType.ROAD_STAGE_RACE.getName())) {
            addedEvent = EventType.ROAD_STAGE_RACE;
        } else if (eventType.equals(EventType.ROAD_RACE.getName())) {
            addedEvent = EventType.ROAD_RACE;
        } else if (eventType.equals(EventType.GROUP_RIDE.getName())) {
            addedEvent = EventType.GROUP_RIDE;
        } else {
            addedEvent = null;
        }

        if (addedEvent != null) {
            checkCurrentEventTypes(eventType, addedEvent);
        }
    }

    private void checkCurrentEventTypes(String selectedEventType, EventType addedEvent) {

        eventTypesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild(selectedEventType)) {

                        // The event type already exists
                        Toast.makeText(getActivity(), selectedEventType + " has already been added!", Toast.LENGTH_SHORT).show();
                    } else {

                        // The event type doesn't exist, proceed with adding
                        AdministratorAccount.addEventType(addedEvent, fieldList);
                        Toast toast = Toast.makeText(getActivity(), selectedEventType + " has been added!", Toast.LENGTH_SHORT);
                        toast.show();

                        if (isAdded()) { // Check if fragment is currently added to its activity
                            getParentFragmentManager().beginTransaction()
                                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.fragmentHolderView, new AdminEventManagementFragment())
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                } else {
                    Log.d("firebase", "No event types found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancellation
            }
        });
    }

}
