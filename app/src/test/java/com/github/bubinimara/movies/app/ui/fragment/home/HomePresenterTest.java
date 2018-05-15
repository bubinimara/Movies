package com.github.bubinimara.movies.app.ui.fragment.home;

import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.cases.GetConfiguration;
import com.github.bubinimara.movies.domain.cases.GetMostPopularMovie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by davide.
 */
public class HomePresenterTest {

    private HomePresenter presenter;

    @Mock
    HomeView view;
    @Mock
    GetMostPopularMovie getMostPopularMovies;
    @Mock
    GetConfiguration getConfiguration;
    @Mock
    private PageMovie pageMovie;

    private PublishSubject<PageMovie> publishSubject = PublishSubject.create();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        Mockito.when(view.isRestored()).thenReturn(false);
        Mockito.when(getConfiguration.toObservable(Mockito.any())).thenReturn(Observable.just(new Configuration()));
        Mockito.when(getMostPopularMovies.toObservable(Mockito.any())).thenReturn(publishSubject);

        presenter = new HomePresenter();
        presenter.getConfiguration = getConfiguration;
        presenter.getMostPopularMovies = getMostPopularMovies;
        presenter.bindView(view);
    }

    @Test
    public void should_show_list_when_viewShowed() {
        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view).showMovies(Mockito.anyCollection());
    }

    @Test
    public void should_not_show_list_when_viewHidden() {
        Mockito.clearInvocations(view);
        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view).showMovies(Mockito.anyCollection());

        Mockito.clearInvocations(view);
        presenter.viewHidden();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view,Mockito.never()).showMovies(Mockito.anyCollection());

        Mockito.clearInvocations(view);
        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view).showMovies(Mockito.anyCollection());
    }

    @Test
    public void should_not_show_list_when_view_is_recreated() {
        Mockito.when(view.isRestored()).thenReturn(true);

        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view,Mockito.never()).showMovies(Mockito.anyCollection());

    }

    @Test
    public void should_show_list_onLoadMore() {
        Mockito.when(view.getCurrentPage()).thenReturn(1);
        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.clearInvocations(view);

        Mockito.when(view.getCurrentPage()).thenReturn(2);
        presenter.onLoadMore();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view).showMovies(Mockito.any());

    }

    @Test
    public void should_not_load_the_same_page_more_times() {
        Mockito.when(view.getCurrentPage()).thenReturn(1);
        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.clearInvocations(view);

        presenter.onLoadMore();
        publishSubject.onNext(pageMovie);
        Mockito.verify(view).getCurrentPage();
        Mockito.verify(view, Mockito.never()).showMovies(Mockito.any());
    }

    @Test
    public void should_show_error_View_on_error() {
        Mockito.when(view.getCurrentPage()).thenReturn(1);
        presenter.viewShowed();
        publishSubject.onNext(pageMovie);
        Mockito.clearInvocations(view);

        Mockito.when(view.getCurrentPage()).thenReturn(2);
        presenter.onLoadMore();
        publishSubject.onError(new Throwable());
        Mockito.verify(view).showError(Mockito.anyInt());
    }

    @Test
    public void should_do_nothing_if_view_is_hidden() {
        Mockito.when(view.getCurrentPage()).thenReturn(0);

        presenter.viewHidden();
        publishSubject.onNext(pageMovie);
        //TODO: annotate ui thread in methods
        //presenter.onLoadMore(); // <- this came from UI thread ... annotate it

        Mockito.verifyNoMoreInteractions(view);
    }

    @Test
    public void should_should_details_onMovieClicked() {
        presenter.viewShowed();
        presenter.onMovieClicked(Mockito.any(MovieModel.class));
        Mockito.verify(view).showDetailsView(Mockito.any());
    }
}