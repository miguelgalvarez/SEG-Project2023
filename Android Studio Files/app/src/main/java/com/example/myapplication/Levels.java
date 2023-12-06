package com.example.myapplication;

public enum Levels {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ELITE("Elite");
    private String name;

    Levels(String name) {
        this.name = name;
    }

    public String toString() {

        return this.name;

    }
}
