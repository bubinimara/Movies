package com.github.bubinimara.movies.app.rx;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by davide.
 */
public class SimpleDisposableObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
