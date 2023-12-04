package com.example.myapplication.participant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.loginpage.LoginHomePageActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Welcome Fragment that displays the welcome screen when users sign up, displays their username and account type
 */
public class ParticipantHomePageFragment extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference participantAccountRef = rootRef.child("Participant");
    DatabaseReference participantAccountEvents;
    DataSnapshot eventSnapshot;
    List<ActiveEvent> participantEvents;
    ListView listParticipantEvents;
    ParticipantEventList participantEventAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_participant_home_page, container, false);

        Bundle args = getArguments();
        String username = args != null ? args.getString("username") : "N/A";
        String accountType = args != null ? args.getString("accountType") : "N/A";

        // Find the TextViews in the UI layout
        TextView usernameTextView = view.findViewById(R.id.usernameText);
        TextView accountTypeTextView = view.findViewById(R.id.userRole);

        // Setting the UI components (TextViews) to display the username and account type
        usernameTextView.setText(username);
        accountTypeTextView.setText(accountType);

        Button logoutButton = view.findViewById(R.id.logoutButtonParticipant);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Logged Out Successfully!", Toast.LENGTH_SHORT).show();

                // Creating an intent to start the LoginHomePageActivity when the logout button is clicked
                Intent intent = new Intent(getContext(), LoginHomePageActivity.class);
                startActivity(intent);
                // This will navigate the user from the participant home page to the login screen
            }
        });

        // Try and find the database reference to the Participant account
        try {
            participantAccountRef = participantAccountRef.child(username);
            participantAccountEvents = participantAccountRef.child("JoinedEvents");
        } catch(Exception e) {
            // Handle the exception
            participantAccountRef = null;
            participantAccountEvents = null;
        }

        // Create the dynamic list of active events the participant has joined
        // This just reuses the ActiveEvent class which was used for the club manager home page
        participantEvents = new ArrayList<ActiveEvent>();
        listParticipantEvents = view.findViewById(R.id.participantActiveEventsList);
        participantEventAdapter = new ParticipantEventList(getActivity(), participantEvents, username);
        listParticipantEvents.setAdapter(participantEventAdapter);

        return view;
    }

    public void onStart() {
        super.onStart();

        // If the database references failed, skip displaying the active event list
        if (participantAccountRef == null || participantAccountEvents == null) {
            return;
        }

        // Attach ValueEventListener to active events under the manager account in Firebase
        participantAccountEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                eventSnapshot = datasnapshot;
                participantEvents.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String eventNameString = childSnapshot.getKey();
                    if (eventNameString != null) {
                        try {
                            ActiveEvent activeEvent = new ActiveEvent(eventNameString);
                            participantEvents.add(activeEvent);
                        } catch (Exception e) {
                            ActiveEvent activeEvent = new ActiveEvent(eventNameString, true);
                            participantEvents.add(activeEvent);
                        }
                    }
                }
                participantEventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
            }
        });
    }
}
