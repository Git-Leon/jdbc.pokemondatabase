package com.github.git_leon.pokemondatabase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A entity is an entity which
 */
public class Pokemon {
    private Long id;
    private String name;
    private Integer secondaryType;
    private Integer primaryType;

    public Pokemon(Long id, String name, Integer secondaryType, Integer primaryType) {
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
