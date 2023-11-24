package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClubManagerHomePageFragment extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference managerAccountRef = rootRef.child("Club Manager");
    DatabaseReference managerAccountActiveEvents;

    DataSnapshot activeEventSnapshot;

    Button logoutBtn;
    List<ActiveEvent> activeEvents;
    ListView listActiveEvents;
    ActiveEventList eventAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_manager_home_page, container, false);

        Bundle args = getArguments();

        // Create the dynamic list of active events
        activeEvents = new ArrayList<ActiveEvent>();
        listActiveEvents = view.findViewById(R.id.activeEventsList);
        eventAdapter = new ActiveEventList(getActivity(), activeEvents);
        listActiveEvents.setAdapter(eventAdapter);

        //get arguments from previous activity
        if (args != null) {
            String username = args.getString("username");
            String accountType = args.getString("accountType");

            // Find the TextViews in the UI layout
            TextView usernameTextView = view.findViewById(R.id.usernameText);
            TextView accountTypeTextView = view.findViewById(R.id.userRole);

            //setting the UI components(TextViews) to display the username and account type
            usernameTextView.setText(username);
            accountTypeTextView.setText(accountType);

            /*
                References the specific manager account
                Will Catch if either the username is null or the manager account contains no 'Events' child
                In either of these cases, the active event list will just appear empty
            */
            try {
                managerAccountRef = managerAccountRef.child(username);
                managerAccountActiveEvents = managerAccountRef.child("Events");
            } catch(Exception e) {
                managerAccountRef = null;
                managerAccountActiveEvents = null;
            }
        }

        logoutBtn = view.findViewById(R.id.logoutClubPage);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
                // Creating an intent to start the LoginHomePageActivity when the logout button is clicked
                Intent intent = new Intent(getContext(), LoginHomePageActivity.class);
                startActivity(intent);
                // This will navigate the user from the club manager home page to the login screen
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();

        // If the database references failed, skip displaying the active event list
        if (managerAccountRef == null || managerAccountActiveEvents == null) {
            return;
        }

        // Attach ValueEventListener to active events under the manager account in Firebase
        managerAccountActiveEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                activeEventSnapshot = datasnapshot;
                activeEvents.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String eventTypeString = childSnapshot.getKey();
                    if (eventTypeString != null) {
                        try {
                            ActiveEvent activeEvent = new ActiveEvent(eventTypeString);
                            activeEvents.add(activeEvent);
                        } catch (Exception e) {
                            ActiveEvent activeEvent = new ActiveEvent(eventTypeString, true);
                            activeEvents.add(activeEvent);
                        }
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

}
