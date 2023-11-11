package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteAccount extends AppCompatActivity {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userAccountRef = rootRef.child("Participant");
    DatabaseReference clubManagerAccountRef = rootRef.child("Club Manager");
    ListView listViewParticipants;
    ListView listViewClubManagers;
    List<ParticipantAccount> participantAccountList;
    List<ClubManagerAccount> clubManagerAccountList;
    DataSnapshot participantSnapshot;
    DataSnapshot clubManagerSnapshot;
    ParticipantAccountList accountAdapter;
    ClubManagerAccountList accountAdapterClub;
    FloatingActionButton backButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account_activity);

        backButton = findViewById(R.id.backButtonDeleteAccount);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        participantAccountList = new ArrayList<>();

        clubManagerAccountList = new ArrayList<>();

        listViewParticipants = findViewById(R.id.activeAccounts);

        listViewClubManagers = findViewById(R.id.activeClubs);

        accountAdapter = new ParticipantAccountList(this, participantAccountList);

        accountAdapterClub = new ClubManagerAccountList(this, clubManagerAccountList);

        listViewParticipants.setAdapter(accountAdapter);

        listViewClubManagers.setAdapter(accountAdapterClub);

        listViewParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int index = 0;
                for (DataSnapshot childSnapshot : participantSnapshot.getChildren()) {
                    if (index == position) {
                        DatabaseReference accountCurrentRef = childSnapshot.getRef();
                        accountCurrentRef.removeValue();
                    }
                    index++;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //getting the participant accounts from the database
        userAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                participantSnapshot = datasnapshot;

                participantAccountList.clear();

                for(DataSnapshot childSnapshot: datasnapshot.getChildren()) {

                    String accountString = childSnapshot.getKey();

                    if (accountString != null) {
                        participantAccountList.add(new ParticipantAccount(accountString));
                    }
                }

                accountAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //getting the club manager accounts from the database
        clubManagerAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                clubManagerSnapshot = datasnapshot;

                clubManagerAccountList.clear();

                for(DataSnapshot childSnapshot: datasnapshot.getChildren()) {

                    String clubAccountString = childSnapshot.getKey();

                    if (clubAccountString != null) {
                        clubManagerAccountList.add(new ClubManagerAccount(clubAccountString));
                    }
                }

                accountAdapterClub.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
