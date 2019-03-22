package com.zipcodewilmington.jdbc.mvc.service;

import com.zipcodewilmington.jdbc.mvc.entity.Pokemon;
import com.zipcodewilmington.jdbc.tools.database.Database;

import java.util.function.Supplier;

/**
 * @author leon on 3/24/18.
 */
public class PokemonService extends Service<Pokemon, Long> {
    public PokemonService() {
        super(Database.POKEMON);
    }

    @Override
    public Pokemon findById(Long id) {
        return super.findById(Pokemon.class, id);
    }

    @Override
    public Pokemon update(Long id, Supplier<Pokemon> newDataSupplier) {
        Pokemon persistedPokemon = findById(id);
        Pokemon newData = newDataSupplier.get();
        persistedPokemon.setName(newData.getName());
        persistedPokemon.setPrimaryType(newData.getPrimaryType());
        persistedPokemon.setSecondaryType(newData.getSecondaryType());
        return findById(id);
    }
}
