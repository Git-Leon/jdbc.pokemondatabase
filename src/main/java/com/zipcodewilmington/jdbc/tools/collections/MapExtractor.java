package com.zipcodewilmington.jdbc.tools.collections;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by leon on 3/13/18.
 */
public class MapExtractor<KeyType, ValueType> {
    private Map<ValueType, ValueType> map;

    public MapExtractor(Map<ValueType, ValueType> map) {
        this.map = new HashMap<>(map);
    }

    public Long getLong(KeyType key) {
        return Long.parseLong(getString(key));
    }


    public Integer getInteger(KeyType key) {
        return Integer.parseInt(getString(key));
    }

    public String getString(KeyType key) {
        return get(key).toString();
    }

    public ValueType get(KeyType key) {
        return map.get(key);
    }
}
