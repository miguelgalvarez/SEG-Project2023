package com.example.myapplication.participant;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    List<Club> participantClubs;
    ListView listParticipantClubs;
    ClubList participantClubAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_participant_club_view, container, false);

        // Fetch args from ParticipantActivity
        Bundle args = getArguments();
        String username = args != null ? args.getString("username") : "N/A";

        // Create the dynamic list of Clubs the participant is part of
        participantClubs = new ArrayList<Club>();
        listParticipantClubs = view.findViewById(R.id.clubListParticipantViewID);
        participantClubAdapter = new ClubList(getActivity(), participantClubs, username);
        listParticipantClubs.setAdapter(participantClubAdapter);

        return view;

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
                participantClubs.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String clubNameString = childSnapshot.child("clubname").getValue(String.class);
                    if (clubNameString != null) {
                        Club activeClub = new Club(clubNameString, childSnapshot);
                        participantClubs.add(activeClub);
                    }
                }
                participantClubAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
            }
        });
    }
}