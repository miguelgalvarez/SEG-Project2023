package com.example.myapplication.participant;

/**
 * class for the active event items which display on club manager home page
 *
 * @author Linden Sheehy
 * @version 1.0
 */
public class ParticipantEvent {

    public String eventName;
    public String eventType;
    public String clubName;

    // Overloaded constructor to allow any string name input for testing
    public ParticipantEvent(String name, String type, String club) {

        this.eventName = name;
        this.eventType = type;
        this.clubName = club;
    }
}
