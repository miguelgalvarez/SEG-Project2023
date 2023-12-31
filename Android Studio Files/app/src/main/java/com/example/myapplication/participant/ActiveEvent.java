package com.example.myapplication.participant;

/**
 * class for the active event items which display on club manager home page
 *
 * @author Linden Sheehy
 * @version 1.0
 */
public class ActiveEvent {

    public static final String TIME_TRIAL = "Time Trial";

    public static final String HILL_CLIMB = "Hill Climb";

    public static final String ROAD_STAGE_RACE = "Road Stage Race";

    public static final String ROAD_RACE = "Road Race";

    public static final String GROUP_RIDE = "Group Ride";

    public static final String[] allTypes = {
            TIME_TRIAL,
            HILL_CLIMB,
            ROAD_STAGE_RACE,
            ROAD_RACE,
            GROUP_RIDE
    };

    public String name;

    public String eventType;
    public String clubName;

    public ActiveEvent(String name, String type, String club) {

        this.eventType = type;
        this.clubName = club;

        // Checks if the name is a valid event type name
        for (String eventType : allTypes) {
            if (name.equals(eventType)) {
                this.name = name;
                return;
            }
        }

        // Throws an error if not
        throw new IllegalArgumentException("No such EventType: " + name);
    }

    // Overloaded constructor to allow any string name input for testing
    public ActiveEvent(String name, Boolean overrideNameCheck) {

        if (!overrideNameCheck) {
            return;
        }

        this.name = name;
    }

    /**
     * Getter method for returning the event type
     *
     * @return the String representation of the event type
     */
    public String getName() {
        return this.name;
    }

    public String getEventType() {return this.eventType;}

    public String getClubName() {return this.clubName;}
}
