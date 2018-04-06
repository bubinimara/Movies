package com.github.bubinimara.app.data;

import com.github.bubinimara.app.data.cache.InMemoryCache;
import com.github.bubinimara.app.data.entity.ConfigurationEntity;
import com.github.bubinimara.app.data.entity.mapper.ConfigurationMapper;
import com.github.bubinimara.app.data.net.ApiTmb;
import com.github.bubinimara.app.domain.Configuration;
import com.github.bubinimara.app.domain.repository.ConfigurationRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public class ConfigurationRepositoryImpl implements ConfigurationRepository {
    private final ApiTmb apiTmb;
    private final InMemoryCache<ConfigurationEntity> configurationCache;

    @Inject
    public ConfigurationRepositoryImpl(ApiTmb apiTmb) {
        this.apiTmb = apiTmb;
        this.configurationCache = new InMemoryCache<>();
    }

    public ConfigurationRepositoryImpl(ApiTmb apiTmb, InMemoryCache<ConfigurationEntity> configurationCache) {
        this.apiTmb = apiTmb;
        this.configurationCache = configurationCache;
    }

    @Override
    public Observable<Configuration> getConfiguration(){
        String keyForCache = "configuration";
        return configurationCache.get(keyForCache)
                .switchIfEmpty(
                        Observable.defer(() -> apiTmb.getConfiguration()
                                .doOnNext(configurationEntity ->
                                        configurationCache.put(keyForCache,configurationEntity))))
                .map(ConfigurationMapper::transform);
    }
}
