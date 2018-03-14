package com.zipcodewilmington.jdbc.sample;

import java.sql.*;

//https://github.com/hjnowakowski/jdbctest
public class MainJdbc {

    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "com.zipcodewilmington.jdbc:mysql://localhost:8888/classicmodels";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

            while (resultSet.next()){
                String productName = resultSet.getString("productName");
                double buyPrice = resultSet.getDouble("buyPrice");

                System.out.println(productName + ": " + buyPrice);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
