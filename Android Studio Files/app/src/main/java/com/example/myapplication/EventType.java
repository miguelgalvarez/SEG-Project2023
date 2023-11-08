package com.example.myapplication;

/**
 * Enumeration class for the 5 types of events
 *
 * @author Zachary Sikka
 * @version 1.0
 */
public enum EventType {
    TIME_TRIAL("Time Trial"),

    HILL_CLIMB("Hill Climb"),

    ROAD_STAGE_RACE("Road Stage Race"),

    ROAD_RACE("Road Race"),

    GROUP_RIDE("Group Ride");

    private String name;

    EventType(String name) {

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

    public static EventType fromString(String name) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getName().equals(name)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("No such EventType: " + name);
    }

    /**
     * Getter method for returning the event type
     *
     * @return the String representation of the event type
     */
    @Override
    public String toString() {

        return this.name;

    }
}
