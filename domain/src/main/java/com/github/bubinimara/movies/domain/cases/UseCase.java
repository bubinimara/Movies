package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.scheduler.BgScheduler;
import com.github.bubinimara.movies.domain.scheduler.UiScheduler;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by davide.
 */

public abstract class UseCase<T,Params> {
    private final UiScheduler uiScheduler;
    private final BgScheduler bgScheduler;
    private final CompositeDisposable compositeDisposable;

    public UseCase(UiScheduler uiScheduler, BgScheduler bgScheduler) {
        this.uiScheduler = uiScheduler;
        this.bgScheduler = bgScheduler;
        this.compositeDisposable = new CompositeDisposable();
    }

    abstract Observable<T> buildObservable(@Nullable Params params);

    public void execute(@NonNull DisposableObserver<T> observer, Params params){
        Observable<T> tObservable = toObservable(params);
        compositeDisposable.add(tObservable.subscribeWith(observer));
    }

    public Observable<T> toObservable(@Nullable Params params) {
        return buildObservable(params).compose(applySchedulers());
    }

    private ObservableTransformer<T,T> applySchedulers() {
        return observable -> observable
                .subscribeOn(bgScheduler.getScheduler())
                .observeOn(uiScheduler.getScheduler());
    }

    public void clear(){
        compositeDisposable.clear();
    }
    public void dispose() {
        compositeDisposable.dispose();
    }
}
