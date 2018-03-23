package com.zipcodewilmington.jdbc.tools.database.connection;

import org.junit.Test;

import java.sql.Connection;

public class ConnectionBuilderTest {
    @Test
    public void buildTest() {
        Connection connection = new ConnectionBuilder()
                .setUrl("jdbc:mysql://localhost/")
                .setDatabaseName("dbname")
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("")
                .build();
    }
}
