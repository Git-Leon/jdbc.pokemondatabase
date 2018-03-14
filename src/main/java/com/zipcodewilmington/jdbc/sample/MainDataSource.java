package com.zipcodewilmington.jdbc.sample;

import org.mariadb.jdbc.MySQLDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//https://github.com/hjnowakowski/jdbctest
public class MainDataSource {

    public final String databaseName = "classicmodels";
    public final String SERVER_NAME = "127.0.0.1";
    public final String USER = "root";
    public final String PASSWORD = "";



    public static void main(String[] args) {
        new MainDataSource().run();
    }


    public void run() {
        MySQLDataSource dataSource = new MySQLDataSource();
        dataSource.setDatabaseName(databaseName);
        dataSource.setServerName(SERVER_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        seed(dataSource);

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pokemon.pokemon_trainer");

            while (resultSet.next()) {
////                String productName = resultSet.getString("productName");
////                double buyPrice = resultSet.getDouble("buyPrice");
//
//                System.out.println(productName + ": " + buyPrice);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void seed(MySQLDataSource dataSource) {
        try {
            Connection c = dataSource.getConnection();
            Statement s = c.createStatement();
            s.execute("CREATE SCHEMA IF NOT EXISTS pokemon;");
            dataSource.setDatabaseName("pokemon");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}