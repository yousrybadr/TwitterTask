package com.example.yousry.twittertask.MainClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by yousry on 7/31/2017.
 */

public class TweetBody implements Parcelable{
    int id;
    long tweet_id;
    String username;
    String text;
    String photo;
    int retweetCount;

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * A constructor that initializes the TWEETBODY object
     **/
    public TweetBody(long tweet_id, String username, String text, String photo,int retweetCount) {
        this.id = 0;
        this.tweet_id = tweet_id;
        this.username = username;
        this.text = text;
        this.photo = photo;
        this.retweetCount =retweetCount;


    }


    public TweetBody() {
        this.id = 0;
        this.tweet_id = 0;
        this.username = "";
        this.text = "";
        this.photo = "";
        retweetCount=0;
    }
    public TweetBody(Tweet tweet) {
        this.id = 0;
        this.tweet_id = tweet.getId();
        this.username = tweet.user.screenName;
        this.text = tweet.text;
        this.photo = tweet.user.profileImageUrl;
        this.retweetCount = tweet.retweetCount;

    }

    @Override
    public String toString() {
        return "TweetBody{" +
                "id=" + id +
                ", tweet_id=" + tweet_id +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", photo='" + photo + '\'' +
                ", retweetCount='" + retweetCount + '\'' +
                '}';
    }

    /**
     * A GETTERS and SETTERS
     **/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(long tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeLong(tweet_id);
        parcel.writeString(username);
        parcel.writeString(text);
        parcel.writeString(photo);
        parcel.writeInt(retweetCount);
    }

    /**
     * Retrieving TweetBody data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private TweetBody(Parcel in){
        this.id = in.readInt();
        this.tweet_id = in.readLong();
        this.username = in.readString();
        this.text = in.readString();
        this.photo =in.readString();
        this.retweetCount=in.readInt();
    }
    public static final Creator<TweetBody> CREATOR = new Creator<TweetBody>() {
        @Override
        public TweetBody createFromParcel(Parcel in) {
            return new TweetBody(in);
        }

        @Override
        public TweetBody[] newArray(int size) {
            return new TweetBody[size];
        }
    };
}
