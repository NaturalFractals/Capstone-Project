package com.developer.jc.newsreporter.content;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines tables and columns names for news articles in database
 */
public class NewsContract {
    //Name of the content provider
    public static final String CONTENT_AUTHORITY = "com.developer.jc.newsreporter";
    //Base of all URI's which apps will use to contact the content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Null constructor
     */
    public NewsContract() {
    }

    public static final String PATH_NEWS = "news";

    public static abstract class NewsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_NEWS).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final String TABLE_NAME = "news";
        public static final String _ID = "_id";
        public static final String COLUMN_NEWS_ID = "newsId";
        public static final String COLUMN_NEWS_TITLE = "title";
        public static final String COLUMN_NEWS_AUTHOR = "author";
        public static final String COLUMN_NEWS_DATE = "date";
        public static final String COLUMN_NEWS_IMAGE_URL = "image";
        public static final String COLUMN_NEWS_ARTICLE = "article";

        public static Uri buildNewsUri(final long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
