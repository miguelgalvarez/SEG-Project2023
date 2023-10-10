package com.example.myapplication;

import java.util.Random;

public class ClubManagerAccount extends Account {

    public ClubManagerAccount(String username, String password) {
        this.username = username;
        this.password = password;

        Random random = new Random();
        int randomNumber = random.nextInt(1000000);

        this.accountID = randomNumber;

    }
}
