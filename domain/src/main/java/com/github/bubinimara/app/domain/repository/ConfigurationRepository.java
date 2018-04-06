package com.github.bubinimara.app.domain.repository;

import com.github.bubinimara.app.domain.Configuration;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public interface ConfigurationRepository {

    Observable<Configuration> getConfiguration();
}
