package com.zipcodewilmington.jdbc.mvc.service;

import com.zipcodewilmington.jdbc.mvc.entity.Pokemon;
import com.zipcodewilmington.jdbc.tools.database.Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author leon on 3/24/18.
 */
public class PokemonService {
    private EntityManager entityManager = Database.POKEMON.getEntityManager();
    private EntityTransaction entityTransaction = entityManager.getTransaction();


    public Pokemon create(Long id, String name, Integer primaryType, Integer secondaryType) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(id);
        pokemon.setName(name);
        pokemon.setPrimaryType(primaryType);
        pokemon.setSecondaryType(secondaryType);
        return create(pokemon);
    }

    public Pokemon create(Pokemon pokemon) {
        Database.POKEMON.persist(pokemon);
        return pokemon;
    }

    public Pokemon findById(Long id) {
        return entityManager.find(Pokemon.class, id);
    }

    public void update(long id, Pokemon newData) {
        String newName = newData.getName();
        Integer newPrimaryType = newData.getPrimaryType();
        Integer newSecondaryType = newData.getSecondaryType();
        update(id, newName, newPrimaryType, newSecondaryType);
    }


    public void update(long id, String newName, Integer newPrimaryType, Integer newSecondaryType) {
        Pokemon persistedPokemon = findById(id);
        persistedPokemon.setName(newName);
        persistedPokemon.setPrimaryType(newPrimaryType);
        persistedPokemon.setSecondaryType(newSecondaryType);
    }

    public void remove(long id) {
        remove(findById(id));
    }

    public void remove(Pokemon pokemon) {
        if (pokemon != null) {
            pokemon = entityManager.merge(pokemon);
            entityTransaction.begin();
            entityManager.remove(pokemon);
            entityTransaction.commit();
        }
    }
}
