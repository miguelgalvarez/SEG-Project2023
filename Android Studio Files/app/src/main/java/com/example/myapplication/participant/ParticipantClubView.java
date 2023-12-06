package com.example.myapplication.participant;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;

import com.example.myapplication.R;
import com.example.myapplication.participant.Club;
import com.example.myapplication.participant.ClubList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantClubView extends Fragment {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference clubAccountRef = rootRef.child("Club Manager");
    DatabaseReference participantAccountClubs;
    DataSnapshot eventSnapshot;
    private Button leaveClubButton;
    private Button rateButton;
    public String username;

    // Stuff for the dynamic list
    List<Club> allParticipantClubs;
    List<Club> shownParticipantClubs;
    ListView listParticipantClubs;
    ClubList participantClubAdapter;
    TextView clubIdName;

    public EditText ratingText;

    // Stores the search string
    String searchFilter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_participant_club_view, container, false);

        // Fetch args from ParticipantActivity
        Bundle args = getArguments();
        username = args != null ? args.getString("username") : "N/A";

        // Set up for the search bar functionality
        SearchView searchView = view.findViewById(R.id.searchbar);
        searchFilter = "";

        // leave club button
        DatabaseReference clubNameRef = rootRef.child("Participant").child(username).child("Joined Club");
        leaveClubButton = view.findViewById(R.id.leaveClubButtonID);
        leaveClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clubNameRef.setValue("No club");
                Toast.makeText(getContext(),"Successfully left club!",Toast.LENGTH_SHORT);
                leaveClubButton.setVisibility(View.INVISIBLE);
                rateButton.setVisibility(View.INVISIBLE);

            }
        });

        rateButton = view.findViewById(R.id.rateClubButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clubNameRef.setValue("No club");
                //Toast.makeText(getContext(),"Successfully left club!",Toast.LENGTH_SHORT);
                //leaveClubButton.setVisibility(View.INVISIBLE);
                showRatingDialog();
            }
        });

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

        // Create the dynamic list of Clubs the participant is part of
        allParticipantClubs = new ArrayList<Club>();
        shownParticipantClubs = new ArrayList<Club>();
        listParticipantClubs = view.findViewById(R.id.clubListParticipantViewID);
        participantClubAdapter = new ClubList(getActivity(), shownParticipantClubs, username, leaveClubButton);
        listParticipantClubs.setAdapter(participantClubAdapter);
        // adding club name to page from database
        clubIdName = view.findViewById(R.id.clubNameTextID);
        clubNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String joinedClub = snapshot.getValue(String.class);
                if (joinedClub != null && !joinedClub.equals("No club")) {
                    // User is in a club, make the rateButton & leaveClubButton visible
                    rateButton.setVisibility(View.VISIBLE);
                    leaveClubButton.setVisibility(View.VISIBLE);

                } else {
                    // User is not in a club, hide the rateButton & leaveClubButton
                    rateButton.setVisibility(View.INVISIBLE);
                    leaveClubButton.setVisibility(View.INVISIBLE);
                }
                clubIdName.setText(joinedClub);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });

        return view;

    }

    public void showRatingDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rate_club_popup_window, null);

        ratingText = dialogView.findViewById(R.id.ratingInput);

        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.setCancelable(false); // Prevents dismissing the dialog on back press or touch outside
        dialog.show();
        DatabaseReference participantNameRef = rootRef.child("Participant").child(username);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = ratingText.getText().toString();
                if (!rating.isEmpty()) {
                    try {
                        int ratingInt = Integer.parseInt(rating);
                        if (ratingInt>=1 && ratingInt<=5 ){
                            participantNameRef.child("rating").setValue(rating, new DatabaseReference.CompletionListener() {

                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        // Handle the error
                                    }
                                }
                            });
                            Toast.makeText(getContext(), "Rating added", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getContext(), "Not a valid rating", Toast.LENGTH_SHORT).show();
                        }

                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Not a valid rating", Toast.LENGTH_SHORT).show();
                    }


                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Not a valid rating", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void onStart() {
        super.onStart();

        // If the database references failed, skip displaying the active event list
        if (clubAccountRef == null) {
            Log.d("debug", "failed to load db");
            return;
        }

        // Attach ValueEventListener to active events under the manager account in Firebase
        clubAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                eventSnapshot = datasnapshot;
                allParticipantClubs.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String clubNameString = childSnapshot.child("clubname").getValue(String.class);
                    if (clubNameString != null) {
                        Club activeClub = new Club(clubNameString, childSnapshot);
                        allParticipantClubs.add(activeClub);
                    }
                }

                // Fill the UI list
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
        shownParticipantClubs.clear();

        // Conditionally add all the search events to the shown list
        for (Club club : allParticipantClubs) {

            // If theres nothing in the search, add the item and skip the rest of the loop code
            if (searchFilter.length() == 0) {
                shownParticipantClubs.add(club);
                continue;
            }

            char[] clubNameChars;
            char[] searchFieldChars;

            try {
                clubNameChars = club.getName().toCharArray();
                searchFieldChars = searchFilter.toCharArray();

                // This happens when the event doesn't have a name, so just show it anyway
                // This shouldn't ever happen unless the event was added manually in the database
            } catch (Exception e) {
                shownParticipantClubs.add(club);
                continue;
            }

            // Search cannot be valid if its longer than the result
            if (searchFieldChars.length > clubNameChars.length) continue;

            // The search must check every char where the search string can fit
            int iterations = clubNameChars.length - searchFieldChars.length;

            // Need to check this many chars per iteration
            int subIterations = searchFieldChars.length;

            for (int i = 0; i <= iterations; i++) {
                Boolean containsSearch = true;

                // Check each char of the search against each char of the event name ( from a different offset every outer loop iteration )
                // If anything isn't equal, skip this iteration ( the iteration of the changing offset, so move to a different offset )
                for (int j = i; j < (i + subIterations); j++) {
                    if (clubNameChars[j] != searchFieldChars[j - i]) {
                        containsSearch = false;
                        break;
                    }
                }

                if (containsSearch) {
                    shownParticipantClubs.add(club);
                    break;
                }
            }
        }

        // Update UI list
        participantClubAdapter.notifyDataSetChanged();
    }
}