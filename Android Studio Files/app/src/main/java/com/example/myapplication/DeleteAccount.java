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
    ListView listViewParticipants;
    List<ParticipantAccount> participantAccountList;
    DataSnapshot particpantSnapshot;
    ParticipantAccountList accountAdapter;

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

        listViewParticipants = findViewById(R.id.activeAccounts);

        accountAdapter = new ParticipantAccountList(this, participantAccountList);

        listViewParticipants.setAdapter(accountAdapter);

        listViewParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int index = 0;
                for (DataSnapshot childSnapshot : particpantSnapshot.getChildren()) {
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

        userAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {

                particpantSnapshot = datasnapshot;

                participantAccountList.clear();

                for(DataSnapshot childSnapshot: datasnapshot.getChildren()) {

                    String accountString = childSnapshot.getKey();

                    if (accountString != null) {
                        //EventType eventType = EventType.fromString(accountString);
                        participantAccountList.add(new ParticipantAccount(accountString));
                    }
                }

                accountAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
