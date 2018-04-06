package com.zipcodewilmington.jdbc.tools.database;

import com.zipcodewilmington.jdbc.tools.collections.ProperStack;
import com.zipcodewilmington.jdbc.tools.database.connection.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.Stack;

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

    public void insert(File file) throws IOException {
        if (!contains(file) && !file.isDirectory()) {
            String sqlStatement = "INSERT INTO migrations SELECT '" + file.getName() + "'";
            statementExecutor.execute(sqlStatement);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                for (String line; (line = br.readLine()) != null; ) {
                    statementExecutor.execute(line);
                }
            }
        }
        statementExecutor.commit();
    }

    public void importFilesFromPath(String migrationsPath) throws IOException {
        File directory = new File(migrationsPath);
        assert (directory.isDirectory());
        File[] files = directory.listFiles();
        for (File file : files) {
            insert(file);
        }
        statementExecutor.commit();
    }

    public void importFilesFromResources() throws IOException {
        String localProjectRootDirectory = System.getProperty("user.dir");
        String localResourceDirectory = "/src/main/resources/migrations/";
        importFilesFromPath(localProjectRootDirectory + localResourceDirectory);
    }
}
