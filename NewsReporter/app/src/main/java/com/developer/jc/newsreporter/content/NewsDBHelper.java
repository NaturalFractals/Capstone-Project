package com.developer.jc.newsreporter.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class to manage database creation and version management for news articles.
 * @author Jesse Cochran
 */
public class NewsDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "news.db";

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME
                + "(" +

                NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY, " +
                NewsContract.NewsEntry.COLUMN_NEWS_ID + " LONG NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_NEWS_TITLE + " STRING NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR + " STRING NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_NEWS_DATE + " STRING NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_NEWS_IMAGE_URL + " STRING NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_NEWS_ARTICLE + " STRING NOT NULL);";

        db.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(db);
    }
}
