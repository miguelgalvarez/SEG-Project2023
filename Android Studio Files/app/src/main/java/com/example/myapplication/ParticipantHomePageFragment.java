package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Welcome Fragment that displays the welcome screen when users sign up, displays their username and account type
 */
public class ParticipantHomePageFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_participant_home_page, container, false);

        Bundle args = getArguments();
        String username = args != null ? args.getString("username") : "N/A";
        String accountType = args != null ? args.getString("accountType") : "N/A";

        // Find the TextViews in the UI layout
        TextView usernameTextView = view.findViewById(R.id.usernameText);
        TextView accountTypeTextView = view.findViewById(R.id.userRole);

        // Setting the UI components (TextViews) to display the username and account type
        usernameTextView.setText(username);
        accountTypeTextView.setText(accountType);

        Button logoutButton = view.findViewById(R.id.logoutButtonParticipant);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Logged Out Successfully!", Toast.LENGTH_SHORT).show();

                // Creating an intent to start the LoginHomePageActivity when the logout button is clicked
                Intent intent = new Intent(getContext(), LoginHomePageActivity.class);
                startActivity(intent);
                // This will navigate the user from the participant home page to the login screen
            }
        });

        return view;
    }
}
