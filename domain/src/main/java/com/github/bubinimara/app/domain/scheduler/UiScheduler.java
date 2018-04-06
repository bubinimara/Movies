package com.github.bubinimara.app.domain.scheduler;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davide.
 */

public interface UiScheduler {
    Scheduler getScheduler();
}
