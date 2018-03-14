package com.zipcodewilmington.jdbc.oop.model;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by leon on 3/14/18.
 * This class is responsible for importing SQL files from local directories
 */
public class SQLFileImporter {
    private final Connection connection;
    private final List<InputStream> filesToBeImported = new ArrayList<InputStream>();

    /**
     * @param conn
     */
    public SQLFileImporter(Connection conn) {
        this.connection = conn;
    }

    /**
     * appends each file in the directory to the files to be imported
     * @param directoryPath
     */
    public void appendDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        for(File file : files) {
            appendFile(file);
        }
    }

    /**
     * @param filePath string representative of a file path
     * @return this; implementation of builder-pattern
     */
    public SQLFileImporter appendFile(String filePath)  {
        return appendFile(new File(filePath));
    }

    /**
     * @param file a file to be imported
     * @return this; implementation of builder-pattern
     */
    public SQLFileImporter appendFile(File file)  {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file.getAbsoluteFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return appendFile(inputStream);
    }

    /**
     * @param in input stream representative of a file path
     * @return this; implementation of builder-pattern
     */
    public SQLFileImporter appendFile(InputStream in)  {
        filesToBeImported.add(in);
        return this;
    }

    /**
     * Imports each of the SQL files that have been appended to this object
     * @throws SQLException
     */
    public void importFiles() throws SQLException {
        for(InputStream in : filesToBeImported) {
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
                        System.out.println(line);
                        st.execute(line);
                    }
                }
            } finally {
                if (st != null) st.close();
            }
        }
    }
}
