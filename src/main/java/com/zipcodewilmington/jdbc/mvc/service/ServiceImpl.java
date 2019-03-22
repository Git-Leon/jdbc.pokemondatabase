package com.zipcodewilmington.jdbc.mvc.service;

import com.zipcodewilmington.jdbc.tools.database.DatabaseInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

abstract public class ServiceImpl
        <EntityType, IdType extends Serializable>
        implements ServiceInterface<EntityType, IdType> {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private DatabaseInterface database;

    public ServiceImpl(DatabaseInterface database) {
        this.database = database;
        this.entityManager = database.getEntityManager();
        this.entityTransaction = entityManager.getTransaction();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityTransaction getEntityTransaction() {
        return entityTransaction;
    }

    public DatabaseInterface getDatabase() {
        return database;
    }
}
