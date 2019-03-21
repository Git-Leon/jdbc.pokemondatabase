package com.zipcodewilmington.jdbc.tools.database.io;

import com.zipcodewilmington.jdbc.tools.database.connection.StatementExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
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
    public void executeScripts() {
        for (InputStream in : scriptsToBeExecuted) {
            execute(in);
        }
    }

    /**
     * @param scripts
     * @return
     */
    private InputStream[] toInputStreams(File[] scripts) {
        InputStream[] inputStreams = new InputStream[scripts.length];
        for (int i = 0; i < scripts.length; i++) {
            File script = scripts[i];
            try {
                String scriptName = script.getName();
                scriptsToBeExecuted[i] = new FileInputStream(scriptName);
            } catch (FileNotFoundException e) {
                throw new Error(e);
            }
        }
        return inputStreams;
    }


    public void execute(InputStream in) {
        StatementExecutor executor = new StatementExecutor(connection);

        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        while (s.hasNext()) {
            String line = s.next();
            boolean condition1 = line.startsWith("/*!");
            boolean condition2 = line.endsWith("*/");
            boolean condition3 = condition1 && condition2;
            if (condition3) {
                int indexOfSpace = line.indexOf(' ');
                line = line.substring(indexOfSpace + 1, line.length() - " */".length());
            }

            if (line.trim().length() > 0) {
                executor.execute(line);
            }
        }
    }


}
