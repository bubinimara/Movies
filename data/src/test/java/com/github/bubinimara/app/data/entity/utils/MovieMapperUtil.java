package com.github.bubinimara.app.data.entity.utils;

import com.github.bubinimara.app.data.entity.MovieEntity;
import com.github.bubinimara.app.domain.Movie;

import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by davide.
 */
public class MovieMapperUtil {
    private static final Long PREFIX_ID = 1L;
    private static final String PREFIX_OVERVIEW = "overview_";
    private static final String PREFIX_POSTER_PATH = "poster_path_";
    private static final String PREFIX_RELEASE_DATA = "release_data_";
    private static final String PREFIX_TITLE = "title_";

    public static void assertMovieEntityWithSuffix(Movie movie, int suffix) {
        assertNotNull(movie);
        assertThat(movie.getId(),is(PREFIX_ID+suffix));
        assertThat(movie.getTitle(),is(PREFIX_TITLE+suffix));
        assertThat(movie.getOverview(),is(PREFIX_OVERVIEW+suffix));
        assertThat(movie.getPosterPath(),is(PREFIX_POSTER_PATH+suffix));
        assertThat(movie.getReleaseDate(),is(PREFIX_RELEASE_DATA+suffix));
    }

    public static MovieEntity createMockMovieEntityFromSuffix(int suffix) {
        MovieEntity mockMovieEntity = Mockito.mock(MovieEntity.class);
        Mockito.when(mockMovieEntity.getId()).thenReturn(PREFIX_ID+suffix);
        Mockito.when(mockMovieEntity.getTitle()).thenReturn(PREFIX_TITLE+suffix);
        Mockito.when(mockMovieEntity.getOverview()).thenReturn(PREFIX_OVERVIEW+suffix);
        Mockito.when(mockMovieEntity.getPoster_path()).thenReturn(PREFIX_POSTER_PATH+suffix);
        Mockito.when(mockMovieEntity.getRelease_date()).thenReturn(PREFIX_RELEASE_DATA+suffix);
        return mockMovieEntity;
    }
}
