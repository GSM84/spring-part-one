package ru.geekbrains.controller;

public enum Actions {

    REMOVE("remove");

    private final String name;

    private Actions(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
