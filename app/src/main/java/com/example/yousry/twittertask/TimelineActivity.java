package com.example.yousry.twittertask;

/**
 * Created by yousry on 7/30/2017.
 */
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.yousry.twittertask.MainClasses.Constants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CollectionTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimelineActivity extends ListActivity {
    TweetTimelineListAdapter adapter;
    List<Tweet> tweets;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweets =new ArrayList<>();
                for(int i=0;i<adapter.getCount();i++){
                    tweets.add(adapter.getItem(i));

                }

                tweets =sort(tweets);



                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        result.data.items.addAll(tweets);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });

            }
        });
        // Collection "National Parks Tweets"

        UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("yousrybadr1")
                .maxItemsPerRequest(5).includeReplies(false).includeRetweets(false)
                .build();
        adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .setViewStyle(R.style.tw__TweetDarkWithActionsStyle)
                .build();

        setListAdapter(adapter);
    }

    List<Tweet> sort(List<Tweet>tweets){
        for(int i=0;i<tweets.size();i++){
            for(int j=i+1;j<tweets.size();j++){
                if(tweets.get(i).retweetCount < tweets.get(j).retweetCount){
                    Tweet tweet = tweets.get(i) ;
                    tweets.set(i,tweets.get(j));
                    tweets.set(j,tweet);
                }
            }
        }
        return tweets;
    }

}