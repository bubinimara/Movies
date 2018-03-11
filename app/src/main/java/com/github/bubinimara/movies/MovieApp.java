package com.github.bubinimara.movies;

import android.app.Application;

import com.github.bubinimara.movies.data.Repository;
import com.github.bubinimara.movies.data.RepositoryImpl;

/**
 * Created by davide.
 */

public class MovieApp extends Application {
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new RepositoryImpl();
    }

    public Repository getRepository() {
        return repository;
    }
}
