package com.github.bubinimara.movies.data;

import com.github.bubinimara.movies.domain.Language;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public class LanguageRepositoryImpl implements LanguageRepository {

    @Inject
    public LanguageRepositoryImpl() {
    }

    @Override
    public Observable<Language> getLanguageInUse() {
        return Observable.just(new Language("English","en"));
    }

    @Override
    public void setLanguageInUse(Language languageInUse) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Observable<List<Language>> getLanguages() {
        // TODO: get language from api
        throw new RuntimeException("Not implemented yet");
    }
}
