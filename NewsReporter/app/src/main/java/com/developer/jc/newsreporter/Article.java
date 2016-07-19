package com.developer.jc.newsreporter;

import android.database.Cursor;

import com.developer.jc.newsreporter.activities.MainActivityFragment;

/**
 * Represents a news article that is saved into the local SQLite Database
 * @author Jesse Cochran
 */
public class Article {
    private long id;
    private String title;
    private String author;
    private String date;
    private String imageUrl;
    private String article;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Article(Cursor cursor) {
        this.id = cursor.getLong(MainActivityFragment.COLUMN_NEWS_ID);
        this.title = cursor.getString(MainActivityFragment.COLUMN_NEWS_TITLE);
        this.author = cursor.getString(MainActivityFragment.COLUMN_NEWS_AUTHOR);
        this.date = cursor.getString(MainActivityFragment.COLUMN_NEWS_DATE);
        this.imageUrl = cursor.getString(MainActivityFragment.COLUMN_NEWS_IMAGE_URL);
        this.article = cursor.getString(MainActivityFragment.COLUMN_NEWS_ARTICLE);
    }
}
