package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubManagerCreateEventFragment extends Fragment {

    LinearLayout layout;

    Spinner spinner;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference eventTypeRef = rootRef.child("Event Type");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_manager_create_event, container, false);

        layout = view.findViewById(R.id.layout);

        // Find the Spinner by ID
        spinner = view.findViewById(R.id.ddMenu);

        // Set up the OnItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                layout.removeAllViews();
                // Item selected from the Spinner
                String eventType = (String) parentView.getItemAtPosition(position);

                Toast.makeText(getContext(), "Selected Event Type: " + eventType, Toast.LENGTH_SHORT).show();

                //change to real check
                if (1==1) {
                    EditText distanceText = new EditText(getContext());
                    distanceText.setHint("Distance");
                    layout.addView(distanceText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if nothing is selected
            }
        });

        return view;
    }

    public void submitButton(View view) {


        //does not work rn

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Club Manager");
        Intent intent = getIntent();


        DatabaseReference newUserRef = usersRef.child(intent.getStringExtra("username"));

        newUserRef.child("Event").setValue(instagram.getText().toString(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error
                    Toast.makeText(CreateEvent.this, "Failed to write Event: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        // back to last page
        //onBackPressed();
    }
}
