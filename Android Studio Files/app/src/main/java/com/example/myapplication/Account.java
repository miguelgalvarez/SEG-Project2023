package com.example.myapplication;


/**
 * General account class which specifies the attributes and methods need to make an account
 *
 * @author Linden Sheehy, Zachary Sikka
 * @version 1.0
 */
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

    /**
     * Getter method for returning the username
     *
     * @return username String
     */
    public String getUsername() {

        return this.username;
    }

    /**
     * Getter method for returning the password
     *
     * @return password String
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * Getter method for returning the first name
     *
     * @return first name String
     */
    public String getFirstName() {

        return this.firstName;
    }

    /**
     * Getter method for returning the last name
     *
     * @return last name String
     */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * Getter method for returning the email
     *
     * @return email String
     */
    public String getEmail() {

        return this.email;
    }

    /**
     * Getter method for returning the username
     *
     * @return account type associated with the account
     */
    public AccountType getAccountType() {
        return accountType;
    }
}
