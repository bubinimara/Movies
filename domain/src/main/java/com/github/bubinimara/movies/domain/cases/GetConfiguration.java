package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.scheduler.BgScheduler;
import com.github.bubinimara.movies.domain.scheduler.UiScheduler;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public class GetConfiguration extends UseCase<Configuration,GetConfiguration.Params> {

    private final ConfigurationRepository configurationRepository;

    @Inject
    public GetConfiguration(UiScheduler uiScheduler, BgScheduler bgScheduler, ConfigurationRepository configurationRepository) {
        super(uiScheduler, bgScheduler);
        this.configurationRepository = configurationRepository;
    }

    @Override
    Observable<Configuration> buildObservable(Params params) {
        return configurationRepository.getConfiguration();
    }

    public static class Params{

    }
}
