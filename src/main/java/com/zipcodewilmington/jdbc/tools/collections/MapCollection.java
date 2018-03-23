package com.zipcodewilmington.jdbc.tools.collections;

import java.util.*;


/**
 * Created by leon on 3/13/18.
 */
public class MapCollection<KeyType, ValueType>
        implements Iterable<Map<KeyType, ValueType>> {
    private final List<Map<KeyType, ValueType>> mapList = new ArrayList<Map<KeyType, ValueType>>() {
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map<KeyType, ValueType> row : this) {
                sb.append("\n" + row.toString());
            }
            return sb.toString();
        }
    };

    public ProperStack<Map<KeyType, ValueType>> toStack() {
        return new ProperStack<Map<KeyType, ValueType>>(mapList) {
            @Override
            public String toString() {
                return mapList.toString();
            }
        };
    }

    public void add(Map<KeyType, ValueType> map) {
        mapList.add(map);
    }

    public List<Map<KeyType, ValueType>> toList() {
        return mapList;
    }


    @Override
    public Iterator<Map<KeyType, ValueType>> iterator() {
        return mapList.iterator();
    }


    @Override
    public String toString() {
        return mapList.toString();
    }

    public Integer size() {
        return mapList.size();
    }
}
