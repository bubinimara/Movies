package com.github.bubinimara.movies.app.model.mapper;

import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.Movie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by davide.
 */
public class MovieModelMapperTest {
    @Mock
    Configuration configuration;

    @Mock
    Movie movie;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_direct_mapping() {
        Mockito.when(movie.getId()).thenReturn(2L);
        Mockito.when(movie.getTitle()).thenReturn("title");
        Mockito.when(movie.getOverview()).thenReturn("overview");

        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);

        assertEquals(movie.getId(),movieModel.getId());
        assertEquals(movie.getTitle(),movieModel.getTitle());
        assertEquals(movie.getOverview(),movieModel.getOverview());
    }

    @Test
    public void should_convert_date_to_year() {
        Mockito.when(movie.getReleaseDate()).thenReturn("2011-10");
        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);
        assertEquals("2011",movieModel.getYear());
    }

    @Test
    public void should_be_empty_malformed_date() {
        Mockito.when(movie.getReleaseDate()).thenReturn("2011 10");
        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);
        assertEquals("",movieModel.getYear());
    }
    @Test
    public void should_be_empty_empty_date() {
        Mockito.when(movie.getReleaseDate()).thenReturn(null);
        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);
        assertTrue(movieModel.getYear().isEmpty());
    }

    @Test
    public void should_have_the_image_url() {

        Configuration.Image image = Mockito.mock(Configuration.Image.class);
        Mockito.when(configuration.getImages()).thenReturn(image);
        Mockito.when(image.getBase_url()).thenReturn("base.url/");
        Mockito.when(image.getPoster_sizes()).thenReturn(Arrays.asList("0"));
        Mockito.when(movie.getPosterPath()).thenReturn("/image.png");
        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);
        assertTrue(movieModel.getYear().isEmpty());

        assertTrue(movieModel.haveImageUrl());
        assertEquals("base.url/0/image.png",movieModel.getImageUrl());
    }

    @Test
    public void should_not_have_the_image_url() {
        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);
        assertTrue(movieModel.getYear().isEmpty());
        assertFalse(movieModel.haveImageUrl());
    }

    //Todo: at the moment get the last image on the array
    @Test
    public void should_get_best_image_url() {
        Configuration.Image image = Mockito.mock(Configuration.Image.class);
        Mockito.when(configuration.getImages()).thenReturn(image);
        Mockito.when(image.getBase_url()).thenReturn("base.url/");
        Mockito.when(image.getPoster_sizes()).thenReturn(Arrays.asList("0","1","2","3"));
        Mockito.when(movie.getPosterPath()).thenReturn("/image.png");
        MovieModel movieModel = MovieModelMapper.transform(movie, configuration);
        assertTrue(movieModel.getYear().isEmpty());

        assertTrue(movieModel.haveImageUrl());
        assertEquals("base.url/3/image.png",movieModel.getImageUrl());
    }

    @Test
    public void should_convert_all_elements_in_a_list() {
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            list.add(Mockito.mock(Movie.class));
        }
        List<MovieModel> movieModels = MovieModelMapper.transform(list, configuration);
        assertEquals(list.size(),movieModels.size());
    }
}