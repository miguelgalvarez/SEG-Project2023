package com.example.myapplication;

public class ParticipantClub {

    private String clubName;

    ParticipantClub(String clubName) {
        this.clubName = clubName;
        return;
    }


    /**
     * Getter method for returning the club name
     *
     * @return the String representation of the club name
     */
    public String getName() {
        return this.clubName;
    }

}