package com.github.bubinimara.app.data;

import com.github.bubinimara.app.data.cache.InMemoryCache;
import com.github.bubinimara.app.data.entity.ConfigurationEntity;
import com.github.bubinimara.app.data.net.ApiTmb;
import com.github.bubinimara.app.domain.Configuration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

/**
 * Created by davide.
 */
public class ConfigurationRepositoryImplTest {

    private ConfigurationRepositoryImpl configurationRepo;

    @Mock
    private ApiTmb apiTmb;
    @Mock
    private InMemoryCache<ConfigurationEntity> cache;

    @Mock
    private ConfigurationEntity configuration;
    @Mock
    private Throwable throwable;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        configurationRepo = new ConfigurationRepositoryImpl(apiTmb,cache);
    }

    @Test
    public void getConfiguration_from_network() {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.empty());
        Mockito.when(apiTmb.getConfiguration()).thenReturn(Observable.just(configuration));
        TestObserver<Configuration> testObserver = TestObserver.create();

        configurationRepo.getConfiguration()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        Mockito.verify(cache,Mockito.atLeastOnce())
                .get(Mockito.anyString());
        Mockito.verify(cache,Mockito.atLeastOnce())
                .put(Mockito.anyString(),Mockito.eq(configuration));
        Mockito.verify(apiTmb,Mockito.times(1)).getConfiguration();
    }
    @Test
    public void getConfiguration_from_cache() {
        Mockito.when(cache.get(Mockito.anyString())).thenReturn(Observable.just(configuration));
        Mockito.when(apiTmb.getConfiguration()).thenReturn(Observable.error(throwable));
        TestObserver<Configuration> testObserver = TestObserver.create();

        configurationRepo.getConfiguration()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        Mockito.verify(cache,Mockito.atLeastOnce())
                .get(Mockito.anyString());
        Mockito.verify(cache,Mockito.never())
                .put(Mockito.anyString(),Mockito.eq(configuration));
        Mockito.verify(apiTmb,Mockito.never()).getConfiguration();
    }
}