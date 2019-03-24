package com.zipcodewilmington.jdbc.tools.database;

import java.util.Arrays;
import java.util.List;

public enum DatabaseTypeAdapter {
    INT("integer", "int", "long"),
    CHAR("character"),
    DATETIME("date"),
    VARCHAR("string");

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
