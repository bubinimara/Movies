package com.github.bubinimara.app.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davide.
 */

public class PageMovieEntity {
    private int page;
    private List<MovieEntity> results;
    @SerializedName(value = "total_pages")
    private int totalPages;
    @SerializedName(value = "total_results")
    private int totalResults;

    public PageMovieEntity() {
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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
