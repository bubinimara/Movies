package com.github.bubinimara.movies.data.entity;

/**
 * Created by davide.
 */

public class MovieEntity {
    private String title;

    public MovieEntity() {
    }

    public MovieEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
