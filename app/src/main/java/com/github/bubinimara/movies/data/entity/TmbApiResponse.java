package com.github.bubinimara.movies.data.entity;

import java.util.List;

/**
 * Created by davide.
 */

public class TmbApiResponse {
    private int page;
    private List<MovieEntity> results;
    private int totalPagess;
    private int totalResults;

    public TmbApiResponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }

    public int getTotalPagess() {
        return totalPagess;
    }

    public void setTotalPagess(int totalPagess) {
        this.totalPagess = totalPagess;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
