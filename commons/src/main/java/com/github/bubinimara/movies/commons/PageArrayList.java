package com.github.bubinimara.movies.commons;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by davide.
 * ArrayList with page info
 */
public class PageArrayList<T> extends ArrayList<T> {
    /**
     * Page number
     */
    private int page;

    public PageArrayList() {
        super();
        page = 0;
    }

    public PageArrayList(int i, int page) {
        super(i);
        this.page = page;
    }

    public PageArrayList(int page) {
        super();
        this.page = page;
    }

    public PageArrayList(Collection<? extends T> collection, int page) {
        super(collection);
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
