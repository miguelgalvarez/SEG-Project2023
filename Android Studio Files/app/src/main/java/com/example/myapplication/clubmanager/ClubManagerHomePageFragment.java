package com.example.myapplication.clubmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.participant.ActiveEvent;
import com.example.myapplication.participant.ActiveEventList;
import com.example.myapplication.loginpage.LoginHomePageActivity;
import com.example.myapplication.R;
import com.example.myapplication.participant.ParticipantEvent;
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
    int logoNumber;
    ImageView logo;
    Button logoutBtn;
    List<ActiveEvent> activeEvents;
    ListView listActiveEvents;
    ActiveEventList eventAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_manager_home_page, container, false);
        Bundle args = getArguments();

        //get arguments from previous activity
        if (args != null) {
            Log.d("DEBUG", "arguments were passed!");
            String username = args.getString("username");
            String accountType = args.getString("accountType");

            // Find the TextViews in the UI layout
            TextView usernameTextView = view.findViewById(R.id.usernameText);
            TextView accountTypeTextView = view.findViewById(R.id.userRole);

            //setting the UI components(TextViews) to display the username and account type
            usernameTextView.setText(username);
            accountTypeTextView.setText(accountType);


            logo = view.findViewById(R.id.logoImage);

            logo.setVisibility(View.INVISIBLE);

            /*
                References the specific manager account
                Will Catch if either the username is null or the manager account contains no 'Events' child
                In either of these cases, the active event list will just appear empty
            */
            try {
                managerAccountRef = managerAccountRef.child(username);
                managerAccountActiveEvents = managerAccountRef.child("Events");

                try {
                    managerAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                if (dataSnapshot.exists()) {
                                    Long logoNumberObject = dataSnapshot.child("Logo Number").getValue(Long.class);
                                    logoNumber = Math.toIntExact(logoNumberObject);
                                    Toast.makeText(getContext(), "logo number is" + logoNumber, Toast.LENGTH_SHORT).show();

                                    if (logoNumber == 1) {
                                        logo.setImageResource(R.drawable.logo1);
                                        logo.setVisibility(View.VISIBLE);
                                    } else if (logoNumber == 2) {
                                        logo.setImageResource(R.drawable.logo2);
                                        logo.setVisibility(View.VISIBLE);
                                    } else if (logoNumber == 3) {
                                        logo.setImageResource(R.drawable.logo3);
                                        logo.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(getContext(), "did not get logo", Toast.LENGTH_SHORT).show();
                                        logo.setVisibility(View.INVISIBLE);
                                    }
                                }
                            } catch (Exception e) {
                                //No logo
                                logo.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                    //logo has not been made
                }


            } catch(Exception e) {
                // Handle the exception
                managerAccountRef = null;
                managerAccountActiveEvents = null;
            }

            // Create the dynamic list of active events
            activeEvents = new ArrayList<ActiveEvent>();
            listActiveEvents = view.findViewById(R.id.activeEventsList);
            eventAdapter = new ActiveEventList(getActivity(), activeEvents, username);
            listActiveEvents.setAdapter(eventAdapter);
        }

        logoutBtn = view.findViewById(R.id.logoutButtonParticipant);

        //set the logo if one was in the database


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

        eventAdapter.setActiveEventListListener(activeEventName -> {
            ClubManagerEditEventFragment fragment = new ClubManagerEditEventFragment();
            Bundle args2 = new Bundle();
            args2.putString("eventTypeName", activeEventName);
            args2.putString("managerAccountName", managerAccountRef.getKey());
            fragment.setArguments(args2);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentHolderViewParticipant, fragment)
                    .addToBackStack(null)
                    .commit();
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
                    String eventNameString = childSnapshot.child("Event Name").getValue(String.class);
                    String clubNameString = managerAccountRef.getKey();

                    if (eventNameString != null && eventTypeString != null && clubNameString != null) {
                        ActiveEvent activeEvent = new ActiveEvent(eventTypeString, eventNameString, clubNameString);
                        eventAdapter.add(activeEvent);
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
