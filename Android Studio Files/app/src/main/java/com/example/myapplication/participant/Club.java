package com.example.myapplication.participant;

import com.google.firebase.database.DataSnapshot;

public class Club {

    private String clubName;

    private DataSnapshot clubManager;
    Club(String clubName, DataSnapshot clubManager) {
        this.clubName = clubName;
        this.clubManager = clubManager;

    }

    /**
     * Getter method for returning the event type
     *
     * @return the String representation of the event type
     */
    public String getName() {
        return this.clubName;
    }

    public DataSnapshot getClubManager() {
        return this.clubManager;
    }


}
