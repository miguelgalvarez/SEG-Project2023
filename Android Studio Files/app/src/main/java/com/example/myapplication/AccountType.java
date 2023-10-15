package com.example.myapplication;

public enum AccountType {

    ADMINISTRATOR("Admin"),

    CLUB_MANAGER("Club Manager"),

    PARTICIPANT("Participant");

    private String name;

    AccountType(String name) {

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
