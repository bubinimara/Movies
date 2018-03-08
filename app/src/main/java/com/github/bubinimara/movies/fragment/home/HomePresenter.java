package com.github.bubinimara.movies.fragment.home;

import com.github.bubinimara.movies.IPresenter;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by davide.
 */

public class HomePresenter implements IPresenter<HomeView> {

    private HomeView homeView;
    private MockRepo mockRepo;

    private int currentPageNumber;
    private Disposable disposable;

    public HomePresenter() {
        mockRepo = new MockRepo();
        currentPageNumber = 0;
    }

    @Override
    public void bindView(HomeView view) {
        this.homeView = view;
        initialize();
    }

    @Override
    public void unbind() {
        this.homeView = null;
        clear();
    }

    private void initialize() {
        disposable = mockRepo.getMostPopularMovies(currentPageNumber)
                .subscribe(this::onSuccess);
    }

    private void onSuccess(List<MovieModel> movieModels) {
        this.homeView.showMovies(movieModels,currentPageNumber);
    }

    private void clear() {
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private static class MockRepo{
        ArrayList<MovieModel> movies;

        public MockRepo() {
            movies = new ArrayList<>();
        }

        public Observable<List<MovieModel>> getMostPopularMovies(int pageNumber){
            return Observable.create(emitter -> {
                /*Thread.sleep(1000);*/
                for (int i = 0; i < 30; i++) {
                    MovieModel m = new MovieModel();
                    m.setTitle("Movie "+pageNumber+"_"+i);
                    movies.add(m);
                }
                if(!emitter.isDisposed()){
                    emitter.onNext(movies);
                }

            });
        }
    }

}
