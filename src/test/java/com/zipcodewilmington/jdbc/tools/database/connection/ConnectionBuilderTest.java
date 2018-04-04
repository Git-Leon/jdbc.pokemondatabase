package com.zipcodewilmington.jdbc.tools.database.connection;

import com.zipcodewilmington.jdbc.tools.general.exception.SQLeonError;
import org.junit.Test;

import java.sql.Connection;

public class ConnectionBuilderTest {

    @Test(expected = SQLeonError.class)
    public void buildMySqlConnection() throws SQLeonError {
        Connection connection = new ConnectionBuilder()
                .setUrl("jdbc:mysql://localhost/")
                .setDatabaseName("uat")
                .setPort(3037)
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("")
                .build();
    }

    @Test
    public void buildMariaDbConnection() {
        Connection connection = new ConnectionBuilder()
                .setUrl("jdbc:mariadb://localhost/")
                .setPort(3306)
                .setDatabaseName("uat")
                .setServerName("127.0.0.1")
                .setUser("root")
                .setPassword("")
                .build();
    }
}
