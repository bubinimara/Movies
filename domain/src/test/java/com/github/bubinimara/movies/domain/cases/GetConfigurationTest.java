package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by davide.
 */
public class GetConfigurationTest {

    private GetConfiguration getConfiguration;

    @Mock
    private ConfigurationRepository reposiory;

    @Mock
    private Configuration configuration;
    @Mock
    private Throwable throwable;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getConfiguration = new GetConfiguration(TestScheduler::new,TestScheduler::new,reposiory);
    }

    @Test
    public void should_get_configuration() {
        Mockito.when(reposiory.getConfiguration()).thenReturn(Observable.just(configuration));
        TestObserver<Configuration> testObserver = TestObserver.create();
        getConfiguration.buildObservable(null)
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);

        testObserver.assertValueAt(0,configuration);
    }

    @Test
    public void should_propagate_error() {
        Mockito.when(reposiory.getConfiguration()).thenReturn(Observable.error(throwable));
        TestObserver<Configuration> testObserver = TestObserver.create();
        getConfiguration.buildObservable(null)
                .subscribe(testObserver);

        testObserver.assertError(throwable);
    }

    @Test
    public void should_dispose() {
        Mockito.when(reposiory.getConfiguration()).thenReturn(Observable.just(configuration));

        DisposableObserver<Configuration> disposable = new DisposableObserverTest<>();
        getConfiguration.execute(disposable,null);

        getConfiguration.dispose();
        assertTrue(disposable.isDisposed());
    }
}