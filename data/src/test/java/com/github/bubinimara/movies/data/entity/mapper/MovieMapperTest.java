package com.github.bubinimara.movies.data.entity.mapper;

import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.data.entity.utils.MovieMapperUtil;
import com.github.bubinimara.movies.domain.Movie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by davide.
 */
public class MovieMapperTest {


    @Test
    public void should_map_element() {
        MovieEntity mockMovieEntity = MovieMapperUtil.createMockMovieEntityFromSuffix(1);
        MovieMapperUtil.assertMovieEntityWithSuffix(MovieMapper.transform(mockMovieEntity),1);
    }

    @Test
    public void should_map_list_of_element() {
        int size = 33;
        ArrayList<MovieEntity> movieEntities = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            movieEntities.add(MovieMapperUtil.createMockMovieEntityFromSuffix(i));
        }
        List<Movie> movies = MovieMapper.transform(movieEntities);
        assertNotNull(movies);
        assertThat(movies.size(),is(movieEntities.size()));
        for (int i = 0; i < size; i++) {
            MovieMapperUtil.assertMovieEntityWithSuffix(movies.get(i),i);
        }
    }

}