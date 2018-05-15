package com.github.bubinimara.movies.app.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by davide.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.Holder<T>>{

    public interface OnItemClicked<T>{
        void onItemClicked(T t);
    }

    public interface OnLoadMore{
        void onLoadMore(int page);
    }

    private final List<T> data;
    private final EndlessRecyclerOnScrollListener scrollListener;

    private OnItemClicked<T> onItemClicked;

    public BaseAdapter() {
        data = Collections.synchronizedList(new ArrayList<T>());
        scrollListener = new EndlessRecyclerOnScrollListener();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder<T> holder, int position) {
        holder.set(getItem(position));
    }

    public T getItem(int position) {
        return data.get(position);
    }

    protected View inflateViewFromResource(ViewGroup parent,@LayoutRes int resId){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return layoutInflater.inflate(resId,parent,false);
    }

    @NonNull
    @Override
    public final Holder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Holder<T> holder = createHolder(parent,viewType);
        holder.setOnItemClicked(onItemClicked);
        return holder;
    }

    abstract Holder<T> createHolder(ViewGroup parent, int viewType);

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(scrollListener);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder<T> holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.set(getItem(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(Collection<T> collection){
        synchronized (data){
            int size = data.size();
            data.clear();
            data.addAll(collection);
            notifyDataSetChanged();
        }
    }
    public void addData(@NonNull Collection<T> collection){
        synchronized (data) {
            int size = data.size();
            data.addAll(collection);
            notifyItemRangeInserted(size,collection.size());
        }
    }

    public void removeData() {
        synchronized (data) {
            int size = data.size();
            data.clear();
            scrollListener.reset();
            notifyItemRangeRemoved(0,size);
        }
    }



    public int getCurrentPage() {
        return scrollListener.getCurrentPage();
    }

    public void setOnLoadMore(OnLoadMore onLoadMore) {
        scrollListener.setListener(onLoadMore);
    }

    public void setOnItemClicked(OnItemClicked<T> onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    public void clean(){
        scrollListener.setListener(null);
        onItemClicked = null;
    }



    public abstract static class Holder<T> extends RecyclerView.ViewHolder{
        private T t;
        private OnItemClicked<T> onItemClicked;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if(onItemClicked!=null){
                    onItemClicked.onItemClicked(t);
                }
            });
        }

        public void set(T t) {
            this.t = t;
            render(this.t);
        }

        public T get() {
            return t;
        }

        public OnItemClicked<T> getOnItemClicked() {
            return onItemClicked;
        }

        public void setOnItemClicked(OnItemClicked<T> onItemClicked) {
            this.onItemClicked = onItemClicked;
        }

        public abstract void render(T t);
    }
}
