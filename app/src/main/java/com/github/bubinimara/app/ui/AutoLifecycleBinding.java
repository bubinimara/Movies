package com.github.bubinimara.app.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.github.bubinimara.app.ui.BasePresenter;
import com.github.bubinimara.app.ui.IView;

/**
 * Created by davide.
 */
public class AutoLifecycleBinding< V extends IView, P extends BasePresenter<V>>
        implements LifecycleObserver {

    private final V view;
    private final P presenter;

    public AutoLifecycleBinding(V view, P presenter) {
        this.view = view;
        this.presenter = presenter;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create(){
        presenter.bindView(view);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        presenter.viewHidden();
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        presenter.viewShowed();
    }
}
