package com.github.bubinimara.movies.app.rules;

import com.github.bubinimara.movies.app.rules.module.EmptyApplicationDataModule;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import org.mockito.Mockito;

/**
 * Created by davide.
 */
public class MockedMovieDataModuleRule extends DataModuleRule {


    public MockedMovieDataModuleRule() {
        super(new EmptyApplicationDataModule(){
            @Override
            protected MovieRepository createMovieRepository() {
                return Mockito.mock(MovieRepository.class);
            }
        });
    }
}
