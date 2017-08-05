package com.example.yousry.twittertask;

import android.net.wifi.hotspot2.pps.Credential;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.yousry.twittertask.BackgroundTask.BackgroundTask;
import com.example.yousry.twittertask.MainClasses.Constants;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class PostTweetActivity extends AppCompatActivity {

    String URL ="https://api.twitter.com/1.1/statuses/retweet/891599645239111681.json";
    BackgroundTask backgroundTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);


        /*Token token = new Token("token_access", "token_secret");
        Credential c = new Credential("user_name", "consumer_key", "consumer_secret", token);
        UserAccountManager m = UserAccountManager.getInstance(c);

        if (m.verifyCredential()) {
            GeoLocation loc = new GeoLocation("+37.5", "+26.7");
            Tweet t = new Tweet("Cool! Geo-located Tweet via Twitter API ME. \o/", loc);
            TweetER ter = TweetER.getInstance(m);
            t = ter.post(t);
        }*/

        backgroundTask =new BackgroundTask(PostTweetActivity.this);
        backgroundTask.setURL_KEY(URL);
        backgroundTask.setTag(BackgroundTask.TAGS.NULL);
        backgroundTask.execute(backgroundTask.getURL_KEY());
        backgroundTask.setListener(new BackgroundTask.MyAsyncListener() {
            @Override
            public void onSuccessfulExecute(String data) {
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            }
        });

    }
}
