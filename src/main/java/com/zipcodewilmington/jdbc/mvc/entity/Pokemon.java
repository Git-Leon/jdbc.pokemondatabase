package com.zipcodewilmington.jdbc.mvc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A entity is an entity which
 */
@Entity
public class Pokemon {
    @Id
    private long id;
    private String name;
    private int secondaryType;
    private int primaryType;

    public Pokemon(long id, String name, int secondaryType, int primaryType) {
        this.id = id;
        this.name = name;
        this.secondaryType = secondaryType;
        this.primaryType = primaryType;
    }

    public Pokemon() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondaryType(int secondaryType) {
        this.secondaryType = secondaryType;
    }

    public void setPrimaryType(int primaryType) {
        this.primaryType = primaryType;
    }
}
