package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.collections.ProperStack;
import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;

import java.io.*;
import java.sql.Connection;
import java.util.Map;

/**
 * Ensures schemas are initialized only once
 */
public class MigrationsTable {
    private StatementExecutor statementExecutor;

    public MigrationsTable(Connection connection) {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS migrations(filename TEXT)";
        this.statementExecutor = new StatementExecutor(connection);
        statementExecutor.executeAndCommit(sqlStatement);
    }

    public boolean contains(File file) {
        String filename = file.getName();
        String queryStatement = "SELECT COUNT(1) FROM migrations WHERE filename = '" + filename + "'";
        ResultSetHandler rsh = statementExecutor.executeQuery(queryStatement);
        String columnName = rsh.getColumnName(1);
        ProperStack<Map<String, String>> stack = rsh.toStack();
        Map<String, String> firstRow = stack.pop();
        String firstColumnValue = firstRow.get(columnName);
        return firstColumnValue.equals("1");
    }

    public void insert(File file) {
        if (!contains(file) && !file.isDirectory()) {
            String sqlStatement = "INSERT INTO migrations SELECT '" + file.getName() + "'";
            statementExecutor.execute(sqlStatement);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                for (String line; (line = br.readLine()) != null; ) {
                    statementExecutor.execute(line);
                }
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        statementExecutor.commit();
    }

    public void importFilesFromPath(String migrationsPath) {
        File directory = new File(migrationsPath);
        assert (directory.isDirectory());
        File[] files = directory.listFiles();
        for (File file : files) {
            insert(file);
        }
        statementExecutor.commit();
    }

    public void importFilesFromResources() {
        String localProjectRootDirectory = System.getProperty("user.dir");
        String localResourceDirectory = "/src/main/resources/migrations/";
        importFilesFromPath(localProjectRootDirectory + localResourceDirectory);
    }
}
