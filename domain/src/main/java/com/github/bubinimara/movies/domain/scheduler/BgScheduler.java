package com.github.bubinimara.movies.domain.scheduler;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davide.
 */
public interface BgScheduler {
    Scheduler getScheduler();
}
