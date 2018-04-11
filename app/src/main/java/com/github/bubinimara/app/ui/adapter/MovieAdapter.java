package com.github.bubinimara.app.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.bubinimara.app.R;
import com.github.bubinimara.app.model.MovieModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davide.
 */
//TODO: generate abstract movie adapter
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder>{

    public interface OnItemClicked{
        void onItemClicked(MovieModel movie);
    }

    public interface OnLoadMore{
        void onLoadMore(int current_page);
    }

    private OnItemClicked onItemClicked;

    private final List<MovieModel> movies;
    private final EndlessRecyclerOnScrollListener scrollListener;


    public MovieAdapter() {
        this.movies = Collections.synchronizedList(new ArrayList<MovieModel>());
        this.scrollListener = new EndlessRecyclerOnScrollListener();
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

    public void setMovies(Collection<MovieModel> moviesCollection){
        synchronized (movies){
            int size = movies.size();
            movies.clear();
            movies.addAll(moviesCollection);
            notifyDataSetChanged();
        }
    }
    public void addMovies(@NonNull Collection<MovieModel> movieCollection){
        synchronized (movies) {
            int size = movies.size();
            movies.addAll(movieCollection);
            notifyItemRangeInserted(size,movieCollection.size());
        }
    }

    public void removeAllMovies() {
        synchronized (movies) {
            int size = movies.size();
            movies.clear();
            scrollListener.reset();
            notifyItemRangeRemoved(0,size);
        }
    }

    public void setOnLoadMore(OnLoadMore onLoadMore) {
        scrollListener.setListener(onLoadMore);
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    public int getCurrentPage() {
        return scrollListener.getCurrentPage();
    }

    public void clean(){
        scrollListener.setListener(null);
        onItemClicked = null;
    }

    public static class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.movie_overview)
        TextView overview;
        @BindView(R.id.movie_year)
        TextView year;
        @BindView(R.id.movie_image)
        ImageView image;

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
            overview.setText(movie.getOverview());
            year.setText(movie.getYear());

            if(movie.haveImageUrl()) {
                Glide.with(itemView.getContext())
                        .load(movie.getImageUrl())
                        .into(image);
            }
        }
    }
}
