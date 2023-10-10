package com.example.myapplication;

import java.util.Random;


public class AdministratorAccount {
    String username;
    String password;
    int accountID;

    public AdministratorAccount(String username, String password) {
        this.username = username;
        this.password = password;

        Random random = new Random();
        int randomNumber = random.nextInt(1000000);

        this.accountID = randomNumber;

    }
}
