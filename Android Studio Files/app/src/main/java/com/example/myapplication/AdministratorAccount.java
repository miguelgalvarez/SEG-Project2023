package com.example.myapplication;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.media.metrics.Event;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Administrator account class which is a subclass of the account class
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
public class AdministratorAccount extends Account{

    public AdministratorAccount(String username, String password, String firstName, String lastName, String email, AccountType accountType) {

        super(username, password, firstName, lastName, email, accountType);
    }


    public static void writeDatabase(EventType eventType, boolean[] fieldList) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventTypeRef = database.getReference("Event Type");

        DatabaseReference addedEventTypeRef = eventTypeRef.child(eventType.toString());

        DatabaseReference eventDetailsRef = addedEventTypeRef.child("eventDetails");

        DatabaseReference registrationRequirementsRef = addedEventTypeRef.child("registrationRequirements");

        String[] eventDetails = {"Level", "Distance", "Start Time", "Location", "Route Overview"};

        String[] registrationRequirements = {"Age", "Level", "Drafting Registration", "Account Standing"};

        for(int i = 0; i<5;i++) {
            eventDetailsRef.child(eventDetails[i]).setValue(fieldList[i], new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error
                        //Toast.makeText(requireContext(), "Failed to write usertype: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        for(int i = 0; i<4;i++) {
            registrationRequirementsRef.child(registrationRequirements[i]).setValue(fieldList[i+5], new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Handle the error
                        //Toast.makeText(requireContext(), "Failed to write usertype: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void addEventType(EventType eventType, boolean[] fieldList) {

        //hash map storing the events that can be offered and their required fields
        //HashMap<EventType, boolean[]> eventsOffered = new HashMap<>();

        //ArrayList<String> requiredFields = new ArrayList<>();

        writeDatabase(eventType, fieldList);
    }


    public Account removeAccount() {
        return null;
    }
}
