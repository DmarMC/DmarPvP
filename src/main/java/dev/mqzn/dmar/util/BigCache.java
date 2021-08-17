package dev.mqzn.dmar.util;

import com.google.common.base.Objects;

public class BigCache<K, V, T> {

    private final K first;
    private final V second;
    private final T third;

    public BigCache(K first, V second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public K getFirst() {
        return first;
    }

    public T getThird() {
        return third;
    }

    public V getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BigCache)) return false;
        BigCache<?, ?, ?> bigCache = (BigCache<?, ?, ?>) o;
        return Objects.equal(getFirst(), bigCache.getFirst()) &&
                Objects.equal(getSecond(), bigCache.getSecond()) &&
                Objects.equal(getThird(), bigCache.getThird());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFirst(), getSecond(), getThird());
    }

}
