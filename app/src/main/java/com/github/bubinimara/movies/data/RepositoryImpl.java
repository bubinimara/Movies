package com.github.bubinimara.movies.data;

import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.data.entity.TmbApiResponse;
import com.github.bubinimara.movies.data.mock.MockRepo;
import com.github.bubinimara.movies.data.net.ApiClient;
import com.github.bubinimara.movies.data.net.ApiTmb;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class RepositoryImpl implements Repository {
    private MockRepo mockRepo;
    private ApiTmb apiTmb;
    String apiKey = "93aea0c77bc168d8bbce3918cefefa45";
    String language = "en-US";

    public RepositoryImpl() {
        mockRepo = new MockRepo();
        ApiClient client = new ApiClient();
        apiTmb = client.getApiTmb();
    }

    @Override
    public Observable<List<MovieEntity>> getMostPopularMovies(int pageNumber) {
        return apiTmb.getMostPopularVideos(apiKey,language,pageNumber)
                .map(TmbApiResponse::getResults);
    }

    @Override
    public Observable<List<MovieEntity>> searchMovie(String searchTerm, int pageNumber) {
        return apiTmb.searchForVideos(apiKey,language,searchTerm,pageNumber)
                .map(TmbApiResponse::getResults);
    }
}
