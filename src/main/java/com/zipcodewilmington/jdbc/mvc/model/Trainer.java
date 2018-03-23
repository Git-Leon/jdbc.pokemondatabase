package com.zipcodewilmington.jdbc.mvc.model;

public class Trainer {
    private final long id;
    private final String name;

    public Trainer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
