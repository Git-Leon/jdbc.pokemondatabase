package com.zipcodewilmington.jdbc.oop.model;

import java.io.File;
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

    public SQLScriptExecutor(InputStream[] scripts, Connection connection) {
        this.scriptsToBeExecuted = scripts;
        this.connection = connection;
    }

    /**
     * Imports each of the SQL files that have been appended to this object
     * @throws SQLException
     */
    public void executeScripts() throws SQLException {
        for(InputStream in : scriptsToBeExecuted) {
            Scanner s = new Scanner(in);
            s.useDelimiter("(;(\r)?\n)|(--\n)");
            Statement st = null;
            try {
                st = connection.createStatement();
                while (s.hasNext()) {
                    String line = s.next();
                    if (line.startsWith("/*!") && line.endsWith("*/")) {
                        int i = line.indexOf(' ');
                        line = line.substring(i + 1, line.length() - " */".length());
                    }

                    if (line.trim().length() > 0) {
                        st.execute(line);
                    }
                }
            } finally {
                if (st != null) st.close();
            }
        }
    }
}
