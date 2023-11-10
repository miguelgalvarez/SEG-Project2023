package com.example.myapplication;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewEventActivity extends AppCompatActivity {

    FloatingActionButton backButton;
    boolean[] fieldList = new boolean[9];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);
        for (int i = 0; i<9;i++) {
            fieldList[i] = false;
        }

        backButton = findViewById(R.id.backButtonNewEvent);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void levelSpeedBox(View view) {
        fieldList[0] = !fieldList[0];
    }

    public void distanceBox(View view) {
        fieldList[1] = !fieldList[1];
    }

    public void startTimeBox(View view) {
        fieldList[2] = !fieldList[2];
    }

    public void locationBox(View view) {
        fieldList[3] = !fieldList[3];
    }

    public void routeBox(View view) {
        fieldList[4] = !fieldList[4];
    }

    public void ageBox(View view) {
        fieldList[5] = !fieldList[5];
    }

    public void LevelSpeedParticipantBox(View view) {
        fieldList[6] = !fieldList[6];
    }

    public void draftingBox(View view) {
        fieldList[7] = !fieldList[7];
    }

    public void accountStandingBox(View view) {
        fieldList[8] = !fieldList[8];
    }

    public void submitButton(View view) {

        EventType addedEvent;

        Spinner spinner = (Spinner) findViewById(R.id.dropdownMenuET);
        String eventType = spinner.getSelectedItem().toString();

        if (eventType.equals(EventType.TIME_TRIAL.getName())) {
            addedEvent = EventType.TIME_TRIAL;
        } else if (eventType.equals(EventType.HILL_CLIMB.getName())) {
            addedEvent = EventType.HILL_CLIMB;
        } else if (eventType.equals(EventType.ROAD_STAGE_RACE.getName())) {
            addedEvent = EventType.ROAD_STAGE_RACE;
        } else if (eventType.equals(EventType.ROAD_RACE.getName())) {
            addedEvent = EventType.ROAD_RACE;
        } else if (eventType.equals(EventType.GROUP_RIDE.getName())) {
            addedEvent = EventType.GROUP_RIDE;
        } else {
            addedEvent = null;
        }

        AdministratorAccount.addEventType(addedEvent, fieldList);

        Toast toast = Toast.makeText(this, eventType + " has been added!", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(getApplicationContext(), WelcomeAdminActivity.class);
        startActivity(intent);

    }

}
