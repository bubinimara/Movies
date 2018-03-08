package com.github.bubinimara.movies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davide.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder>{



    public interface OnItemClicked{
        void onItemClicked(MovieModel movie);
    }

    public interface OnLoadMore{
        void onLoadMore(int current_page);
    }

    private OnItemClicked onItemClicked;

    private List<MovieModel> movies;
    private EndlessRecyclerOnScrollListener scrollListener;

    public MovieAdapter() {
        this.movies = new ArrayList<>();
        this.scrollListener = new EndlessRecyclerOnScrollListener();
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    public void setOnLoadMore(OnLoadMore onLoadMore) {
        scrollListener.setListener(onLoadMore);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(scrollListener);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_movie,parent,false);
        return new Holder(view, onItemClicked);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(@NonNull Collection<MovieModel> movies, int page){
        this.movies.addAll(movies);
    }


    public static class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_title)
        TextView title;
        private MovieModel movie;

        private final OnItemClicked onItemClicked;

        public Holder(View itemView, OnItemClicked onItemClicked) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.onItemClicked = onItemClicked;
        }

        @OnClick(R.id.movie_root)
        void onClick(){
            if (onItemClicked!=null && getAdapterPosition()!=RecyclerView.NO_POSITION) {
                onItemClicked.onItemClicked(movie);
            }
        }
        public void setMovie(MovieModel movie) {
            this.movie = movie;
            render();
        }

        private void render() {
            title.setText(movie.getTitle());
        }
    }
}
