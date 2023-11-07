package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class NewEventActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);
    }



    Spinner spinner = (Spinner) findViewById(R.id.dropdownMenuET);
    String size = spinner.getSelectedItem().toString();


    boolean[] fieldList = new boolean[9];
    public void levelSpeedBox(View view) {
        fieldList[0] = true;
    }

    public void distanceBox(View view) {
        fieldList[1] = true;
    }

    public void startTimeBox(View view) {
        fieldList[2] = true;
    }

    public void locationBox(View view) {
        fieldList[3] = true;
    }

    public void routeBox(View view) {
        fieldList[4] = true;
    }

    public void ageBox(View view) {
        fieldList[5] = true;
    }

    public void LevelSpeedParticipantBox(View view) {
        fieldList[6] = true;
    }

    public void draftingBox(View view) {
        fieldList[7] = true;
    }

    public void accountStandingBox(View view) {
        fieldList[8] = true;
    }

    public void submitButton() {
        //AdministratorAccount.addEventType(EventType, fieldList);
    }

}
