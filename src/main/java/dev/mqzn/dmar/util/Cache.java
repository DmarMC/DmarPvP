package dev.mqzn.dmar.util;

import java.util.Objects;

public class Cache<K, V> {
    
    private final K key;
    private final V value;
    
    public Cache(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cache)) return false;
        Cache<?, ?> cache = (Cache<?, ?>) o;
        return getKey().equals(cache.getKey()) &&
                getValue().equals(cache.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }


}
