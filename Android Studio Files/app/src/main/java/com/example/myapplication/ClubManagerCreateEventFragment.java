package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClubManagerCreateEventFragment extends Fragment {

    LinearLayout layout;
    Spinner spinner;
    private Button submitButton;
    private String eventType;

    private EditText distance;
    private EditText level;
    private EditText location;
    private EditText route;
    private EditText start;
    private EditText age;
    private EditText particpants;
    private EditText date;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypeRef = rootRef.child("Event Type");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_manager_create_event, container, false);

        layout = view.findViewById(R.id.layout);
        spinner = view.findViewById(R.id.ddMenu);
        distance = view.findViewById(R.id.distanceText);
        level = view.findViewById(R.id.levelText);
        location = view.findViewById(R.id.LocationText);
        route = view.findViewById(R.id.routeText);
        start = view.findViewById(R.id.startTimeText);
        age = view.findViewById(R.id.ageText);
        particpants = view.findViewById(R.id.participantsText);
        date = view.findViewById(R.id.dateText);


        // Set up the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                //Next step is to set each text view to be invisible if its not in the data base
                //layout.setVisibility(View.INVISIBLE);
                // Item selected from the Spinner

                distance.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                route.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                level.setVisibility(View.VISIBLE);
                age.setVisibility(View.VISIBLE);

                eventType = (String) parentView.getItemAtPosition(position);
                eventTypeRef.child(eventType).child("eventDetails").child("Distance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean distanceValue = dataSnapshot.getValue(Boolean.class);

                        if (distanceValue != null && distanceValue.equals(Boolean.TRUE)) {

                        } else {
                            distance.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                eventTypeRef.child(eventType).child("eventDetails").child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean distanceValue = dataSnapshot.getValue(Boolean.class);

                        if (distanceValue != null && distanceValue.equals(Boolean.TRUE)) {

                        } else {
                            location.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                eventTypeRef.child(eventType).child("eventDetails").child("Route Overview").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean distanceValue = dataSnapshot.getValue(Boolean.class);

                        if (distanceValue != null && distanceValue.equals(Boolean.TRUE)) {

                        } else {
                            route.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                eventTypeRef.child(eventType).child("eventDetails").child("Start Time").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean distanceValue = dataSnapshot.getValue(Boolean.class);

                        if (distanceValue != null && distanceValue.equals(Boolean.TRUE)) {

                        } else {
                            start.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                eventTypeRef.child(eventType).child("registrationRequirements").child("Level").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean distanceValue = dataSnapshot.getValue(Boolean.class);

                        if (distanceValue != null && distanceValue.equals(Boolean.TRUE)) {

                        } else {
                            level.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                eventTypeRef.child(eventType).child("registrationRequirements").child("Age").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean distanceValue = dataSnapshot.getValue(Boolean.class);

                        if (distanceValue != null && distanceValue.equals(Boolean.TRUE)) {

                        } else {
                            age.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        submitButton = view.findViewById(R.id.submit_button2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });

        return view;

    }
    public void submitButton() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference clubManagerRef = database.getReference("Club Manager");

        String username = getArguments().getString("username");
        DatabaseReference userRef = clubManagerRef.child(username);

        DatabaseReference eventsRef = userRef.child("Events");

        // Check if the event type already exists
        eventsRef.orderByValue().equalTo(eventType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Event type already exists, handle accordingly (e.g., show an error message)
                    Toast.makeText(getContext(), "Event type already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Event type does not exist, add a new child to the "Events" node
                    addNewEvent(eventsRef);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if the database query is canceled
                Toast.makeText(getContext(), "Error checking event type: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewEvent(DatabaseReference eventsRef) {
        // Add a new child to the "Events" node with the event type
        eventsRef.push().setValue(eventType, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(getContext(), "Failed to write event: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    eventsRef.child("Distance").setValue(distance.getText().toString());

                    Toast.makeText(getContext(), "Event Updated!", Toast.LENGTH_SHORT).show();
                    // Navigate to the home page or perform other actions as needed
                    if (isAdded()) {
                        getParentFragmentManager().beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.fragmentHolderViewClubManager, new ClubManagerHomePageFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });
    }

}