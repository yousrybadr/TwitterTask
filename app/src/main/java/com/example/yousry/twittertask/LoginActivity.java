package com.example.yousry.twittertask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yousry.twittertask.MainClasses.Constants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.internal.ActivityLifecycleManager;
import com.twitter.sdk.android.core.models.Tweet;


public class LoginActivity extends AppCompatActivity {


    

    String TAG =LoginActivity.class.getSimpleName();
    TwitterLoginButton loginButton;
    TextView textView;
    private TwitterAuthClient mTwitterAuthClient;
    TwitterApiClient twitterApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView = (TextView) findViewById(R.id.text);
        mTwitterAuthClient = new TwitterAuthClient();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();
            }
        });
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Fonts/idroid.otf");
        textView.setTypeface(typeface);
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.i(TAG,"SUCCESS");


                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                twitterApiClient =TwitterCore.getInstance().getApiClient(session);

                SharedPreferences.Editor editor = getSharedPreferences(Constants.KEY_SHARED_PREFERENCE, MODE_PRIVATE).edit();
                editor.putString(Constants.KEY_SHARED_USERNAME, session.getUserName());
                editor.putLong(Constants.KEY_SHARED_ID, session.getUserId());
                editor.putString(Constants.KEY_SHARED_TOKEN, session.getAuthToken().token);
                editor.putString(Constants.KEY_SHARED_SECRET, session.getAuthToken().secret);

                editor.putBoolean(Constants.KEY_SHARED_LOGIN, true);
                editor.commit();


                //TwitterCore.getInstance().addApiClient(session,);


                /*TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                String username= session.getUserName();
                String userid=String.valueOf(session.getUserId());
                textView.setText(token+"\n"+username+"\n"+secret+"\n"+userid);*/

                startActivity(new Intent(LoginActivity.this,TweetsListActivity.class).putExtra(Constants.KEY_USERNAME,session.getUserName()));
                //startActivity(new Intent(LoginActivity.this,ShowTweetActivity.class));
            }


            @Override
            public void failure(TwitterException exception) {
                Log.e(TAG,"FAILURE");
                Toast.makeText(getApplicationContext()
                        ,getResources().getString(R.string.TwitterException)
                        ,Toast.LENGTH_SHORT
                ).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode,resultCode,data);
    }
}

