package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Administrator account class which is a subclass of the account class
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
public class AdministratorAccount extends Account{

    public AdministratorAccount(String username, String password, String firstName, String lastName, String email, AccountType accountType) {

        super(username, password, firstName, lastName, email, accountType);
    }

    public void addEventType() {

        //hash map storing the events that can be offered and their required fields
        HashMap<EventType, ArrayList> eventsOffered = new HashMap<>();

        ArrayList<String> requiredFields = new ArrayList<>();

    }


    public Account removeAccount() {
        return null;
    }
}
