package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubManagerCreateEventFragment extends Fragment {

    LinearLayout layout;
    Spinner spinner;
    private Button submitButton;
    private String eventType;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypeRef = rootRef.child("Event Type");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_manager_create_event, container, false);

        layout = view.findViewById(R.id.layout);
        spinner = view.findViewById(R.id.ddMenu);

        // Set up the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                layout.removeAllViews();
                // Item selected from the Spinner
                eventType = (String) parentView.getItemAtPosition(position);

                Toast.makeText(getContext(), "Selected Event Type: " + eventType, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        submitButton = view.findViewById(R.id.submit_button2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton();
            }
        });

        return view;

    }
    public void submitButton() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Club Manager");

        String username = getArguments().getString("username");

        DatabaseReference newUserRef = usersRef.child(username);

        newUserRef.child("Event").setValue(eventType, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(getContext(), "Failed to write event: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Event Updated!: ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (isAdded()) { // Check if fragment is currently added to its activity
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragmentHolderViewClubManager, new ClubManagerHomePageFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}