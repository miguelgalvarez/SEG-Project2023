package com.example.myapplication.participant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Levels;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParticipantJoinEventFragment extends Fragment {

    String eventName;
    String eventType;
    String clubName;
    String clubUsername;
    String level;
    String age;
    String participantUsername;
    Button submitButton;

    private TextView eventNameView;
    private TextView eventTypeView;
    private TextView clubNameView;
    private TextView distanceView;
    private TextView locationView;
    private TextView routeView;
    private TextView startTimeView;
    private TextView dateView;
    private EditText levelView;
    private EditText ageView;
    private boolean levelInput;
    private boolean ageInput;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_participant_join_event, container, false);

        //receiving arguments
        Bundle args = getArguments();
        eventName = args.getString("eventName");
        eventType = args.getString("eventType");
        clubName = args.getString("clubName");
        clubUsername = args.getString("clubUsername");
        participantUsername = args.getString("username");

        //initializing text views
        eventNameView = view.findViewById(R.id.eventNameID);
        eventTypeView = view.findViewById(R.id.eventTypeView);
        clubNameView = view.findViewById(R.id.clubNameView);

        eventNameView.setText(eventName);
        eventTypeView.setText(eventType);
        clubNameView.setText(clubName);

        distanceView = view.findViewById(R.id.distanceText);
        locationView = view.findViewById(R.id.locationText);
        routeView = view.findViewById(R.id.routeText);
        startTimeView = view.findViewById(R.id.startTimeText);
        dateView = view.findViewById(R.id.dateText);

        levelView = view.findViewById(R.id.levelText);
        ageView = view.findViewById(R.id.ageText);

        readFromDatabase();

        submitButton = view.findViewById(R.id.submitButtonJoinEvent);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserInputs() == true) {
                    Toast.makeText(getContext(), "Joined Event Successfully!", Toast.LENGTH_SHORT).show();
                    writeToDatabase();

                    //navigating back to home page
                    Bundle args = new Bundle();
                    args.putString("username", participantUsername);
                    args.putString("accountType", "Participant");
                    ParticipantHomePageFragment fragment = new ParticipantHomePageFragment();
                    fragment.setArguments(args);

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragmentHolderViewParticipant, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;

    }
    public void writeToDatabase() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference joinedEventsRef = rootRef.child("Participant").child(participantUsername).child("JoinedEvents").child(eventName);

        joinedEventsRef.child("Event Type").setValue(eventType, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });

        joinedEventsRef.child("ClubName").setValue(clubName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });


    }
    public void readFromDatabase() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference eventDetailsRef = rootRef.child("Club Manager").child(clubUsername).child("Events").child(eventType);

        eventDetailsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Date")) {
                    String date = dataSnapshot.child("Date").getValue(String.class);
                    dateView.setText("Date: " + date);
                } else {
                    dateView.setVisibility(View.GONE);
                }

                if (dataSnapshot.hasChild("Distance")) {
                    String distance = dataSnapshot.child("Distance").getValue(String.class);
                    distanceView.setText("Distance: " + distance + "km");
                } else {
                    distanceView.setVisibility(View.GONE);
                }

                if (dataSnapshot.hasChild("Location")) {
                    String location = dataSnapshot.child("Location").getValue(String.class);
                    locationView.setText("Location: " + location);
                } else {
                    locationView.setVisibility(View.GONE);
                }


                if (dataSnapshot.hasChild("Route Overview")) {
                    String routeOverview = dataSnapshot.child("Route Overview").getValue(String.class);
                    routeView.setText("Route Overview: " + routeOverview);
                } else {
                    routeView.setVisibility(View.GONE);
                }

                if (dataSnapshot.hasChild("Start Time")) {
                    String startTime = dataSnapshot.child("Start Time").getValue(String.class);
                    startTimeView.setText("Start Time: " + startTime);
                } else {
                    startTimeView.setVisibility(View.GONE);
                }

                if (dataSnapshot.hasChild("Level")) {
                    level = dataSnapshot.child("Level").getValue(String.class);
                    levelInput = true;
                } else {
                    levelView.setVisibility(View.GONE);
                    levelInput = false;
                }

                if (dataSnapshot.hasChild("Age")) {
                    age = dataSnapshot.child("Age").getValue(String.class);
                    ageInput = true;
                } else {
                    ageView.setVisibility(View.GONE);
                    ageInput = false;
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean getUserInputs() {
        boolean isValid = true;

        String enteredLevel = levelView.getText().toString();
        // Validate Level
        if (levelInput) {
            if (enteredLevel.isEmpty()) {
                levelView.setError("Please enter a level");
                isValid = false; // Not valid if level is empty
            } else if (!enteredLevel.equalsIgnoreCase((Levels.BEGINNER).toString()) && !enteredLevel.equalsIgnoreCase((Levels.INTERMEDIATE).toString()) && !enteredLevel.equalsIgnoreCase((Levels.ELITE).toString())) {
                levelView.setError("Please enter a valid level");
                isValid = false;
            } else if (!enteredLevel.equalsIgnoreCase(level)) {
                Toast.makeText(getContext(), "This event is only for participants of level " + level, Toast.LENGTH_SHORT).show();
                isValid = false;
            }
        }

        // Validate Age
        if (ageInput) {
            try {
                int enteredAge = Integer.parseInt(ageView.getText().toString());
                int requiredAge = Integer.parseInt(age);

                if (enteredAge < requiredAge) {
                    ageView.setError("You are too young to join this event");
                    isValid = false; // Not valid if age is less than required
                }
            } catch (NumberFormatException e) {
                ageView.setError("Please enter a valid age");
                isValid = false; // Not valid if age is not a number
            }
        }

        return isValid;
    }

}
