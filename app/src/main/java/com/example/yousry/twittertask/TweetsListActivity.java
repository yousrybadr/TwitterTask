package com.example.yousry.twittertask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yousry.twittertask.Adapters.RecyclerViewAdapter;
import com.example.yousry.twittertask.MainClasses.Constants;
import com.example.yousry.twittertask.MainClasses.TweetBody;
import com.example.yousry.twittertask.SqliteDatabaseConnection.LocalDbHelper;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import retrofit2.Call;

public class TweetsListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    MenuItem item;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    StatusesService statusesService;
    List<Tweet> tweets;
    String mUsername;
    Button button;
    boolean check_sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets_list);

        check_sort =false;

        item = (MenuItem) findViewById(R.id.action_sort);
        button= (Button) findViewById(R.id.button);
        button.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDbHelper localDbHelper=new LocalDbHelper(TweetsListActivity.this);
                localDbHelper.Open();
                if(tweets.size()>=10){
                    for(int i=0;i<10;i++){
                        localDbHelper.addTweet(new TweetBody(tweets.get(i)));
                    }
                }else {
                    for(int i=0;i<tweets.size();i++){
                        localDbHelper.addTweet(new TweetBody(tweets.get(i)));
                    }
                }
                List<TweetBody>tweetBodies= localDbHelper.getAllTweets();
                Toast.makeText(getApplicationContext(),tweetBodies.get(1).toString(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(TweetsListActivity.this,ViewHighRetweetPostsActivity.class));
            }
        });
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.

                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        tweets =new ArrayList<>();



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getIntent().hasExtra(Constants.KEY_USERNAME)){
            mUsername = getIntent().getStringExtra(Constants.KEY_USERNAME);
        }else {
            mUsername= "twitterdev";
        }
        mUsername= "twitterdev";
    }

    @Override
    protected void onResume() {
        super.onResume();
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        statusesService = twitterApiClient.getStatusesService();

        //statusesService.userTimeline(null,"yousrybadr1",5,null,null,null,null,null,true);
        Call<List<Tweet>> listCall =statusesService.userTimeline(null,mUsername,null,null,null,null,true,true,true);

        //Call<List<Tweet>> listCall =statusesService.mentionsTimeline(2,null,null,null,true,true);
        //Call<List<Tweet>> listCall =statusesService.userTimeline(null,mUsername,null,null,null,null,null,null,true);
        listCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(final Result<List<Tweet>> result) {
                //Do something with result
                adapter = new RecyclerViewAdapter(result.data,TweetsListActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void failure(TwitterException exception) {
                //Do something on failure
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.twitter_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id ==R.id.action_logout){
            SharedPreferences.Editor editor = getSharedPreferences(Constants.KEY_SHARED_PREFERENCE, MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            //editor.commit();
            startActivity(new Intent(TweetsListActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return true;
        }else if(id ==R.id.action_timeline){
            startActivity(new Intent(TweetsListActivity.this,TimelineRecyclerViewActivity.class)
                    .putExtra(Constants.KEY_USERNAME,mUsername));

            return true;
        }else if(id==R.id.action_sort){
            //startActivity(new Intent(TweetsListActivity.this,TimelineActivity.class));

            if(!check_sort){
                item.setTitle("Un Sort");
                check_sort =true;
                button.setVisibility(View.VISIBLE);
                tweets =  new ArrayList<>(adapter.getItems());

                Collections.sort(tweets, new Comparator<Tweet>() {
                    @Override
                    public int compare(Tweet tweet, Tweet t1) {
                        return Integer.valueOf(t1.retweetCount).compareTo(Integer.valueOf(tweet.retweetCount));
                    }
                });

                //store Tweets in Database
                LocalDbHelper localDbHelper=new LocalDbHelper(TweetsListActivity.this);
                localDbHelper.Open();
                localDbHelper.deleteAllTweets();
                localDbHelper.close();

                adapter = new RecyclerViewAdapter(tweets,TweetsListActivity.this);
                recyclerView.setAdapter(adapter);
            }else{
                check_sort =false;
                item.setTitle("Sort");
                button.setVisibility(View.GONE);
                onResume();
            }



        }else if(id ==R.id.action_delete){
            adapter.clear();
            adapter.notifyDataSetChanged();
        }else if(id == R.id.action_post){
            startActivity(new Intent(TweetsListActivity.this,PostTweetActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        statusesService = twitterApiClient.getStatusesService();

        //statusesService.userTimeline(null,"yousrybadr1",5,null,null,null,null,null,true);
        Call<List<Tweet>> listCall =statusesService.userTimeline(null,mUsername,null,null,null,null,true,true,true);
        //Call<List<Tweet>> listCall =statusesService.mentionsTimeline(2,null,null,null,true,true);
        //Call<List<Tweet>> listCall =statusesService.userTimeline(null,mUsername,null,null,null,null,null,null,true);
        listCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(final Result<List<Tweet>> result) {
                //Do something with result
                adapter = new RecyclerViewAdapter(result.data,TweetsListActivity.this);

                //adapter.clear();
                //adapter.addAll(result.data);
                recyclerView.setAdapter(adapter);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void failure(TwitterException exception) {
                //Do something on failure
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<Tweet> sort(List<Tweet>tweets){
        List<Tweet> tweets1;
        for(int i=0;i<tweets.size();i++){
            for(int j=i+1;j<tweets.size();j++){
                if(tweets.get(i).retweetCount < tweets.get(j).retweetCount){

                }
            }
        }
        return tweets;
    }

}