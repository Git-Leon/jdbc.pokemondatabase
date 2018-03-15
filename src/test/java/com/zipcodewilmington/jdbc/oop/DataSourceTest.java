package com.zipcodewilmington.jdbc.oop;

import com.sun.tools.javac.util.Assert;
import com.zipcodewilmington.jdbc.oop.utils.DataSource;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by leon on 3/13/18.
 */
public class DataSourceTest {
    @Test
    public void testConnect() {
        // Given
        String databaseName = "db1";
        String serverName = "127.0.0.1";
        String user = "root";
        String password = "root";
        DataSource dataSource = new DataSource(databaseName, serverName, user, password);

        // When
        Connection connection = dataSource.getConnection();

        // Then
        Assert.checkNonNull(connection);
    }
}
