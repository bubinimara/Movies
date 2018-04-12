package com.github.bubinimara.movies.domain.repository;

import com.github.bubinimara.movies.domain.Language;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public interface LanguageRepository {
    Observable<Language> getLanguageInUse();
    void setLanguageInUse(Language languageInUse);

    Observable<List<Language>> getLanguages();
}
