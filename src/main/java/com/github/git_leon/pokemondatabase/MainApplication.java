package com.github.git_leon.pokemondatabase;

import com.github.git_leon.pokemondatabase.model.Pokemon;
import com.github.git_leon.utils.jdbc.database.Database;
import com.github.git_leon.utils.jdbc.database.DatabaseTable;
import com.github.git_leon.utils.jdbc.database.DatabaseTableStatmentor;
import com.github.git_leon.utils.jdbc.executor.StatementExecutorInterface;

/**
 * Created by leon on 3/14/18.
 */
public class MainApplication {
    public static void main(String[] args) {
        Database database = Database.UAT;
        database.drop();
        database.create();
        database.use();

        Pokemon pokemonToAddToDatabase = new Pokemon(1L, "Smeargle", 1, null);
        Class<? extends Pokemon> classToPersist = pokemonToAddToDatabase.getClass();
        DatabaseTableStatmentor databaseTableStatmentor = new DatabaseTableStatmentor(database, classToPersist);
        DatabaseTable table = database.getTable(classToPersist.getSimpleName());

        StatementExecutorInterface statementExecutor = database.getStatementExecutor();
        String createStatement = databaseTableStatmentor.getCreateStatement();
        String insertionStatement = databaseTableStatmentor.getInsertionStatement(pokemonToAddToDatabase);
        String insertionStatementHardCoded = "INSERT INTO UAT.pokemon (id, name, primaryType, secondaryType) VALUES (1, 'Smeargle', 1, null);";

        statementExecutor.executeAndCommit(createStatement);
        statementExecutor.executeAndCommit(insertionStatementHardCoded);
//        statementExecutor.executeAndCommit(insertionStatement);

        System.out.println(table.all());
    }
}
