package com.github.bubinimara.movies.data.entity.mapper;

import com.github.bubinimara.movies.data.entity.PageMovieEntity;
import com.github.bubinimara.movies.domain.PageMovie;

/**
 * Created by davide.
 */

public class PageMovieMapper {

    public static PageMovie transform(PageMovieEntity pageMovieEntity){
        PageMovie pageMovie = new PageMovie();
        pageMovie.setPage(pageMovieEntity.getPage());
        pageMovie.setMovies(MovieMapper.transform(pageMovieEntity.getResults()));

        return pageMovie;
    }
}
