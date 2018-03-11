package com.github.bubinimara.movies.data.mock;

import com.github.bubinimara.movies.data.Repository;
import com.github.bubinimara.movies.data.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class MockRepo {


    private ArrayList<MovieEntity> getMore(String search,int pageNumber){
        ArrayList<MovieEntity> lm = new ArrayList<>();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 30; i++) {
            MovieEntity m = new MovieEntity();
            m.setTitle("Item "+pageNumber+"_"+i + ": "+search);
            lm.add(m);
        }

        return lm;
    }

    public Observable<List<MovieEntity>> getMostPopularMovies(int pageNumber) {
        return Observable.create(emitter -> {
            if(!emitter.isDisposed())
                emitter.onNext(getMore("Movie",pageNumber));
        });    }


    public Observable<List<MovieEntity>> searchMovie(String searchTerm, int pageNumber) {
        return Observable.create(emitter -> {
            if(!emitter.isDisposed()) {
                emitter.onNext(getMore(searchTerm, pageNumber));
            }
        });
    }
}
