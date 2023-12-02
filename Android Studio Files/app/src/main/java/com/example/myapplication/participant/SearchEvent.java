package com.example.myapplication.participant;

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

    SearchEvent(String eventName, String eventType, String clubName) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.clubName = clubName;
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
}
