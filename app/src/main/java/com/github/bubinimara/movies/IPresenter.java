package com.github.bubinimara.movies;

/**
 * Created by davide.
 */

public interface IPresenter<T extends IView> {
    void bindView(T view);
    void unbind();
}
