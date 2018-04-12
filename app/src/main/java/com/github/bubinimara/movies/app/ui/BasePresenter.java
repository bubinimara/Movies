package com.github.bubinimara.movies.app.ui;

/**
 * Created by davide.
 */

public abstract class BasePresenter<T extends IView> {
    protected T view;

    public void bindView(T view){
        this.view =view;
    }

    public void viewShowed(){

    }

    public void viewHidden(){

    }
    public void unbindView(){
        view = null;
    }

    public T getView() {
        return view;
    }
}
