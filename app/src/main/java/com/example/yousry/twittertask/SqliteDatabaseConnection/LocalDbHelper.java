package com.example.yousry.twittertask.SqliteDatabaseConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.yousry.twittertask.MainClasses.TweetBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousry on 4/6/2017.
 */

public class LocalDbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "twittertask";

    // Contacts table name
    private static final String TABLE_TWEETS = "tweets";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TWEET_ID = "tweetid";
    private static final String KEY_TWEET_USERNANE = "tweetusername";
    private static final String KEY_TEXT = "tweettext";
    private static final String KEY_PHOTO = "tweetimageurl";
    private static final String KEY_RETWEET_COUNT = "retweetcount";


    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public LocalDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }





    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LANDMARKS_TABLE = "CREATE TABLE " + TABLE_TWEETS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TWEET_USERNANE + " TEXT,"
                + KEY_TWEET_ID + " INTEGER,"
                + KEY_TEXT + " TEXT,"
                + KEY_PHOTO + " TEXT,"
                + KEY_RETWEET_COUNT + " INTEGER"
                + ")";
        db.execSQL(CREATE_LANDMARKS_TABLE);
    }



    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);

        // Create tables again
        onCreate(db);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void onDelete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);
    }



    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Tweet
    public void addTweet(TweetBody tweet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TWEET_USERNANE, tweet.getUsername());
        values.put(KEY_TWEET_ID, tweet.getTweet_id());
        values.put(KEY_TEXT, tweet.getText());
        values.put(KEY_PHOTO, tweet.getPhoto());
        values.put(KEY_RETWEET_COUNT,tweet.getRetweetCount());
        // Inserting Row
        db.insert(TABLE_TWEETS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Tweet
    public TweetBody getTweetBodyById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TWEETS, new String[] { KEY_ID,
                        KEY_TWEET_ID, KEY_TWEET_USERNANE, KEY_TEXT, KEY_PHOTO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        TweetBody tweetBody = null;
        if (cursor != null) {
            tweetBody = new TweetBody(
                    cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3),cursor.getInt(4));
        }
        // return tweet
        return tweetBody;
    }
    // Getting single Tweet
    public TweetBody getTweetBodyByTweetId(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TWEETS, new String[] { KEY_ID,
                        KEY_TWEET_ID, KEY_TWEET_USERNANE, KEY_TEXT, KEY_PHOTO, KEY_RETWEET_COUNT }, KEY_TWEET_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        TweetBody tweetBody = null;
        if (cursor != null) {
            tweetBody = new TweetBody(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3),cursor.getInt(4));
        }
        // return tweet
        return tweetBody;
    }

    public void Open(){
        db =this.getWritableDatabase();
    }
    public Boolean isOpen(){
        if(db.isOpen()){
            return true;
        }
        return false;
    }
    public void Close()
    {
        db.close();
    }


    // Getting All Tweets
    public List<TweetBody> getAllTweets() {
        List<TweetBody> tweetBodyList = new ArrayList<TweetBody>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TWEETS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                TweetBody tweetBody = new TweetBody();
                tweetBody.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                tweetBody.setUsername(cursor.getString(cursor.getColumnIndex(KEY_TWEET_USERNANE)));
                tweetBody.setTweet_id(cursor.getLong(cursor.getColumnIndex(KEY_TWEET_ID)));
                tweetBody.setText(cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
                tweetBody.setPhoto(cursor.getString(cursor.getColumnIndex(KEY_PHOTO)));
                tweetBody.setRetweetCount(cursor.getInt(cursor.getColumnIndex(KEY_RETWEET_COUNT)));

                // Adding contact to list
                tweetBodyList.add(tweetBody);
            } while (cursor.moveToNext());
        }

        // return contact list
        return tweetBodyList;
    }

    // Updating single Tweet
    public int updateTweet(TweetBody tweetBody) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TWEET_ID, tweetBody.getTweet_id());
        values.put(KEY_TWEET_USERNANE, tweetBody.getUsername());
        values.put(KEY_TEXT, tweetBody.getText());
        values.put(KEY_PHOTO, tweetBody.getPhoto());

        // updating row
        return db.update(TABLE_TWEETS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tweetBody.getId()) });
    }
    // Deleting All Tweets From Db
    public void deleteAllTweets(){
        db.delete(TABLE_TWEETS,null,null);
    }

    // Deleting single Tweet
    public void deleteTweet(TweetBody tweetBody) {
        db = this.getWritableDatabase();
        db.delete(TABLE_TWEETS, KEY_ID + " = ?",
                new String[] { String.valueOf(tweetBody.getId()) });
    }

    // Getting Tweets Count
    public int getTweetCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TWEETS;
        this.db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
