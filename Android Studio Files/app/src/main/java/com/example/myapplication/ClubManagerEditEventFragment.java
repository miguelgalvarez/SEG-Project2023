package com.example.myapplication;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that represents the page where the admin can edit an new event types
 * @author zacharysikka
 * @version 1.0
 */
public class ClubManagerEditEventFragment extends Fragment {

    FloatingActionButton backButton;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference allManagersRef = rootRef.child("Club Manager");
    DatabaseReference managerAccountRef;
    DatabaseReference eventRef;
    String managerUsername;

    // Variables for the dynamic lists
    List<MutableAttribute> mutableAttributes;
    ListView listMutableAttributes;
    MutableAttributeList mutableAdapter;

    List<ImmutableAttribute> immutableAttributes;
    ListView listImmutableAttributes;
    ImmutableAttributeList immutableAdapter;

    String eventType;
    String[] mutableFieldListValues = new String[10];  // Holds the values in the EditText fields
    String[] mutableFieldListKeys = new String[10]; // Holds the key values for what each value applies to
    ArrayList<String> childCodes;

    /*
    The way I wrote this has two lists of strings where one is the value and one is the key.
    So when loading the page, each key-value pair in the database gets added to their respective list at the same index
     */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_manager_edit_event, container, false);

        // Get required database references
        eventType = getArguments().getString("eventTypeName"); // Assuming passed via arguments
        managerUsername = getArguments().getString("managerAccountName"); // Assuming passed via arguments
        managerAccountRef = allManagersRef.child(managerUsername);
        eventRef = managerAccountRef.child("Events").child(eventType);

        // Set the title of the page to show the event type name
        TextView eventName = view.findViewById(R.id.eventName);
        eventName.setText(eventRef.getKey());

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setupTextFields(view, dataSnapshot);
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

//        backButton = view.findViewById(R.id.backButtonEditEventType);
//        backButton.setOnClickListener(v -> {
//            if (getFragmentManager() != null) {
//                getFragmentManager().popBackStack();
//            }
//        });


        return view;
    }


    private void setupTextFields(View view, DataSnapshot dataSnapshot) {

        childCodes = new ArrayList<String>();

        // Set up the dynamic lists for the immutable and mutable attributes
        immutableAttributes = new ArrayList<ImmutableAttribute>();
        listImmutableAttributes = view.findViewById(R.id.immutableAttributes);
        immutableAdapter = new ImmutableAttributeList(getActivity(), immutableAttributes);
        listImmutableAttributes.setAdapter(immutableAdapter);

        mutableAttributes = new ArrayList<MutableAttribute>();
        listMutableAttributes = view.findViewById(R.id.mutableAttributes);
        mutableAdapter = new MutableAttributeList(getActivity(), mutableAttributes);
        listMutableAttributes.setAdapter(mutableAdapter);

        int i = 0;

        for (DataSnapshot child : dataSnapshot.getChildren()) {

            try {

                String attType = child.getKey();
                String attValue = child.getValue(String.class);
                if (isMutable(attType)) {
                    mutableAttributes.add(new MutableAttribute(attType, attValue));
                    childCodes.add(attType);  // Only care about this for mutable because its related to writing the value
                } else {
                    immutableAttributes.add(new ImmutableAttribute(attType, attValue));
                }

            } catch (Exception e) { continue; }

            i++;
        }
    }

    public static Boolean isMutable(String eventType) {
        // Checks if the String value is equal to any of the predetermined 'mutable' attribute types
        if (eventType == null) return false;
        return (
                        eventType.equals("Distance") ||
                        eventType.equals("Location") ||
                        eventType.equals("Route") ||
                        eventType.equals("Start Time") ||
                        eventType.equals("Date") ||
                        eventType.equals("Name")
        );
    }

    public void submitFragment() {

        Toast.makeText(getActivity(), "Edit Saved!", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < childCodes.size(); i++) {

            EditText valueField = mutableAttributes.get(i).viewHolder.attributeValue;
            String valueFieldString = valueField.getText().toString();

            Log.d("debug", valueFieldString);

            eventRef.child(childCodes.get(i)).setValue(valueFieldString,
                            new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Code to handle the error case
                    }
                }
            });
        }

        childCodes = null;


        ClubManagerHomePageFragment fragment = new ClubManagerHomePageFragment();
        Bundle args = new Bundle();
        args.putString("username", managerUsername);
        args.putString("accountType", "Club Manager");
        fragment.setArguments(args);
        if (isAdded()) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentHolderViewClubManager, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}