package com.example.myapplication.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.clubmanager.ClubManagerAccount;
import com.example.myapplication.clubmanager.ClubManagerAccountList;
import com.example.myapplication.participant.ParticipantAccount;
import com.example.myapplication.participant.ParticipantAccountList;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment screen for the admin account management which shows the active club and participant accounts
 * and allows the admin to delete the accounts
 *
 * @author Zachary Sikka
 * @version 1.0
 */
public class AdminAccountManagementFragment extends Fragment {

    // Firebase database references
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userAccountRef = rootRef.child("Participant");
    DatabaseReference clubManagerAccountRef = rootRef.child("Club Manager");

    // UI elements
    ListView listViewParticipants;
    ListView listViewClubManagers;
    List<ParticipantAccount> participantAccountList;
    List<ClubManagerAccount> clubManagerAccountList;
    DataSnapshot participantSnapshot;
    DataSnapshot clubManagerSnapshot;
    ParticipantAccountList accountAdapter;
    ClubManagerAccountList accountAdapterClub;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_account_management, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize and set up ListView for participant accounts
        participantAccountList = new ArrayList<>();
        clubManagerAccountList = new ArrayList<>();
        listViewParticipants = view.findViewById(R.id.activeAccounts);
        listViewClubManagers = view.findViewById(R.id.activeClubs);

        accountAdapter = new ParticipantAccountList(getActivity(), participantAccountList);
        accountAdapterClub = new ClubManagerAccountList(getActivity(), clubManagerAccountList);

        listViewParticipants.setAdapter(accountAdapter);
        listViewClubManagers.setAdapter(accountAdapterClub);

        // Set up item click listener for participant ListView
        listViewParticipants.setOnItemClickListener((adapterView, view1, position, l) -> {
            int index = 0;
            for (DataSnapshot childSnapshot : participantSnapshot.getChildren()) {
                if (index == position) {
                    DatabaseReference accountCurrentRef = childSnapshot.getRef();
                    accountCurrentRef.removeValue();
                }
                index++;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Attach ValueEventListener to participant accounts in Firebase
        userAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                participantSnapshot = datasnapshot;
                participantAccountList.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String accountString = childSnapshot.getKey();
                    if (accountString != null) {
                        participantAccountList.add(new ParticipantAccount(accountString));
                    }
                }
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
            }
        });

        // Attach ValueEventListener to club manager accounts in Firebase
        clubManagerAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                clubManagerSnapshot = datasnapshot;
                clubManagerAccountList.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    String clubAccountString = childSnapshot.getKey();
                    if (clubAccountString != null) {
                        clubManagerAccountList.add(new ClubManagerAccount(clubAccountString));
                    }
                }
                accountAdapterClub.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle potential errors
            }
        });
    }
}
