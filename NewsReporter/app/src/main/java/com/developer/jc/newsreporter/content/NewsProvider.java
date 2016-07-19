package com.developer.jc.newsreporter.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Content provider for Database
 * @author Jesse Cochran
 */
public class NewsProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NewsDBHelper mNewsDBHelper;

    private static final int NEWS = 100;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String contentAuthority = NewsContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(contentAuthority, NewsContract.PATH_NEWS, NEWS);
        return uriMatcher;
    }

    /**
     * Initialize the provider. The Android system calls this method immediately after it creates
     * the provider. The content provider object is not created until the content resolver
     * object attempts to access it.
     * @return whether or not the provider was created
     */
    @Override
    public boolean onCreate() {
        mNewsDBHelper = new NewsDBHelper(getContext());
        return true;
    }

    /**
     * Retrieves data from the provider
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return cursor object
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursorToReturn;
        cursorToReturn = mNewsDBHelper.getReadableDatabase().query(
                NewsContract.NewsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        return cursorToReturn;
    }

    /**
     * Returns a MIME type corresponding to a content URI
     * @param uri Universal Resource Identifier
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case(NEWS): {
                return NewsContract.NewsEntry.CONTENT_DIR_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    /**
     * Inserts a new row into the provider. Returns a content URI from the newly inserted row.
     * @param uri Universal Resource Identifier
     * @param values values to be inserted
     * @return content URI from newly inserted row
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        Uri uriToReturn;
        switch (sUriMatcher.match(uri)) {
            case NEWS: {
                long _id = db.insert(NewsContract.NewsEntry.TABLE_NAME, null, values);
                if(_id == 0) {
                    Toast.makeText(getContext(), "not added", Toast.LENGTH_LONG).show();
                }
                if(_id > 0) {
                    uriToReturn = NewsContract.NewsEntry.buildNewsUri(_id);
                    Toast.makeText(getContext(), "added", Toast.LENGTH_LONG).show();
                } else {
                    throw new android.database.SQLException("Failed to insert row");
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uriToReturn;
    }

    /**
     * Deletes a row in the database
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return number of rows deleted
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;

        switch(match) {
            case NEWS: {
                numDeleted = db.delete(NewsContract.NewsEntry.TABLE_NAME, selection, selectionArgs);

                //reset ID
                db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME =  '" +
                NewsContract.NewsEntry.TABLE_NAME + "'");
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unkown uri: " + uri);
            }
        }
        return numDeleted;
    }

    /**
     * Updates an existing row in the database
     * @param uri Universal Resource Identifier
     * @param values Values to be updated
     * @param selection
     * @param selectionArgs
     * @return number of rows updated
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mNewsDBHelper.getWritableDatabase();
        int numUpdated = 0;

        if(values == null) {
            throw new IllegalArgumentException("Cannot have null content values.");
        }

        switch(sUriMatcher.match(uri)) {
            case NEWS: {
                numUpdated = db.update(NewsContract.NewsEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if(numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numUpdated;
    }
}
