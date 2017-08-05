package com.example.yousry.twittertask.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yousry.twittertask.LoginActivity;
import com.example.yousry.twittertask.R;
import com.example.yousry.twittertask.ShowTweetActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.XAuthAuthorization;
import twitter4j.auth.Authorization;
import twitter4j.auth.BasicAuthorization;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TweetItemViewHolder extends RecyclerView.ViewHolder {
    private TweetView tweetView;
    public LinearLayout myLayout;
    private Button buttonRetweet;
    private static Twitter twitter;
    private static RequestToken requestToken;

    public TweetItemViewHolder(View itemView) {
        super(itemView);
        myLayout  = (LinearLayout) itemView.findViewById(R.id.my_tweet_layout);
        buttonRetweet = (Button)itemView.findViewById(R.id.buttonRetweet);

    }

    public void bind(final Tweet tweet, final Context context) {
        tweetView = new TweetView(context, tweet, R.style.tw__TweetDarkWithActionsStyle);
        //tweetView.setOnActionCallback(actionCallback);
        String s ="Retweet Count : ";
        myLayout.addView(tweetView);

        buttonRetweet.setText(s + String.valueOf(tweet.retweetCount));
        buttonRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



}
