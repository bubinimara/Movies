package com.github.bubinimara.app.data;

import com.github.bubinimara.app.data.cache.InMemoryCache;
import com.github.bubinimara.app.data.entity.PageMovieEntity;
import com.github.bubinimara.app.data.net.ApiTmb;
import com.github.bubinimara.app.domain.PageMovie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by davide.
 */
public class MovieRepositoryImplTest {
    private MovieRepositoryImpl repository;

    @Mock
    private ApiTmb apiTmb;
    @Mock
    private InMemoryCache<PageMovieEntity> cache;

    @Mock
    private PageMovieEntity pageMovieEntity;
    @Mock
    private Throwable throwable;

    private String language = "language";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new MovieRepositoryImpl(apiTmb,cache);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void should_get_most_populars_videos_from_network() throws Exception {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.empty());
        Mockito.when(apiTmb.getMostPopularVideos(Mockito.anyString(),Mockito.anyInt())).thenReturn(Observable.just(pageMovieEntity));
        TestObserver<PageMovie> testObserver = TestObserver.create();
        repository.getMostPopularMovies(1)
            .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        Mockito.verify(cache,Mockito.atLeastOnce())
                .put(Mockito.anyString(),Mockito.eq(pageMovieEntity));
    }

    @Test
    public void should_get_most_populars_videos_from_cache() throws Exception {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.just(pageMovieEntity));
//        Mockito.when(apiTmb.getMostPopularVideos(Mockito.anyString(),Mockito.anyInt())).thenThrow(new Throwable());

        TestObserver<PageMovie> testObserver = TestObserver.create();
        repository.getMostPopularMovies(1)
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        Mockito.verify(cache,Mockito.never())
                .put(Mockito.anyString(),Mockito.eq(pageMovieEntity));
        Mockito.verify(apiTmb,Mockito.never())
                .getMostPopularVideos(Mockito.anyString(),Mockito.anyInt());

    }

    @Test
    public void should_throw_error_when_no_most_populars_videos_founded() throws Exception {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.empty()); // no cache
        Mockito.when(apiTmb.getMostPopularVideos(Mockito.anyString(),Mockito.anyInt())).thenReturn(Observable.error(throwable));//net error

        TestObserver<PageMovie> testObserver = TestObserver.create();
        repository.getMostPopularMovies(1)
                .subscribe(testObserver);

        testObserver.assertError(throwable);
        testObserver.completions();

        Mockito.verify(cache,Mockito.never())
                .put(Mockito.anyString(),Mockito.eq(pageMovieEntity));
        Mockito.verify(apiTmb,Mockito.atLeastOnce())
                .getMostPopularVideos(Mockito.anyString(),Mockito.anyInt());

    }



    @Test
    public void should_get_search_videos_from_network() throws Exception {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.empty());
        Mockito.when(apiTmb.searchForVideos(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt())).thenReturn(Observable.just(pageMovieEntity));
        TestObserver<PageMovie> testObserver = TestObserver.create();
        repository.searchMovie("",1)
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        Mockito.verify(cache,Mockito.atLeastOnce())
                .put(Mockito.anyString(),Mockito.eq(pageMovieEntity));
    }

    @Test
    public void should_get_search_for_videos_from_cache() throws Exception {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.just(pageMovieEntity));

        TestObserver<PageMovie> testObserver = TestObserver.create();
        repository.searchMovie("",1)
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        Mockito.verify(cache,Mockito.never())
                .put(Mockito.anyString(),Mockito.eq(pageMovieEntity));
        Mockito.verify(apiTmb,Mockito.never())
                .getMostPopularVideos(Mockito.anyString(),Mockito.anyInt());

    }

    @Test
    public void should_throw_error_when_search_for_videos_not_founded() throws Exception {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.empty()); // no cache
        Mockito.when(apiTmb.searchForVideos(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt())).thenReturn(Observable.error(throwable));//net error

        TestObserver<PageMovie> testObserver = TestObserver.create();
        repository.searchMovie("",1)
                .subscribe(testObserver);

        testObserver.assertError(throwable);
        testObserver.completions();

        Mockito.verify(cache,Mockito.never())
                .put(Mockito.anyString(),Mockito.eq(pageMovieEntity));
        Mockito.verify(apiTmb,Mockito.atLeastOnce())
                .searchForVideos(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt());

    }

    @Test
    public void should_cache_keys_different_between_apis() throws Exception {
        ArrayList<String> args = new ArrayList<>();
        Mockito.when(cache.get(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String key = invocation.getArgument(0);
                    args.add(key);
                    return Observable.just(pageMovieEntity);
                });

        repository.getMostPopularMovies(0)
        .subscribe(TestObserver.create());

        repository.searchMovie("",0)
        .subscribe(TestObserver.create());

        assertFalse(args.isEmpty());
        assertNotEquals(args.get(0),args.get(1));

    }

    @Test
    public void should_cache_different_between_api_calls() throws Exception {
        ArrayList<String> args = new ArrayList<>();
        Mockito.when(cache.get(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String key = invocation.getArgument(0);
                    args.add(key);
                    return Observable.just(pageMovieEntity);
                });

        repository.getMostPopularMovies(0)
                .subscribe(TestObserver.create());
        repository.getMostPopularMovies(0)
                .subscribe(TestObserver.create());
        repository.getMostPopularMovies(1)
                .subscribe(TestObserver.create());

        assertFalse(args.isEmpty());
        assertEquals(args.get(0),args.get(1));
        assertNotEquals(args.get(0),args.get(2));

    }
}