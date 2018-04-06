package com.github.bubinimara.app.domain.cases;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by davide.
 */
class DisposableObserverTest<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T pageMovie) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
