package com.github.bubinimara.movies.data.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class InMemoryCache<T> {
    private static final int DEFAULT_SIZE = 5;

    //TODO: sync it
    private final SortedMap<WrapperKey,T> map;
    private final int maxSize;

    public InMemoryCache() {
        this.map = new TreeMap<>();
        maxSize = DEFAULT_SIZE;
    }

    public InMemoryCache(SortedMap<WrapperKey, T> map, int maxSize) {
        this.map = map;
        this.maxSize = maxSize;
    }

    public void put(String key, T t){
        if(map.size()>maxSize && !map.containsKey(new WrapperKey(key))){
            map.remove(map.firstKey());
        }
        map.put(new WrapperKey(key),t);
    }
    public Observable<T> getMoviesFromCache(String key){
        T movieEntities = map.get(new WrapperKey(key));
        if(movieEntities == null){
            return null;
        }
        return Observable.just(movieEntities);
    }

    private static class WrapperKey implements Comparable<WrapperKey>{
        final String key;
        final private long timestamp;

        public WrapperKey(String key) {
            this.key = key;
            this.timestamp = System.currentTimeMillis();
        }

        @Override
        public int compareTo(@NonNull WrapperKey o) {
            return (int) (timestamp - o.timestamp);
        }

        @Override
        public String toString() {
            return "WrapperKey{" +
                    "key='" + key + '\'' +
                    '}';
        }
    }
}
