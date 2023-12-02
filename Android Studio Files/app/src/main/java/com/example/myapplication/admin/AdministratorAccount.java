package com.example.myapplication.admin;

import com.example.myapplication.Account;
import com.example.myapplication.AccountType;
import com.example.myapplication.clubmanager.ClubManagerAccount;
import com.example.myapplication.EventType;
import com.example.myapplication.participant.ParticipantAccount;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Administrator account class which is a subclass of the account class.
 * This class contains methods specific to administrative actions such as managing event types
 * and removing club manager or participant accounts.
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
public class AdministratorAccount extends Account {

    // Constructor for AdministratorAccount
    public AdministratorAccount(String username, String password, String firstName, String lastName, String email, AccountType accountType) {
        super(username, password, firstName, lastName, email, accountType);
    }

    /**
     * Writes event details and registration requirements to the database.
     * @param eventType The type of event to add or modify in the database.
     * @param fieldList An array of boolean values representing various attributes of the event type.
     */
    public static void writeDatabase(String eventType, boolean[] fieldList) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventTypeRef = database.getReference("Event Type");

        // Reference to the specific event type
        DatabaseReference addedEventTypeRef = eventTypeRef.child(eventType);

        // Separate references for event details and registration requirements
        DatabaseReference eventDetailsRef = addedEventTypeRef.child("eventDetails");
        DatabaseReference registrationRequirementsRef = addedEventTypeRef.child("registrationRequirements");

        // Arrays to define the fields of event details and registration requirements
        String[] eventDetails = {"Distance", "Location", "Route Overview", "Start Time"};
        String[] registrationRequirements = {"Age", "Level"};

        // Writing event details to the database
        for(int i = 0; i < eventDetails.length; i++) {
            eventDetailsRef.child(eventDetails[i]).setValue(fieldList[i], new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Code to handle the error case
                    }
                }
            });
        }

        // Writing registration requirements to the database
        for(int i = 0; i < registrationRequirements.length; i++) {
            registrationRequirementsRef.child(registrationRequirements[i]).setValue(fieldList[i+4], new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // Code to handle the error case
                    }
                }
            });
        }
    }

    /**
     * Adds a new event type with specified fields to the database.
     * @param eventType The event type to be added.
     * @param fieldList The attributes of the event type.
     */
    public static void addEventType(EventType eventType, boolean[] fieldList) {
        writeDatabase(eventType.toString(), fieldList);
    }

    /**
     * Removes a club manager account from the database.
     * @param username The username of the club manager account to be removed.
     * @return The removed club manager account.
     */
    public static ClubManagerAccount removeClubManagerAccount(String username) {
        ClubManagerAccount account = new ClubManagerAccount(username);
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Club Manager").child(username);
        dR.removeValue();
        return account;
    }

    /**
     * Removes a participant account from the database.
     * @param username The username of the participant account to be removed.
     * @return The removed participant account.
     */
    public static ParticipantAccount removeParticipantAccount(String username) {
        ParticipantAccount account = new ParticipantAccount(username);
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Participant").child(username);
        dR.removeValue();
        return account;
    }

}
