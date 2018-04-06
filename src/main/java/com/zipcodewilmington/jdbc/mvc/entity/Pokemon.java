package com.zipcodewilmington.jdbc.mvc.entity;

import com.zipcodewilmington.jdbc.tools.database.Database;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A entity is an entity which
 */
@Entity
public class Pokemon {
    @Id
    @GeneratedValue
//    @Column(name = "ID")
    private long id;

    private Database uat = Database.UAT;
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
