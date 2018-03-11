package com.github.bubinimara.movies.data.cache;

import com.github.bubinimara.movies.data.entity.MovieEntity;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class InMemoryCache {
    HashMap<String,List<MovieEntity>> map;

    public InMemoryCache() {
        this.map = new HashMap<>();
    }

    public void put(String key,List<MovieEntity> movieEntities){
        //TODO: put map limit
        map.put(key,movieEntities);
    }
    public Observable<List<MovieEntity>> getMoviesFromCache(String key){
        List<MovieEntity> movieEntities = map.get(key);
        if(movieEntities == null){
            return null;
        }
        return Observable.just(movieEntities);
    }
}
