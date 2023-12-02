package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantClubView extends Fragment {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference participantAccountRef = rootRef.child("Club Manager");
    DatabaseReference participantAccountClubs;
    DataSnapshot eventSnapshot;
    List<ParticipantClub> participantClubs;
    ListView listParticipantClubs;
    ParticipantClubList participantClubAdapter;

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
        participantClubs = new ArrayList<ParticipantClub>();
        listParticipantClubs = view.findViewById(R.id.clubListParticipantViewID);
        participantClubAdapter = new ParticipantClubList(getActivity(), participantClubs, username);
        listParticipantClubs.setAdapter(participantClubAdapter);

        return view;

    }

    public void onStart() {
        super.onStart();

        // If the database references failed, skip displaying the active event list
        if (participantAccountRef == null) {
            Log.d("debug", "failed to load db");
            return;
        }

        // Attach ValueEventListener to active events under the manager account in Firebase
        participantAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                eventSnapshot = datasnapshot;
                participantClubs.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String clubNameString = childSnapshot.child("clubname").getValue(String.class);
                    Log.d("debug",clubNameString);
                    if (clubNameString != null) {
                        ParticipantClub activeClub = new ParticipantClub(clubNameString);
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