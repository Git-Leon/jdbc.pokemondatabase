package com.zipcodewilmington.jdbc.oop.model;

import com.zipcodewilmington.jdbc.oop.model.SQLScriptExecutor;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leon on 3/13/18.
 */
public class DatabaseSeeder {
    private final SQLScriptExecutor scriptExecutor;

    public DatabaseSeeder(Connection connection) {
        this.scriptExecutor = new SQLScriptExecutor(connection);
    }

    public void importFilesFromDirectory(File file) {
        assert (file.isDirectory());
        scriptExecutor.appendDirectory(file);

        try {
            scriptExecutor.executeScripts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void importFilesFromDirectory(String directoryPath) {
        importFilesFromDirectory(new File(directoryPath));
    }
}
