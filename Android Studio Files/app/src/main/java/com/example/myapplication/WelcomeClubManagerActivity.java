package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelcomeClubManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_club_manager_actvity);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String accountType = intent.getStringExtra("accountType");

        BottomNavigationView bottomNavigationView = findViewById(R.id.clubManagerBottomNavMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                Bundle args = new Bundle();
                if (item.getItemId() == R.id.homeClubManagerBottomNavItem) {
                    selectedFragment = new ClubManagerHomePageFragment();
                    args.putString("username", username);
                    args.putString("accountType", accountType);
                    selectedFragment.setArguments(args);

                } else if (item.getItemId() == R.id.createEventBottomNavItem) {
                    args.putString("username", username);
                    selectedFragment = new ClubManagerCreateEventFragment();

                } else if (item.getItemId() == R.id.editProfileBottomNavItem) {
                    selectedFragment = new ClubManagerEditProfileFragment();
                    args.putString("username", username);
                    selectedFragment.setArguments(args);
                } else {
                    return false;
                }

                //navigate to selected fragment screen
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderViewClubManager, selectedFragment).commit();
                return true;
            }
        });


        ClubManagerHomePageFragment fragment = new ClubManagerHomePageFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("accountType", accountType);
        fragment.setArguments(args);

        //Going to the admin home page fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderViewClubManager, fragment).commit();
    }


}
