package com.example.myapplication;

/**
 * Enumeration class for the 3 types of accounts
 *
 * @author Zachary Sikka
 * @version 1.0
 */
public enum AccountType {

    ADMINISTRATOR("Admin"),

    CLUB_MANAGER("Club Manager"),

    PARTICIPANT("Participant");

    private String name;

    AccountType(String name) {

        this.name = name;

    }

    /**
     * Getter method for returning the account type
     *
     * @return the String representation of the account type
     */
    public String getName() {

        return this.name;
    }

    /**
     * toString method for returning the account type
     *
     * @return the String representation of the account type
     */
    @Override
    public String toString() {

        return this.name;

    }
}
