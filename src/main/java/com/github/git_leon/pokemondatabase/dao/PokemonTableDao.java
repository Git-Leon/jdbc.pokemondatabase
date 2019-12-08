package com.github.git_leon.pokemondatabase.dao;

import com.github.git_leon.collectionutils.MapCollection;
import com.github.git_leon.collectionutils.maps.MapExtractor;
import com.github.git_leon.pokemondatabase.model.Pokemon;
import com.github.git_leon.utils.jdbc.database.Database;
import com.github.git_leon.utils.jdbc.database.DatabaseTable;
import com.github.git_leon.utils.jdbc.resultset.ResultSetHandler;

import java.sql.ResultSet;
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
        this.databaseTable = Database.POKEMON.getTable("pokemon");
    }

    public List<Pokemon> getAll() {
        return get(databaseTable.all().getResultSet());
    }

    public List<Pokemon> getFirst(int limit) {
        return get(databaseTable.limit(limit).getResultSet());
    }

    public Pokemon findByid(long id) {
        ResultSetHandler rsh = databaseTable.where("ID = " + id);
        List<Pokemon> pokemons = get(rsh.getResultSet());
        return pokemons.get(0);
    }

    public List<Pokemon> get(ResultSet rs) {
        MapCollection<String, String> mapCollection = new ResultSetHandler(rs).toMapCollection();
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
