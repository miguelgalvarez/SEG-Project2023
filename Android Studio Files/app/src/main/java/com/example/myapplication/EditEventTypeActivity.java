package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class EditEventTypeActivity extends AppCompatActivity {
    FloatingActionButton backButton;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypesRef = rootRef.child("Event Type");
    DatabaseReference eventTypeChild;
    boolean[] editFieldList = new boolean[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.edit_event_type_activity);
        backButton = findViewById(R.id.backButtonEditEventType);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String eventTypeName = intent.getStringExtra("eventTypeName");
        eventTypeChild = eventTypesRef.child(eventTypeName);
        Log.d("key", eventTypeChild.getKey());

        TextView eventTypeTitleText = findViewById(R.id.EventTypeTitleText);
        eventTypeTitleText.setText(eventTypeChild.getKey());
        for (int i = 0; i<9;i++) {
            editFieldList[i] = false;
        }
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //attaching value event listener
//        eventTypesRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                //clearing the previous artist list
//
//                //iterating through all the nodes
//                int i=0;
//                for (DataSnapshot snapshotChild1 : dataSnapshot.getChildren()) {
//                    for (DataSnapshot dataSnapshotChild2 : snapshotChild1.getChildren()) {
//                        //getting product
//                        Boolean dataFromFB = dataSnapshotChild2.getValue(Boolean.class);
//                        //adding product to the list
//                        editFieldList[i]=dataFromFB;
//                        i++;
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
    public void editlevelSpeedBox(View view) {
        editFieldList[0] = !editFieldList[0];
    }

    public void editdistanceBox(View view) {
        editFieldList[1] = !editFieldList[1];
    }

    public void editstartTimeBox(View view) {
        editFieldList[2] = !editFieldList[2];
    }

    public void editlocationBox(View view) {
        editFieldList[3] = !editFieldList[3];
    }

    public void editrouteBox(View view) {
        editFieldList[4] = !editFieldList[4];
    }

    public void editageBox(View view) {
        editFieldList[5] = !editFieldList[5];
    }

    public void editLevelSpeedParticipantBox(View view) {
        editFieldList[6] = !editFieldList[6];
    }

    public void editdraftingBox(View view) {
        editFieldList[7] = !editFieldList[7];
    }

    public void editaccountStandingBox(View view) {
        editFieldList[8] = !editFieldList[8];
    }

    public void submitEditButton(View view) {

//        Toast toast = Toast.makeText(this, eventType + " has been added!", Toast.LENGTH_SHORT);
//        toast.show();
        AdministratorAccount.writeDatabase(eventTypeChild.getKey(), editFieldList);
        Intent intent = new Intent(getApplicationContext(), ActiveEventsActivity.class);
        startActivity(intent);

    }


}
