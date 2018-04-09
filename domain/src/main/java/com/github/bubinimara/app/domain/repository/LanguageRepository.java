package com.github.bubinimara.app.domain.repository;

import com.github.bubinimara.app.domain.Language;

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
