package com.github.git_leon.utils.jdbc.database;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leon on 3/13/18.
 * converts from java-datatypes to sql-datatypes
 */
public enum DatabaseTypeAdapter {
    INT("integer", "int", "long"),
    CHAR("character"),
    DATETIME("date"),
    TEXT("string");

    private final List<String> javaEquivalentTypes;

    DatabaseTypeAdapter(String... javaEquivalentTypes) {
        this.javaEquivalentTypes = Arrays.asList(javaEquivalentTypes);
    }

    public String toJavaEquivalentType() {
        return javaEquivalentTypes.get(0);
    }

    public Boolean contains(String type) {
        return javaEquivalentTypes.contains(type);
    }

    public static String toSQLEquivalentType(Class<?> javaType) {
        DatabaseTypeAdapter[] values = DatabaseTypeAdapter.values();
        String javaTypeAsString = javaType.getSimpleName();
        for(DatabaseTypeAdapter sqlType : values) {
            if(sqlType.contains(javaTypeAsString.toLowerCase())) {
                return sqlType.name();
            }
        }
        throw new IllegalArgumentException(javaType.getSimpleName() + " is not a valid argument");
    }
}
