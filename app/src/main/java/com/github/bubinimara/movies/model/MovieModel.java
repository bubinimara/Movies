package com.github.bubinimara.movies.model;

/**
 * Created by davide.
 */

public class MovieModel {
    private long id;
    private String title;
    private String year;
    private String overview;
    private String imageUrl;

    public MovieModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean haveImageUrl() {
        return getImageUrl()!=null && !getImageUrl().isEmpty();
    }
}
