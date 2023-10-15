package com.example.myapplication;

public class Account {

    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    AccountType accountType;



    public Account(String username, String password, String firstName, String lastName, String email, AccountType accountType) {
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

    public String getFirstName() {

        return this.firstName;
    }

    public String getLastName() {

        return this.lastName;
    }

    public String getEmail() {

        return this.email;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
