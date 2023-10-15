package com.example.myapplication;

public class Account {

    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    String accountType;



    public Account(String username, String password, String firstName, String lastName, String email, String accountType) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
    }

    public String getUsername() {

        return this.username;
    }

    public String getPassword() {
        
        return this.password;
    }
}
