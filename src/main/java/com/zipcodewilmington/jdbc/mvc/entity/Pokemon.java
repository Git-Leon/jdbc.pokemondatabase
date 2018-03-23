package com.zipcodewilmington.jdbc.mvc.entity;

/**
 * A entity is an entity which
 */
public class Pokemon {
    private final long id;
    private final String name;
    private final int secondaryType;
    private final int primaryType;

    public Pokemon(long id, String name, int secondaryType, int primaryType) {
        this.id = id;
        this.name = name;
        this.secondaryType = secondaryType;
        this.primaryType = primaryType;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSecondaryType() {
        return secondaryType;
    }

    public int getPrimaryType() {
        return primaryType;
    }
}
