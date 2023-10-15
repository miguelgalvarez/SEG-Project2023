package com.example.myapplication;

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

    public String getName() {

        return this.name;
    }

    @Override
    public String toString() {

        return this.name;

    }
}
