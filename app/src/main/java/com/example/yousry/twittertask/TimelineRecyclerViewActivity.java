package com.example.yousry.twittertask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.yousry.twittertask.MainClasses.Constants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BasicTimelineFilter;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineCursor;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


/**
 * Created by yousry on 7/30/2017.
 */

public class TimelineRecyclerViewActivity extends AppCompatActivity {

    String Tag =TimelineRecyclerViewActivity.class.getSimpleName();
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

    String mUserName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweetui_timeline_recyclerview);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        if(getIntent().hasExtra(Constants.KEY_USERNAME)){
            mUserName =getIntent().getStringExtra(Constants.KEY_USERNAME);
        }else{
            mUserName ="twitterdev";
        }
        /*final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#hiking")
                .maxItemsPerRequest(15)
                .build();*/
        UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(mUserName)
                .maxItemsPerRequest(5).includeReplies(false).includeRetweets(false)
                .build();
        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this)
                        .setTimeline(userTimeline)
                        .setOnActionCallback(new Callback<Tweet>() {
                            @Override
                            public void success(Result<Tweet> result) {
                                Toast.makeText(getApplicationContext(),result.data.idStr,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void failure(TwitterException exception) {

                            }
                        })
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

        recyclerView.setItemViewCacheSize(5);
        recyclerView.setAdapter(adapter);
        //Log.i(Tag,String.valueOf(adapter.getItemCount()));
    }
}