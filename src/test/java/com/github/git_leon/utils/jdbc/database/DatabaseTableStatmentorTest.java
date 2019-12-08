package com.github.git_leon.utils.jdbc.database;

import com.github.git_leon.pokemondatabase.model.Pokemon;
import com.github.git_leon.utils.jdbc.connection.ConnectionBuilder;
import org.junit.Test;

public class DatabaseTableStatmentorTest {
    @Test
    public void test1() {
        System.out.println(
                new DatabaseTableStatmentor(new DatabaseImpl(new ConnectionBuilder()
                        .setUrl("jdbc:mysql://localhost/")
                        .setPort(42000)
                        .setDatabaseName("uat")
                        .setServerName("127.0.0.1")
                        .setUser("root")
                        .setPassword("")
                        .setServerTimezone("UTC"),
                        "UAT"), Pokemon.class)
                        .getCreateStatement());
    }
}
