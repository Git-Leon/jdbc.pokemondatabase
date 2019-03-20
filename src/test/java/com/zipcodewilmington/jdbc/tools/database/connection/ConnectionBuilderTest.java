package com.zipcodewilmington.jdbc.tools.database.connection;

import org.junit.Test;

public class ConnectionBuilderTest {

    @Test
    public void buildMySqlConnection()  {
        new ConnectionBuilder()
                .setUrl("jdbc:mysql://localhost/")
                .setDatabaseName("uat")
                .setPort(3036)
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("newpass")
                .build();
    }

    @Test
    public void buildMariaDbConnection() {
        new ConnectionBuilder()
                .setUrl("jdbc:mariadb://localhost/")
                .setPort(3306)
                .setDatabaseName("uat")
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("")
                .build();
    }
}
