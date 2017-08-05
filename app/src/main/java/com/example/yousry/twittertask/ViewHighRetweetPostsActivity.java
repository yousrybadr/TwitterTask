package com.example.yousry.twittertask;

import android.graphics.Color;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yousry.twittertask.Adapters.CustomAdapter;
import com.example.yousry.twittertask.MainClasses.TweetBody;
import com.example.yousry.twittertask.SqliteDatabaseConnection.LocalDbHelper;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.List;

public class ViewHighRetweetPostsActivity extends AppCompatActivity {

    CustomAdapter customAdapter;
    //ListView listView;
    LocalDbHelper localDbHelper;
    List<TweetBody> list;
    List<Long> listIds;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_high_retweet_posts);
        //listView = (ListView) findViewById(R.id.listView);
        linearLayout = (LinearLayout) findViewById(R.id.myLinear);
        localDbHelper =new LocalDbHelper(ViewHighRetweetPostsActivity.this);
        localDbHelper.Open();

        if(localDbHelper.isOpen()) {
            list =localDbHelper.getAllTweets();
        }
        localDbHelper.Close();





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_help){
            CustomDialogClass customDialogClass=new CustomDialogClass(ViewHighRetweetPostsActivity.this);
            customDialogClass.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        listIds =new ArrayList<>();
        for(int i=0;i<list.size();i++){
            listIds.add(list.get(i).getTweet_id());
            Log.i("KEY",list.get(i).toString());
        }
        TweetUtils.loadTweets(listIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> tweets =result.data;
                for (int i=0;i<tweets.size();i++){
                    TweetView tweetView = new TweetView(ViewHighRetweetPostsActivity.this, tweets.get(i), R.style.tw__TweetDarkWithActionsStyle);

                    View v = new View(ViewHighRetweetPostsActivity.this);
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            20
                    );
                    //layoutParams.setMargins(24, 24, 24, 24);
                    v.setLayoutParams(layoutParams);
                    v.setBackgroundColor(Color.WHITE);
                    int _10 = (int) getResources().getDimension(R.dimen.fab_margin);
                    tweetView.setPadding(_10,_10,_10,_10);
                    linearLayout.addView(tweetView);
                    linearLayout.addView(v);

                }
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }
}
