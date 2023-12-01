package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * Welcome Activity that displays the welcome screen when users sign up, displays their username and account type
 *
 * @author Android Studio, Zachary Sikka, Zachary Kranabetter, Miguel Alvarez
 * @version 1.0
 */

public class ParticipantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");

        BottomNavigationView bottomNavigationView = findViewById(R.id.participantBottomNavMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment;
                Bundle args = new Bundle();
                if (item.getItemId() == R.id.homeParticipantBottomNavItem) {
                    selectedFragment = new ParticipantHomePageFragment();
                    args.putString("username", username);
                    args.putString("accountType", accountType);
                    selectedFragment.setArguments(args);

                } else if (item.getItemId() == R.id.clubsBottomNavItem) {
                    selectedFragment = new ParticipantClubSearchFragment();
                    args.putString("username", username);
                    selectedFragment.setArguments(args);

                } else if (item.getItemId() == R.id.eventsBottomNavItem) {
                    selectedFragment = new ParticipantJoinEventFragment();
                    args.putString("username", username);
                    selectedFragment.setArguments(args);
                } else {
                    return false;
                }

                //navigate to selected fragment screen
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderViewParticipant, selectedFragment).commit();
                return true;
            }
        });


        ParticipantHomePageFragment fragment = new ParticipantHomePageFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("accountType", accountType);
        fragment.setArguments(args);

        //Going to the participant home page fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderViewParticipant, fragment).commit();
    }

}