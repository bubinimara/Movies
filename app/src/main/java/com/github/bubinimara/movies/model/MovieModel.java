package com.github.bubinimara.movies.model;

/**
 * Created by davide.
 */

public class MovieModel {
    private String title;
    private String year;
    private String overview;

    public MovieModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
