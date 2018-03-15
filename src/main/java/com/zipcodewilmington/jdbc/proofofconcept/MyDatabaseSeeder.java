package com.zipcodewilmington.jdbc.proofofconcept;

import com.sun.istack.internal.NotNull;
import com.zipcodewilmington.jdbc.oop.utils.DataSource;
import com.zipcodewilmington.jdbc.oop.utils.ResultSetHandler;

import java.io.*;
import java.sql.*;

public class MyDatabaseSeeder {
    private final DataSource dataSource;
    private String migrationsPath;

    public MyDatabaseSeeder(DataSource dataSource, String migrationsPath) {
        this.dataSource = dataSource;
        this.migrationsPath = System.getProperty("user.dir") + migrationsPath;
    }

    public MyDatabaseSeeder(DataSource dataSource) {
        this(dataSource, "/src/main/resources/migrations/");
    }

    public boolean run() {
        try {
            importFilesFromResourcesDirectory();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void importFilesFromResourcesDirectory() throws IOException, SQLException {
        createMigrationsTable();

        System.out.println(migrationsPath);

        File[] files = new File(migrationsPath).listFiles();

        Connection conn = dataSource.getConnection();

        for (File file : files) {
            if (!isInMigrationsTable(file.getName())) {
                int counter = 0;
                try( BufferedReader br = new BufferedReader(new FileReader(file))) {
                    for(String line; (line = br.readLine()) != null; ) {
                        Statement s = conn.createStatement();
                        s.execute(line);
                        counter++;
                        if (counter % 10 == 0)
                            System.out.print('.');
                        if (counter == 1000) {
                            System.out.println();
                            counter = 0;
                        }
                    }
                }
                insertIntoMigrationsTable(conn, file.getName());
                conn.commit();
            }
        }
        conn.close();
    }

    private void insertIntoMigrationsTable(@NotNull Connection conn, String name) throws SQLException {
        Statement s = conn.createStatement();
        s.execute("INSERT INTO migrations SELECT '" + name + "'");
    }


    public void createMigrationsTable() {
        Connection conn = this.dataSource.getConnection();

        try {
            Statement s = conn.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS migrations(filename TEXT)");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isInMigrationsTable(String filename) {
        Connection conn = this.dataSource.getConnection();

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM migrations WHERE filename = '" + filename + "'");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            String columnName = rsmd.getColumnName(1);
            ResultSetHandler rsh = new ResultSetHandler(resultSet);
            String count = rsh.toStack().pop().get(columnName);

            return count.equals("1");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
