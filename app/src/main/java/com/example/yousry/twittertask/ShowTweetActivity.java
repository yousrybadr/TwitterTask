package com.example.yousry.twittertask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

public class ShowTweetActivity extends AppCompatActivity {


    TextView textView;
    final Callback<Tweet> actionCallback = new Callback<Tweet>() {


        @Override
        public void success(Result<Tweet> result) {
            // Intentionally blank
        }

        @Override
        public void failure(TwitterException exception) {
            if (exception instanceof TwitterAuthException) {
                // launch custom login flow
                startActivity(new Intent(ShowTweetActivity.this,LoginActivity.class));
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tweet);
        textView = (TextView) findViewById(R.id.textView);
        final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.my_tweet_layout);


        final long tweetId = getIntent().getLongExtra("TweetID",510908133917487104L);

        //TwitterApiClient twitterApiClient=new TwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession());
        //twitterApiClient.getListService().statuses(null,null,"yousrybadr1",null,null,null,2,false,false);
        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                textView.setText(
                        "TEXT is :" +result.data.text +"\n"
                        +"CreatedAt is :" + result.data.createdAt +"\n"
                        +"idStr is :" + result.data.idStr +"\n"
                        +"UserCreatedAt is :" + result.data.user.createdAt + "\n"
                        +"Email is :" + result.data.user.email + "\n"
                        +"Description is :" + result.data.user.description + "\n"
                        +"FavoriteCount is :" + result.data.favoriteCount + "\n"
                        +"FilterLevel is :" + result.data.filterLevel + "\n"
                        +"RetweetCount is :" + result.data.retweetCount + "\n"
                        +"inReplyToUserIdStr is :" + result.data.inReplyToUserIdStr + "\n"
                        +"Name is :" + result.data.user.name + "\n"
                        +"followersCount is :" + result.data.user.followersCount + "\n"

                );

                final TweetView tweetView = new TweetView(ShowTweetActivity.this, result.data, R.style.tw__TweetDarkWithActionsStyle);

                tweetView.setOnActionCallback(actionCallback);

                myLayout.addView(tweetView);
            }

            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(...).show();
            }
        });
    }
}
