package com.zipcodewilmington.jdbc.mvc.service;

import com.zipcodewilmington.jdbc.tools.database.DatabaseInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.function.Supplier;

public interface ServiceInterface<EntityType, IdType extends Serializable> {

    default EntityType create(Supplier<EntityType> supplier) {
        return create(supplier.get());
    }

    default EntityType create(EntityType entity) {
        getDatabase().persist(entity);
        return entity;
    }

    default void remove(IdType id) {
        remove(findById(id));
    }

    default void remove(EntityType entity) {
        if (entity != null) {
            entity = getEntityManager().merge(entity);
            getEntityTransaction().begin();
            getEntityManager().remove(entity);
            getEntityTransaction().commit();
        }
    }

    default EntityType findById(Class<EntityType> entityTypeClass, IdType id) {
        return getEntityManager().find(entityTypeClass, id);
    }

    default EntityType update(IdType id, EntityType newData) {
        return update(id, ()->newData);
    }

    EntityType findById(IdType id);

    EntityType update(IdType id, Supplier<EntityType> supplier);

    EntityManager getEntityManager();

    EntityTransaction getEntityTransaction();

    DatabaseInterface getDatabase();
}
