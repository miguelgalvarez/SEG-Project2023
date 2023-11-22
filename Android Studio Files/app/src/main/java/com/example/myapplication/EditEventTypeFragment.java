package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Fragment that represents the page where the admin can edit an new event types
 * @author zacharysikka
 * @version 1.0
 */
public class EditEventTypeFragment extends Fragment {

    FloatingActionButton backButton;
    DatabaseReference rootMain = FirebaseDatabase.getInstance().getReference();
    DatabaseReference rootAllEventTypes = rootMain.child("Event Type");
    DatabaseReference rootEventType;

    boolean[] editFieldList = new boolean[9];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event_type, container, false);

        String eventTypeName = getArguments().getString("eventTypeName"); // Assuming passed via arguments
        rootEventType = rootAllEventTypes.child(eventTypeName);

        TextView eventTypeTitleText = view.findViewById(R.id.EventTypeTitleText);
        eventTypeTitleText.setText(rootEventType.getKey());

        rootEventType.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                setupCheckBoxes(view, dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button myButton = view.findViewById(R.id.editSubmit);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFragment();
            }
        });

        backButton = view.findViewById(R.id.backButtonEditEventType);
        backButton.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });


        return view;
    }


    private void setupCheckBoxes(View view, DataSnapshot dataSnapshot) {
        CheckBox[] checkBoxes = new CheckBox[]{
                view.findViewById(R.id.editCheckBox1),
                view.findViewById(R.id.editCheckBox2),
                view.findViewById(R.id.editCheckBox3),
                view.findViewById(R.id.editCheckBox4),
                view.findViewById(R.id.editCheckBox5),
                view.findViewById(R.id.editCheckBox6),
                view.findViewById(R.id.editCheckBox7),
                view.findViewById(R.id.editCheckBox8),
                view.findViewById(R.id.editCheckBox9)
        };

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

        for (int j = 0; j<editFieldList.length;j++) {
            CheckBox checkBox = checkBoxes[j];

            final int index = j;
            checkBox.setOnClickListener(v -> {
                editFieldList[index] = !editFieldList[index];
                checkBox.setChecked(editFieldList[index]);
            });
        }
    }


    public void submitFragment() {

        Toast.makeText(getActivity(), "Edit Saved!", Toast.LENGTH_SHORT).show();

        AdministratorAccount.writeDatabase(rootEventType.getKey(), editFieldList);

        if (isAdded()) { // Check if fragment is currently added to its activity
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragmentHolderView, new AdminEventManagementFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

}