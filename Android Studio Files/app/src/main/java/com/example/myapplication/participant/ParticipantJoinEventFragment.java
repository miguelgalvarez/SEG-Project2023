package com.example.myapplication.participant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.participant.ParticipantClub;
import com.example.myapplication.participant.ParticipantClubList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantJoinEventFragment extends Fragment {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference participantAccountRef = rootRef.child("Participant");
    DatabaseReference participantAccountClubs;
    DataSnapshot eventSnapshot;

    List<ParticipantClub> participantClubs;
    ListView listParticipantClubs;
    ParticipantClubList participantClubAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_participant_join_event, container, false);

        // Fetch args from ParticipantActivity
        Bundle args = getArguments();
        String username = args != null ? args.getString("username") : "N/A";

        // Try and find the database reference to the Participant account
        try {
            participantAccountRef = participantAccountRef.child(username);
            participantAccountClubs = participantAccountRef.child("Clubs");
        } catch(Exception e) {
            // Handle the exception
            participantAccountRef = null;
            participantAccountClubs = null;
        }

        // Create the dynamic list of Clubs the participant is part of
        participantClubs = new ArrayList<ParticipantClub>();
        listParticipantClubs = view.findViewById(R.id.participantClubsList);
        participantClubAdapter = new ParticipantClubList(getActivity(), participantClubs, username);
        listParticipantClubs.setAdapter(participantClubAdapter);

        return view;

    }

    public void onStart() {
        super.onStart();

        // If the database references failed, skip displaying the active event list
        if (participantAccountRef == null || participantAccountClubs == null) {
            Log.d("debug", "failed to load db");
            return;
        }

        // Attach ValueEventListener to active events under the manager account in Firebase
        participantAccountClubs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                eventSnapshot = datasnapshot;
                participantClubs.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String clubNameString = childSnapshot.getKey();
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