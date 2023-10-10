package com.example.myapplication;

public class Account {

    String username;
    String password;
    String accountID;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountID = username;
    }
}
