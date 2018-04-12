package com.github.bubinimara.movies.data.cache;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by davide.
 */
public class InMemoryCacheTest {
    private InMemoryCache<String> inMemoryCache;

    @Before
    public void setUp() throws Exception {
        inMemoryCache = new InMemoryCache<>();
    }

    @Test
    public void put() throws Exception {
        TestObserver<String> testObserver = TestObserver.create();
        inMemoryCache.put(getKey(0),getVal(0));
        inMemoryCache.haveKey(getKey(0));
        inMemoryCache.get(getKey(0))
                    .subscribe(testObserver);

        assertThat(testObserver.valueCount(),is(1));
        testObserver.assertValue(getVal(0));

    }

    @Test
    public void should_remove_oldest_values() throws InterruptedException {
        int overflow = 3;
        for (int i = 0; i <inMemoryCache.getMaxSize()+overflow; i++) {
            inMemoryCache.put(getKey(i),getVal(i));
            inMemoryCache.haveKey(getKey(i));
            assertTrue(inMemoryCache.haveKey(getKey(i)));
            Thread.sleep(500);
        }

        assertThat(inMemoryCache.size(),is(inMemoryCache.getMaxSize()));

        for (int i = 0; i < overflow; i++) {
            inMemoryCache.haveKey(getKey(i));
            assertFalse(getKey(i),inMemoryCache.haveKey(getKey(i)));
        }
        for (int i = 0; i < inMemoryCache.getMaxSize(); i++){
            TestObserver<String> testObserver = TestObserver.create();
            Observable<String> moviesFromCache = inMemoryCache.get(getKey(i+overflow));
            moviesFromCache.subscribe(testObserver);
            testObserver.assertValue(getVal(i+overflow));

        }
    }
    @Test
    public void should_insert_all_max_size() throws Exception {
        for (int i = 0; i <inMemoryCache.getMaxSize(); i++) {
            inMemoryCache.put(getKey(i),getVal(i));
            inMemoryCache.haveKey(getKey(i));
            assertTrue(inMemoryCache.haveKey(getKey(i)));
        }

        for (int i = 0; i < inMemoryCache.getMaxSize(); i++){
            TestObserver<String> testObserver = TestObserver.create();
            Observable<String> moviesFromCache = inMemoryCache.get(getKey(i));
            moviesFromCache.subscribe(testObserver);
            testObserver.assertValue(getVal(i));

        }

    }

    @Test
    public void getMoviesFromCache() throws Exception {
    }

    private String getKey(int i){
        return "key_"+i;
    }

    private String getVal(int i){
        return "val_"+i;
    }


}