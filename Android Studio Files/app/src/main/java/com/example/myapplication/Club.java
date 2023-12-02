package com.example.myapplication;

public class Club {

    private String clubName;

    Club(String clubName) {
        this.clubName = clubName;
    }

    /**
     * Getter method for returning the event type
     *
     * @return the String representation of the event type
     */
    public String getName() {
        return this.clubName;
    }

}
