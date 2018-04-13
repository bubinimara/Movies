package com.github.bubinimara.movies.data.cache;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
// TODO: extract interface
public class InMemoryCache<T> {
    private static final int DEFAULT_SIZE = 5;
    private final HashMap<WrapperKey,T> map;
    private final int maxSize;

    public InMemoryCache() {
        this.map = new HashMap<>();
        maxSize = DEFAULT_SIZE;
    }

    public int size(){
        return map.size();
    }
    public boolean haveKey(String key){
        return map.containsKey((new WrapperKey(key)));
    }
    public void put(String key, T t){

        if(map.size()>=maxSize && !map.containsKey(new WrapperKey(key))){
            removeOldest();
        }

        map.put((new WrapperKey(key)),t);
    }

    private void removeOldest() {
        ArrayList<WrapperKey> wrapperKeys = new ArrayList<>(map.keySet());
        Collections.sort(wrapperKeys);
        WrapperKey key = wrapperKeys.get(0);
        map.remove(key);
    }

    public Observable<T> get(String key){
        T t = map.get((new WrapperKey(key)));
        if(t == null){
            return Observable.empty();
        }
        return Observable.just(t);
    }

    public int getMaxSize() {
        return maxSize;
    }

    private static class WrapperKey implements Comparable<WrapperKey>{
        final String key;
        final long timestamp;

        WrapperKey(String key) {
            this.key = key;
            this.timestamp = System.currentTimeMillis();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WrapperKey)) return false;

            WrapperKey that = (WrapperKey) o;

            return key.equals(that.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public int compareTo(@NonNull WrapperKey o) {
            return (o.timestamp - timestamp)>0?-1:1;
        }

        @Override
        public String toString() {
            return "WrapperKey{" +
                    "key='" + key + '\'' +
                    '}';
        }
    }
}
