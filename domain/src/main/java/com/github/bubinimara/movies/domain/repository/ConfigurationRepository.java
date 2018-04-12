package com.github.bubinimara.movies.domain.repository;

import com.github.bubinimara.movies.domain.Configuration;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public interface ConfigurationRepository {

    Observable<Configuration> getConfiguration();
}
