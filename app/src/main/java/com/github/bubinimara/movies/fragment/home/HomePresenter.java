package com.github.bubinimara.movies.fragment.home;

import com.github.bubinimara.movies.IPresenter;
import com.github.bubinimara.movies.data.Repository;
import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.model.mapper.MovieModelMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by davide.
 */

public class HomePresenter implements IPresenter<HomeView> {
    private final Scheduler uiScheduler;
    private final Scheduler bgScehduler;
    private final Repository repository;

    private HomeView homeView;

    private int currentPageNumber;
    private Disposable disposable;

    private PublishSubject<Integer> statePublishSubject;

    public HomePresenter(Repository repository, Scheduler bgScehduler, Scheduler uiScheduler) {
        this.repository = repository;
        this.bgScehduler = bgScehduler;
        this.uiScheduler = uiScheduler;

        currentPageNumber = 0;
        statePublishSubject = PublishSubject.create();
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
        observForPageChange();
        statePublishSubject.onNext(currentPageNumber);
    }

    private void observForPageChange(){
        disposable = statePublishSubject
                .flatMap(m -> repository
                        .getMostPopularMovies(m)
                        .subscribeOn(bgScehduler)
                        .observeOn(uiScheduler)
                    )
                .map(MovieModelMapper::transform)
                .subscribe(this::onSuccess);
    }

    private void clear() {
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private void onSuccess(List<MovieModel> movieModels) {
        this.homeView.showMovies(movieModels,currentPageNumber);
    }


    public void onLoadMore(int currentPage){
        currentPageNumber++;
        statePublishSubject.onNext(currentPageNumber);
    }

    private static class MockRepo{
        public Observable<List<MovieModel>> getMostPopularMovie(int pageNumber){
            return Observable.create(emitter -> {
                if(!emitter.isDisposed())
                    emitter.onNext(getMore(pageNumber));
            });
        }


        private ArrayList<MovieModel> getMore(int pageNumber){
            ArrayList<MovieModel> lm = new ArrayList<>();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 30; i++) {
                MovieModel m = new MovieModel();
                m.setTitle("Movie "+pageNumber+"_"+i);
                lm.add(m);
            }

            return lm;
        }
    }

}
