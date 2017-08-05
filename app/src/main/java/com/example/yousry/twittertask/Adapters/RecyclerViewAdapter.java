package com.example.yousry.twittertask.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yousry.twittertask.R;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anupamchugh on 05/10/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<TweetItemViewHolder> {

    TweetItemViewHolder tweetItemViewHolder;
    List<Tweet> items;
    Context context;

    public RecyclerViewAdapter(List<Tweet> items,Context context) {
        this.items = items;
        this.context=context;
    }

    public RecyclerViewAdapter(Context context) {
        this.items = new ArrayList<Tweet>();
        this.context =context;
    }


    public void setItems(List<Tweet> items) {
        this.items = items;
    }

    @Override
    public TweetItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
        tweetItemViewHolder =new TweetItemViewHolder(view);
        return tweetItemViewHolder;
    }

    @Override
    public void onBindViewHolder(TweetItemViewHolder holder, int position) {
        holder.bind(items.get(position),context);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        items=new ArrayList<>();
        tweetItemViewHolder.myLayout.removeAllViews();
        notifyDataSetChanged();
    }

    public List<Tweet> getItems() {
        return items;
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }
}
