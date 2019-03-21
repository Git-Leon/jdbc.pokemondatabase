package com.zipcodewilmington.jdbc.tools.connection;

import org.junit.Before;

import java.sql.Connection;

public class ConnectionWrapperTest {
    private ConnectionWrapper wrapper;

    @Before
    public void setup() {
        Connection connection = new ConnectionBuilder()
                .setUrl("jdbc:mysql://localhost/")
                .setDatabaseName("dbname")
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("")
                .build();
        this.wrapper = new ConnectionWrapper(connection);
    }
}
