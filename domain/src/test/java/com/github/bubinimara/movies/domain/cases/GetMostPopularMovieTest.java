package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

/**
 * Created by davide.
 */
public class GetMostPopularMovieTest {
    private GetMostPopularMovie getMostPopularMovie;

    @Mock
    private MovieRepository repository;

    @Mock
    private PageMovie pageMovie;

    @Mock
    private Throwable throwable;

    private GetMostPopularMovie.Params params = new GetMostPopularMovie.Params(1);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getMostPopularMovie = new GetMostPopularMovie(TestScheduler::new,TestScheduler::new,repository, languageRepository);
    }

    @Test
    public void should_get_videos() throws Exception {
        mockRepositoryResponse(Observable.just(pageMovie));
        TestObserver<PageMovie> testObserver = TestObserver.create();
        getMostPopularMovie.buildObservable(params).subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        testObserver.assertValue(p->p.equals(pageMovie));
    }

    private void mockRepositoryResponse(Observable<PageMovie> just) {
        Mockito.when(repository.getMostPopularMovies(1))
                .thenReturn(just);
    }

    @Test
    public void should_not_throw_error_when_empty() throws Exception {
        mockRepositoryResponse(Observable.empty());
        TestObserver<PageMovie> testObserver = TestObserver.create();

        getMostPopularMovie.buildObservable(params).subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertComplete();
    }
    @Test
    public void should_propagate_error() throws Exception {
        mockRepositoryResponse(Observable.error(throwable));
        TestObserver<PageMovie> testObserver = TestObserver.create();

        getMostPopularMovie.buildObservable(params).subscribe(testObserver);
        testObserver.assertError(throwable);
    }

    @Test
    public void should_dispose() throws Exception {
        mockRepositoryResponse(Observable.empty());

        DisposableObserver<PageMovie> disposable = new DisposableObserverTest<>();

        getMostPopularMovie.execute(disposable,params);
        getMostPopularMovie.dispose();
        assertTrue(disposable.isDisposed());
    }

}