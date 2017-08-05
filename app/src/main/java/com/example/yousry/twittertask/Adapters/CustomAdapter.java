package com.example.yousry.twittertask.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yousry.twittertask.MainClasses.TweetBody;
import com.example.yousry.twittertask.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * Created by anupamchugh on 05/11/16.
 */



public class CustomAdapter extends ArrayAdapter {
    ViewHolder viewHolder;
    private ArrayList<TweetBody> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        LinearLayout linearLayout;

    }


    public CustomAdapter(ArrayList data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Nullable
    @Override
    public TweetBody getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Task task=new Task(mContext);
        //task.execute(dataSet.get(position));

        /*TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();

        //statusesService.userTimeline(null,"yousrybadr1",5,null,null,null,null,null,true);
        Call<Tweet> call =statusesService.show(dataSet.get(0).getTweet_id(),null,null,null);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                TweetView tweetView = new TweetView(mContext, result.data, R.style.tw__TweetDarkWithActionsStyle);
                viewHolder.linearLayout.addView(tweetView);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });*/
        TweetUtils.loadTweet(dataSet.get(position).getTweet_id(), new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                TweetView tweetView = new TweetView(mContext, result.data, R.style.tw__TweetDarkWithActionsStyle);
                viewHolder.linearLayout.addView(tweetView);
                viewHolder.linearLayout.setDividerPadding(20);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
    private class Task extends AsyncTask<TweetBody,Void,Void>{


        Context context;

        public Task( Context context) {
            this.context = context;
        }


        @Override
        protected Void doInBackground(TweetBody... tweetBodies) {

            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService statusesService = twitterApiClient.getStatusesService();

            //statusesService.userTimeline(null,"yousrybadr1",5,null,null,null,null,null,true);
            Call<Tweet> call =statusesService.show(tweetBodies[0].getTweet_id(),null,null,null);
            /*call.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    TweetView tweetView = new TweetView(mContext, result.data, R.style.tw__TweetDarkWithActionsStyle);
                    viewHolder.linearLayout.addView(tweetView);
                }

                @Override
                public void failure(TwitterException exception) {

                }
            });*/

            TweetUtils.loadTweet(tweetBodies[0].getTweet_id(), new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {

                }

                @Override
                public void failure(TwitterException exception) {

                }
            });
            return null;
        }
    }
}
