package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
    DatabaseReference rootMain = FirebaseDatabase.getInstance().getReference();
    DatabaseReference rootAllEventTypes = rootMain.child("Event Type");
    DatabaseReference rootEventType;

    boolean[] editFieldList = new boolean[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event_type_activity);

        Intent intent = getIntent();
        String eventTypeName = intent.getStringExtra("eventTypeName");
        rootEventType = rootAllEventTypes.child(eventTypeName);
        Log.d("key", rootEventType.getKey());

        TextView eventTypeTitleText = findViewById(R.id.EventTypeTitleText);
        eventTypeTitleText.setText(rootEventType.getKey());

        // fetch the boolean values in the db once when this page is opened
        rootEventType.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // list of references to checkboxes in the order they appear on the screen top to bottom
                CheckBox[] checkBoxes = {
                        findViewById(R.id.editCheckBox1),
                        findViewById(R.id.editCheckBox2),
                        findViewById(R.id.editCheckBox3),
                        findViewById(R.id.editCheckBox4),
                        findViewById(R.id.editCheckBox5),
                        findViewById(R.id.editCheckBox6),
                        findViewById(R.id.editCheckBox7),
                        findViewById(R.id.editCheckBox8),
                        findViewById(R.id.editCheckBox9)
                };

                // counter to increment the index for the boolean list and the checkbox list
                int i = 0;

                // This goes through the 2 sets of booleans
                for (DataSnapshot snapshotChild1 : dataSnapshot.getChildren()) {
                    // This goes through the children within each set of booleans
                    for (DataSnapshot snapshotChild2 : snapshotChild1.getChildren()) {

                        // get value
                        boolean booleanValue = snapshotChild2.getValue(Boolean.class);

                        // load the value to the list
                        editFieldList[i] = booleanValue;
                        checkBoxes[i].setChecked(booleanValue);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // back button functionality
        backButton = findViewById(R.id.backButtonEditEventType);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /*

    All these functions below here make the checkboxes actually work.
    They just not the value when they're checked.

     */

    public void editLevelSpeedBox(View view) {
        editFieldList[0] = !editFieldList[0];
    }

    public void editDistanceBox(View view) {
        editFieldList[1] = !editFieldList[1];
    }

    public void editStartTimeBox(View view) {
        editFieldList[2] = !editFieldList[2];
    }

    public void editLocationBox(View view) {
        editFieldList[3] = !editFieldList[3];
    }

    public void editRouteBox(View view) {
        editFieldList[4] = !editFieldList[4];
    }

    public void editAgeBox(View view) {
        editFieldList[5] = !editFieldList[5];
    }

    public void editLevelSpeedParticipantBox(View view) {
        editFieldList[6] = !editFieldList[6];
    }

    public void editDraftingBox(View view) {
        editFieldList[7] = !editFieldList[7];
    }

    public void editAccountStandingBox(View view) {
        editFieldList[8] = !editFieldList[8];
    }

    // submit button calls writeDatabase with a string input
    public void submitEditButton(View view) {

        Toast.makeText(this, "Edit Saved!", Toast.LENGTH_SHORT).show();

        AdministratorAccount.writeDatabase(rootEventType.getKey(), editFieldList);

        // back to last page
        onBackPressed();
    }

}
