package com.github.bubinimara.movies.data.net;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.github.bubinimara.movies.data.entity.ConfigurationEntity;
import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.data.entity.PageMovieEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.observers.TestObserver;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by davide.
 */
public class ApiTmbTest {
    private static final String FAKE_KEY = "MY_FAKE_KEY";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private MockWebServer mockWebServer = new MockWebServer();
    private MockApiResponseCreator mockApiResponseCreator = new MockApiResponseCreator();
    private ApiTmb apiTmb;

    @Before
    public void setUp() throws Exception {
        ApiClient apiClient = new ApiClient(FAKE_KEY, mockWebServer.url("/"));
        apiTmb = apiClient.getApiTmb();
        assertNotNull(apiTmb);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getMostPopularVideos() throws Exception {
        TestObserver<PageMovieEntity> testObserver = TestObserver.create();
        mockWebServer.enqueue(getPageMoviesResponse());

        apiTmb.getMostPopularVideos("lan", 1)
            .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        PageMovieEntity pageMovieEntity = testObserver.values().get(0);
        assertPageMovie(pageMovieEntity);
        assertFalse(pageMovieEntity.getResults().isEmpty());
        MovieEntity movie = pageMovieEntity.getResults().get(0);
        assertMovie0(movie);


    }



    @Test
    public void searchForVideos() throws Exception {
        TestObserver<PageMovieEntity> testObserver = TestObserver.create();
        mockWebServer.enqueue(getPageMoviesResponse());

        apiTmb.searchForVideos("lan","searchTerm", 1)
                .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        PageMovieEntity pageMovieEntity = testObserver.values().get(0);
        assertPageMovie(pageMovieEntity);
        assertFalse(pageMovieEntity.getResults().isEmpty());
        MovieEntity movie = pageMovieEntity.getResults().get(0);
        assertMovie0(movie);

    }

    @Test
    public void findVideoById() throws Exception {
        TestObserver<MovieEntity> testObserver = TestObserver.create();
        mockWebServer.enqueue(getMovieResponse());

        apiTmb.getMovieById(337167,"lang")
                .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        assertMovie0(testObserver.values().get(0));
    }

    @Test
    public void getSimilarMovies() throws Exception {
        TestObserver<PageMovieEntity> testObserver = TestObserver.create();
        mockWebServer.enqueue(getSimilarMoviesResponse());

        apiTmb.getSimilarMovies(337167,"lang",0)
                .subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        PageMovieEntity pageMovieEntity = testObserver.values().get(0);
        assertPageMovie(pageMovieEntity);
        assertFalse(pageMovieEntity.getResults().isEmpty());
        MovieEntity movie = pageMovieEntity.getResults().get(0);
        assertMovie0(movie);

    }


    @Test
    public void getConfiguration() throws IOException {
        TestObserver<ConfigurationEntity> testObserver = TestObserver.create();
        mockWebServer.enqueue(getConfigurationResponse());

        apiTmb.getConfiguration()
                .subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        assertConfiguration(testObserver.values().get(0));

    }

    private void assertConfiguration(ConfigurationEntity configuration) {
        assertArrayEquals(configuration.getChange_keys().toArray(),
                new String[]{"adult","air_date","also_known_as","alternative_titles","biography","birthday","budget","cast","certifications","character_names","created_by","crew","deathday","episode","episode_number","episode_run_time","freebase_id","freebase_mid","general","genres","guest_stars","homepage","images","imdb_id","languages","name","network","origin_country","original_name","original_title","overview","parts","place_of_birth","plot_keywords","production_code","production_companies","production_countries","releases","revenue","runtime","season","season_number","season_regular","spoken_languages","status","tagline","title","translations","tvdb_id","tvrage_id","type","video","videos"});
        assertArrayEquals(configuration.getImages().getPoster_sizes().toArray(),
                new String[]{"w92","w154","w185","w342","w500","w780","original"});
        assertThat(configuration.getImages().getBase_url(),is("http://image.tmdb.org/t/p/"));

    }

    @Test
    public void should_add_params_to_query_for_most_popular_movies() throws Exception {
        mockWebServer.enqueue(getPageMoviesResponse());
        TestObserver<PageMovieEntity> testObserver = TestObserver.create();
        apiTmb.getMostPopularVideos("lang",2).subscribe(testObserver);

        testObserver.assertNoErrors();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        HttpUrl requestUrl = recordedRequest.getRequestUrl();

        assertThat(requestUrl.queryParameter("api_key"),is(FAKE_KEY));
        assertThat(requestUrl.queryParameter("page"),is("2"));
        assertThat(requestUrl.queryParameter("language"),is("lang"));

    }
    @Test
    public void should_add_params_to_query_for_search_movies() throws Exception {
        mockWebServer.enqueue(getPageMoviesResponse());
        TestObserver<PageMovieEntity> testObserver = TestObserver.create();
        apiTmb.searchForVideos("lang","searchTerm",2).subscribe(testObserver);

        testObserver.assertNoErrors();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        HttpUrl requestUrl = recordedRequest.getRequestUrl();

        assertThat(requestUrl.queryParameter("api_key"),is(FAKE_KEY));
        assertThat(requestUrl.queryParameter("page"),is("2"));
        assertThat(requestUrl.queryParameter("language"),is("lang"));
        assertThat(requestUrl.queryParameter("query"),is("searchTerm"));

    }

    private void assertPageMovie(PageMovieEntity pageMovieEntity) {
        assertNotNull(pageMovieEntity);
        assertThat(pageMovieEntity.getPage(),is(1));
        assertThat(pageMovieEntity.getTotalResults(),is(19900));
        assertThat(pageMovieEntity.getTotalPages(),is(995));
        assertNotNull(pageMovieEntity.getResults());
    }

    private void assertMovie0(MovieEntity movie) {
        assertThat(movie.getId(),is((long)337167));
        assertThat(movie.getTitle(),is("Fifty Shades Freed"));
        assertThat(movie.getOverview(),is("Believing they have left behind shadowy figures from their past, newlyweds Christian and Ana fully embrace an inextricable connection and shared life of luxury. But just as she steps into her role as Mrs. Grey and he relaxes into an unfamiliar stability, new threats could jeopardize their happy ending before it even begins."));
        assertThat(movie.getPoster_path(),is("/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg"));
        assertThat(movie.getRelease_date(),is("2018-02-07"));
    }

    public MockResponse getPageMoviesResponse() throws IOException {
        String body = mockApiResponseCreator.getResponseBody("pageMovie.json");
        return new MockResponse().setBody(body).setResponseCode(200);
    }
    public MockResponse getConfigurationResponse() throws IOException {
        String body = mockApiResponseCreator.getResponseBody("configuration.json");
        return new MockResponse().setBody(body).setResponseCode(200);
    }
    public MockResponse getMovieResponse() throws IOException {
        String body = mockApiResponseCreator.getResponseBody("movie.json");
        return new MockResponse().setBody(body).setResponseCode(200);
    }

    private MockResponse getSimilarMoviesResponse() throws IOException {
        String body = mockApiResponseCreator.getResponseBody("similarMovie.json");
        return new MockResponse().setBody(body).setResponseCode(200);
    }

}