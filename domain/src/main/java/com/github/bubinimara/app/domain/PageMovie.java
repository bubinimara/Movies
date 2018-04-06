package com.github.bubinimara.app.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */

public class PageMovie {
    private int page;
    private List<Movie> movies;

    public PageMovie() {
        movies = new ArrayList<>();
        page = 0;

    }

    public PageMovie(int page, List<Movie> movies) {
        this.page = page;
        this.movies = movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PageMovie))
        return super.equals(o);
        PageMovie pageMovie = (PageMovie) o;
        return pageMovie.getMovies().equals(getMovies());
    }
}
