package com.zipcodewilmington.jdbc.mvc.dao;

import com.github.git_leon.collectionutils.MapCollection;
import com.github.git_leon.collectionutils.maps.MapExtractor;
import com.zipcodewilmington.jdbc.mvc.entity.Pokemon;
import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.DatabaseTable;
import com.zipcodewilmington.jdbc.tools.resultset.ResultSetHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leon on 3/13/18.
 * Dao determines how a client accesses an entity from the Database.
 */
public class PokemonTableDao {
    private final DatabaseTable databaseTable;

    public PokemonTableDao() {
        this.databaseTable = Database.POKEMON.getTable("pokemons");
    }

    public List<Pokemon> getAll() {
        ResultSetHandler rsh = databaseTable.all();
        return get(rsh);
    }

    public List<Pokemon> getFirst(int limit) {
        ResultSetHandler rsh = databaseTable.limit(limit);
        return get(rsh);
    }

    public Pokemon findByid(long id) {
        ResultSetHandler rsh = databaseTable.where("ID = " + id);
        List<Pokemon> pokemons = get(rsh);
        return pokemons.get(0);
    }

    public List<Pokemon> get(ResultSetHandler rsh) {
        MapCollection<String, String> mapCollection = rsh.toMapCollection();
        List<Pokemon> list = new ArrayList<>(mapCollection.size());

        for (Map<String, String> row : mapCollection) {
            MapExtractor<String, String> extractor = new MapExtractor<>(row);
            String name = extractor.getString("name");
            int secondary_type = extractor.getInteger("secondary_type ");
            int primary_type = extractor.getInteger("primary_type");
            long id = extractor.getLong("id");
            Pokemon pokemon = new Pokemon(id, name, secondary_type, primary_type);
            list.add(pokemon);
        }
        return list;
    }
}
