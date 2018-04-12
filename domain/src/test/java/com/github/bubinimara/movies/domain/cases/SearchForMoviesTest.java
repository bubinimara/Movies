package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.Language;
import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;

/**
 * Created by davide.
 */
public class SearchForMoviesTest {

    private SearchForMovies searchForMovies;

    @Mock
    private MovieRepository repository;

    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private PageMovie pageMovie;
    @Mock
    private Throwable throwable;
    private SearchForMovies.Params params = new SearchForMovies.Params("",1);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(languageRepository.getLanguageInUse()).thenReturn(Observable.just(new Language("name","iso")));
        searchForMovies = new SearchForMovies(TestScheduler::new,TestScheduler::new,repository, languageRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void should_return_movies() {
        mockRepositoryResponse(Observable.just(pageMovie));
        TestObserver<PageMovie> testObserver = TestObserver.create();

        searchForMovies.buildObservable(params).subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        testObserver.assertValue(p->p.equals(pageMovie));
    }


    @Test
    public void should_not_throw_error_when_empty() throws Exception {
        mockRepositoryResponse(Observable.empty());

        TestObserver<PageMovie> testObserver = TestObserver.create();
        searchForMovies.buildObservable(params).subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertComplete();
    }

    @Test
    public void should_propagate_error() throws Exception {
        mockRepositoryResponse(Observable.error(throwable));
        TestObserver<PageMovie> testObserver = TestObserver.create();
        searchForMovies.buildObservable(params).subscribe(testObserver);

        testObserver.assertError(throwable);
    }

    @Test
    public void should_dispose() throws Exception {
        mockRepositoryResponse(Observable.empty());

        DisposableObserver<PageMovie> disposable = new DisposableObserverTest<>();

        searchForMovies.execute(disposable,params);
        searchForMovies.dispose();
        assertTrue(disposable.isDisposed());
    }

    private void mockRepositoryResponse(Observable<PageMovie> just) {
        Mockito.when(repository.searchMovie(Mockito.anyString(),Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(just);
    }

}