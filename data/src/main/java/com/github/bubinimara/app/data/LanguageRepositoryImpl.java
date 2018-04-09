package com.github.bubinimara.app.data;

import com.github.bubinimara.app.domain.Language;
import com.github.bubinimara.app.domain.repository.LanguageRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
class LanguageRepositoryImpl implements LanguageRepository {

    @Inject
    public LanguageRepositoryImpl() {
    }

    @Override
    public Observable<Language> getLanguageInUse() {
        //return Observable.just(new Language(Locale.getDefault().getDisplayName(),Locale.getDefault().getISO3Language()));
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
