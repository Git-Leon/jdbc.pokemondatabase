package com.github.git_leon.utils.jdbc.database;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by leon on 3/13/18.
 * Responsible for introspecting a POJO and returning a respective Database creation statement
 */
public class DatabaseTableStatmentor<PersistentType> {
    private final DatabaseInterface database;
    private final Class<PersistentType> persistentTypeClass;

    public DatabaseTableStatmentor(DatabaseInterface database, Class<PersistentType> persistentTypeClass) {
        this.database = database;
        this.persistentTypeClass = persistentTypeClass;
    }

    public String getInsertionStatement(PersistentType instanceToPersist) {
        return null;
    }

    public String getCreateStatement() {
        StringBuilder statementBuilder = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS ")
                .append("%s.%s(id INT AUTO_INCREMENT PRIMARY KEY");

        Field[] fields = persistentTypeClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            String databaseType = DatabaseTypeAdapter.toSQLEquivalentType(fieldType);
            if (!"id".equalsIgnoreCase(fieldName)) {
                statementBuilder
                        .append(", ")
                        .append(fieldName)
                        .append(" ")
                        .append(databaseType);
            }
            field.setAccessible(false);
        }

        return String.format(
                statementBuilder.append(");").toString(),
                database.getName(),
                persistentTypeClass.getSimpleName());
    }
}
