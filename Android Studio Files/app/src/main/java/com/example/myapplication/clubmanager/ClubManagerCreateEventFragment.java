package com.example.myapplication.clubmanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClubManagerCreateEventFragment extends Fragment {

    ConstraintLayout layout;
    Spinner spinner;
    private Button submitButton;
    private String eventType;

    private EditText distance;
    private EditText level;
    private EditText location;
    private EditText route;
    private EditText start;
    private EditText age;
    private EditText participants;
    private EditText date;

    private String username;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypeRef = rootRef.child("Event Type");

    List<String> allFields;

    List<EditText> allTextViews;

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
        participants = view.findViewById(R.id.participantsText);
        date = view.findViewById(R.id.dateText);

        EditText[] eventDetailTextViews = {distance, location, route, start};
        EditText[] registrationRequirementsTextView = {level, age};

        allTextViews = new ArrayList<>();
        allFields = new ArrayList<>();

        populateDropdownMenu();


        // Set up the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                //Next step is to set each text view to be invisible if its not in the data base
                //layout.setVisibility(View.INVISIBLE);
                // Item selected from the Spinner
                //populateDropdownMenu();

                //clear lists if a new event type is selected from the spinner
                allFields.clear();
                allTextViews.clear();

                String[] eventDetails = {"Distance", "Location", "Route Overview", "Start Time"};
                String[] registrationRequirements = {"Level", "Age"};

                eventType = (String) parentView.getItemAtPosition(position);
                for (int i = 0; i < eventDetails.length; i++) {
                    int finalI = i;
                    eventTypeRef.child(eventType).child("eventDetails").child(eventDetails[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean value = dataSnapshot.getValue(Boolean.class);

                            if (value != null && value.equals(Boolean.TRUE)) {
                                eventDetailTextViews[finalI].setVisibility(View.VISIBLE);
                                allFields.add(eventDetails[finalI]);
                                allTextViews.add(eventDetailTextViews[finalI]);

                            } else {
                                eventDetailTextViews[finalI].setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                for (int i = 0; i<registrationRequirements.length; i++) {
                    int finalI = i;
                    eventTypeRef.child(eventType).child("registrationRequirements").child(registrationRequirements[i]).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean value = dataSnapshot.getValue(Boolean.class);

                            if (value != null && value.equals(Boolean.TRUE)) {
                                registrationRequirementsTextView[finalI].setVisibility(View.VISIBLE);
                                allFields.add(registrationRequirements[finalI]);
                                allTextViews.add(registrationRequirementsTextView[finalI]);

                            } else {
                                registrationRequirementsTextView[finalI].setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        submitButton = view.findViewById(R.id.submit_button2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitButton();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;

    }
    // end of the onCreateView method ^
    private void populateDropdownMenu() {
        // Clear existing items
        spinner.setAdapter(null);

        // Retrieve items from the database
        eventTypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> eventTypes = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String eventType = snapshot.getKey();
                        eventTypes.add(eventType);
                    }

                    // Populate the spinner with items from the database
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, eventTypes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(getContext(), "Error retrieving event types: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getOtherFields() {
        //String participantValue = participants.getText().toString();
        //String dateValue = date.getText().toString();
        allFields.add("Participants");
        allFields.add("Date");
        allTextViews.add(participants);
        allTextViews.add(date);
    }

    public void submitButton() throws ParseException {
        if (!validateFields()) {
            Toast.makeText(getContext(), "Validation failed!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference clubManagerRef = database.getReference("Club Manager");

        username = getArguments().getString("username");
        DatabaseReference userRef = clubManagerRef.child(username);

        DatabaseReference eventsRef = userRef.child("Events");

        // Check if the event type already exists
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChild(eventType)) {
                    // Event type already exists
                    Toast.makeText(getContext(), eventType + " already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Event type does not exist or the events section is empty, add a new event
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
    private boolean validateFields() throws ParseException {
        if (!TextUtils.isDigitsOnly(distance.getText().toString())) {
            distance.setError("Invalid distance");
            return false;
        }
        if (!isValidLocationInput(location.getText().toString())) {
            location.setError("Invalid location");
            return false;
        }
        if (route.getText().toString().isEmpty()) {
            route.setError("Invalid route");
            return false;
        }
        if (!isValidTime(start.getText().toString())) {
            start.setError("Invalid start time");
            return false;
        }
        String levelInput = level.getText().toString();
        if (!(levelInput.equals("beginner") || levelInput.equals("intermediate") || levelInput.equals("elite"))) {
            level.setError("Invalid level");
            return false;
        }
        if (!isValidNumber(age.getText().toString())) {
            age.setError("Invalid age");
            return false;
        }
        if (!isValidNumber(participants.getText().toString())) {
            participants.setError("Invalid number of participants");
            return false;
        }
        if (!isValidFutureDate(date.getText().toString())) {
            date.setError("Invalid date");
            return false;
        }
        return true;
    }
    private boolean isValidLocationInput(String input) {
        // Regex to check if the input is numbers followed by strings
        String regex = "^\\d+\\s+.*$";
        return input != null && input.matches(regex);
    }
    private boolean isValidTime(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            format.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidNumber(String number) {
        try {
            return Integer.parseInt(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidFutureDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(dateString);
            return date != null && date.after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    private void addNewEvent(DatabaseReference eventsRef) {
        // Add a new child to the "Events" node with the event type

        //gets the text views, participants and date
        getOtherFields();

        eventsRef.child(eventType);

        DatabaseReference fieldsRef = eventsRef.child(eventType);

        for (int i = 0; i < allFields.size(); i++) {
            fieldsRef.child(allFields.get(i)).setValue(allTextViews.get(i).getText().toString(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error
                        Toast.makeText(getContext(), "Failed to write event: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Event Created!", Toast.LENGTH_SHORT).show();

                        // Navigate back to the home page
                        ClubManagerHomePageFragment fragment = new ClubManagerHomePageFragment();
                        Bundle args = new Bundle();
                        args.putString("username", username);
                        args.putString("accountType", "Club Manager");
                        fragment.setArguments(args);

                        if (isAdded()) {
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentHolderViewParticipant, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                }
            });
        }
    }
}