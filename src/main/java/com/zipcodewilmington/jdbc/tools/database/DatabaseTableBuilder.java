package com.zipcodewilmington.jdbc.tools.database;

import java.lang.reflect.Field;
import java.util.*;

public class DatabaseTableBuilder {
    private final DatabaseInterface database;

    public DatabaseTableBuilder(DatabaseInterface database) {
        this.database = database;
    }

    public void create(Class<?> classToIntrospect) {

    }

    public String getCreateStatement(Class<?> classToIntrospect) {
        return new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(database.getName())
                .append(".")
                .append(classToIntrospect.getSimpleName())
                .append("(")
                .append(getFieldMap(classToIntrospect).toString())
                .append(")")
                .toString();
    }


    private Map<Class<?>, String> getFieldMap(Class<?> classToIntrospect) {
        Field[] fields = classToIntrospect.getDeclaredFields();
        Map<Class<?>, String> map = createHashMap();
        for (Field field : fields) {
            boolean defaultAccess = field.isAccessible();
            field.setAccessible(true);
            map.put(field.getType(), field.getName());
            field.setAccessible(defaultAccess);
        }
        return map;
    }

    private Map<Class<?>, String> createHashMap() {
        return new HashMap<Class<?>, String>() {
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<Class<?>, String> entry : this.entrySet()) {
                    String sqlType = DatabaseTypeAdapter.toSQLEquivalentType(entry.getKey());
                    String fieldName = entry.getValue();
                    sb
                            .append(fieldName).append(" ")
                            .append(sqlType).append(" null, ");
                }
                String result = sb.toString().replaceAll(", $", "");
                return result;
            }
        };
    }

}
