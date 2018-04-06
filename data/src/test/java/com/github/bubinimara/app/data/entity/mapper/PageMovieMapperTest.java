package com.github.bubinimara.app.data.entity.mapper;

import android.support.annotation.NonNull;

import com.github.bubinimara.app.data.entity.MovieEntity;
import com.github.bubinimara.app.data.entity.PageMovieEntity;
import com.github.bubinimara.app.data.entity.utils.MovieMapperUtil;
import com.github.bubinimara.app.domain.PageMovie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by davide.
 */
public class PageMovieMapperTest {

    private static final int PAGE_NUMBER = 2;
    private static final int MOVIE_SIZE = 33;
    private int TOTAL_PAGES = 20;

    @Test
    public void transform() {

        PageMovie pageMovie = PageMovieMapper.transform(createMockPageMovie());
        assertNotNull(pageMovie);
        assertThat(pageMovie.getPage(),is(PAGE_NUMBER));
        assertThat(pageMovie.getMovies().size(),is(MOVIE_SIZE));
        for (int i = 0; i < MOVIE_SIZE; i++) {
            MovieMapperUtil.assertMovieEntityWithSuffix(pageMovie.getMovies().get(i),i);
        }
    }

    private PageMovieEntity createMockPageMovie() {
        PageMovieEntity mockPageMovie = new PageMovieEntity();
        mockPageMovie.setPage(PAGE_NUMBER);
        mockPageMovie.setResults(createMockMovieEntities(MOVIE_SIZE));
        mockPageMovie.setTotalPages(TOTAL_PAGES);
        mockPageMovie.setTotalResults(MOVIE_SIZE);
        return mockPageMovie;
    }

    @NonNull
    private List<MovieEntity> createMockMovieEntities(int capacity) {
        List<MovieEntity> mockListMovie = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            mockListMovie.add(MovieMapperUtil.createMockMovieEntityFromSuffix(i));
        }
        return mockListMovie;
    }


}