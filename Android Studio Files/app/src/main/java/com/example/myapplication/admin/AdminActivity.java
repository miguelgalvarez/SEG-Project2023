package com.example.myapplication.admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Admin activity screen that runs in the background while the admin is logged in and hold the bottom navigation menu
 *
 * @author Zachary Sikka
 * @version 1.0
 */
public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminBottomNavMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.homeBottomNavItem) {
                    selectedFragment = new AdminHomePageFragment();
                } else if (item.getItemId() == R.id.accountManagementBottomNavItem) {
                    selectedFragment = new AdminAccountManagementFragment();
                } else if (item.getItemId() == R.id.eventTypeBottomNavItem) {
                    selectedFragment = new AdminEventManagementFragment();
                } else {
                    return false;
                }

                //navigate to selected fragment screen
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderView, selectedFragment).commit();
                return true;
            }
        });

        //Going to the admin home page fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderView, new AdminHomePageFragment()).commit();


    }
}