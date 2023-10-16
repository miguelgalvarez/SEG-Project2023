package com.example.myapplication;

/**
 * Participant account class which is a subclass of the account class
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
public class ParticipantAccount extends Account{

    public ParticipantAccount(String username, String password, String firstName, String lastName, String email, AccountType accountType) {

        super(username, password, firstName, lastName, email, accountType);
    }
}
