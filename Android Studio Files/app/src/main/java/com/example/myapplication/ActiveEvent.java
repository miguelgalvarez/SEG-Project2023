package com.example.myapplication;

/**
 * Enumeration class for the 5 types of events
 *
 * @author Zachary Sikka
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

    private String name;

    ActiveEvent(String name) {

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
    ActiveEvent(String name, Boolean overrideNameCheck) {

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
}
