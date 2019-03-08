package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.scheduler.BgScheduler;
import com.github.bubinimara.movies.domain.scheduler.UiScheduler;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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
/*
        return Observable.create(emitter -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!emitter.isDisposed()) {
                //emitter.onError(new Throwable("Err"));
                emitter.onNext(configurationRepository.getConfiguration().blockingFirst());
            }
        });
*/
    }

    public static class Params{

    }
}
