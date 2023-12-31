package com.example.myapplication.participant;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * class for the active event items which display on club manager home page
 *
 * @author Linden Sheehy
 * @version 1.0
 */
public class SearchEvent {

    private final String eventName;
    private final String eventType;
    private final String clubName;
    private final String clubUsername;
    private String participantUsername;

    public SearchEvent(String eventName, String eventType, String clubName, String clubUsername, String participantUsername) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.clubName = clubName;
        this.clubUsername = clubUsername;
        this.participantUsername = participantUsername;
    }

    /**
     * Getter and setter methods for private variables
     */
    public String getEventName() {
        return this.eventName;
    }

    public String getEventType() {
        return this.eventType;
    }

    public String getClubName() {
        return this.clubName;
    }

    public String getClubUsername() {
        return this.clubUsername;
    }

    //callback to handle asynchronous nature of firebase
    public interface ParticipantJoinedCallback {
        void onCallback(boolean hasJoined);
    }


    public void hasParticipantJoined(String accountName, ParticipantJoinedCallback callback) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference joinedEvents = rootRef.child("Participant").child(accountName).child("JoinedEvents");
        joinedEvents.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(eventName)) {
                    callback.onCallback(snapshot.hasChild(eventName));
                } else {
                    callback.onCallback(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
