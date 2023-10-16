package com.example.myapplication;

/**
 * Club Manager account class which is a subclass of the account class
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
public class ClubManagerAccount extends Account {

    public ClubManagerAccount(String username, String password, String firstName, String lastName, String email, AccountType accountType) {

        super(username, password, firstName, lastName, email, accountType);
    }
}
