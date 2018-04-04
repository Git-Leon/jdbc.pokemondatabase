package com.zipcodewilmington.jdbc.tools.database.io;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 3/14/18.
 * This class is responsible for appending SQL files for a subsequent SQLScriptExecutor to execute
 */
public class SQLScriptExecutorBuilder {
    private final Connection connection;
    private final List<InputStream> scriptsToBeExecuted = new ArrayList<InputStream>();

    /**
     * @param conn
     */
    public SQLScriptExecutorBuilder(Connection conn) {
        this.connection = conn;
    }

    /**
     * appends each file in the directory to the files to be imported
     *
     * @param directory
     */
    public void appendDirectory(File directory) {
        assert (directory.isDirectory());
        File[] files = directory.listFiles();
        for (File file : files) {
            if (!file.isDirectory())
                appendScript(file);
        }
    }

    /**
     * appends each file in the directory to the files to be imported
     *
     * @param directoryPath
     */
    public void appendDirectory(String directoryPath) {
        appendDirectory(new File(directoryPath));
    }

    /**
     * @param filePath string representative of a file path
     * @return this; implementation of builder-pattern
     */
    public SQLScriptExecutorBuilder appendScript(String filePath) {
        return appendScript(new File(filePath));
    }

    /**
     * @param file a file to be imported
     * @return this; implementation of builder-pattern
     */
    public SQLScriptExecutorBuilder appendScript(File file) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file.getAbsoluteFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return appendScript(inputStream);
    }

    /**
     * @param in input stream representative of a file path
     * @return this; implementation of builder-pattern
     */
    public SQLScriptExecutorBuilder appendScript(InputStream in) {
        scriptsToBeExecuted.add(in);
        return this;
    }

    public SQLScriptExecutor build() {
        int arraySize = scriptsToBeExecuted.size();
        InputStream[] scripts = scriptsToBeExecuted.toArray(new InputStream[arraySize]);
        return new SQLScriptExecutor(scripts, connection);
    }
}
