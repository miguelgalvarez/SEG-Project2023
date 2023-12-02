package com.example.myapplication.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.loginpage.LoginHomePageActivity;
import com.example.myapplication.R;

/**
 * fragment that represents the admin home page
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
public class AdminHomePageFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment from the XML file 'fragment_admin_home_page'
        return inflater.inflate(R.layout.fragment_admin_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finding the logout button from the layout
        Button logoutButton = view.findViewById(R.id.logoutButton);

        // Setting an onClickListener to the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
                // Creating an intent to start the LoginHomePageActivity when the logout button is clicked
                Intent intent = new Intent(getContext(), LoginHomePageActivity.class);
                startActivity(intent);
                // This will navigate the user from the admin home page to the login screen
            }
        });
    }
}
