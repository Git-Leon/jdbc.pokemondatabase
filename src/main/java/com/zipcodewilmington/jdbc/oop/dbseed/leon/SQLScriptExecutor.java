package com.zipcodewilmington.jdbc.oop.dbseed.leon;

import com.zipcodewilmington.jdbc.oop.utils.StatementExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by leon on 3/13/18.
 */
public class SQLScriptExecutor {
    private final InputStream[] scriptsToBeExecuted;
    private final Connection connection;


    public SQLScriptExecutor(File[] scripts, Connection connection) {
        this.connection = connection;
        scriptsToBeExecuted = toInputStreams(scripts);
    }


    public SQLScriptExecutor(InputStream[] scripts, Connection connection) {
        this.scriptsToBeExecuted = scripts;
        this.connection = connection;
    }

    /**
     * Imports each of the SQL files that have been appended to this object
     *
     * @throws SQLException
     */
    public void executeScripts(){
        StatementExecutor executor = new StatementExecutor(connection);
        for (InputStream in : scriptsToBeExecuted) {
            Scanner s = new Scanner(in);
            s.useDelimiter("(;(\r)?\n)|(--\n)");
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int indexOfSpace = line.indexOf(' ');
                    line = line.substring(indexOfSpace + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    executor.execute(line);
                }
            }

        }
    }


    // Called upon construction
    private InputStream[] toInputStreams(File[] scripts) {
        InputStream[] inputStreams = new InputStream[scripts.length];
        for (int i = 0; i < scripts.length; i++) {
            File script = scripts[i];
            try {
                String scriptName = script.getName();
                scriptsToBeExecuted[i] = new FileInputStream(scriptName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return inputStreams;
    }

}
