package com.developer.jc.newsreporter.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.developer.jc.newsreporter.R;
import com.developer.jc.newsreporter.content.NewsContract;
import com.developer.jc.newsreporter.content.NewsDBHelper;

import java.util.ArrayList;

/**
 * Detail Acitivity for News Article. Contains a fragment of the articles details
 * @author Jesse Cochran
 */
public class DetailActivity extends AppCompatActivity {

    private ArrayList<String> articleText;
    private String articleTitle;
    private String articleImageUrl;
    private String articleId;
    private String articleAuthor;
    private String articleDate;
    private String favoritesCheck;
    private Long articleLongId;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        articleText = getIntent().getStringArrayListExtra("article");
        articleImageUrl = getIntent().getStringExtra("image");
        articleTitle = getIntent().getStringExtra("title");
        articleAuthor = getIntent().getStringExtra("author");
        articleDate = getIntent().getStringExtra("date");
        mPosition = getIntent().getIntExtra("position", -1);
        //Checks to see if the Detail Activity is in Favorites
        favoritesCheck = getIntent().getStringExtra("check");
        if(favoritesCheck == null) {
            articleId = getIntent().getStringExtra("id");
        } else {
            articleLongId = getIntent().getLongExtra("id", 0);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Set icon based on detail activity
        if(favoritesCheck == null) {
            fab.setImageResource(R.drawable.starstar);
        } else {
            fab.setImageResource(R.drawable.trashcan);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoritesCheck == null) {
                    long id = Long.parseLong(articleId);
                    insertArticle(id);
                } else {
                    deleteArticle(articleLongId);
                    if(mPosition != -1) {
                        MainActivityFragment.favoritesListAdapter.remove(mPosition);
                        MainActivityFragment.favoritesListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * Inserts an article into the database
     * @param id
     */
    private void insertArticle(long id) {
        final Uri uri = NewsContract.NewsEntry.buildNewsUri(id);
        final Cursor cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null
        );

        if(cursor != null) {
            final ContentValues values = new ContentValues();

            values.put(NewsContract.NewsEntry.COLUMN_NEWS_ID, id);
            values.put(NewsContract.NewsEntry.COLUMN_NEWS_TITLE, articleTitle);
            values.put(NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR, articleAuthor);
            values.put(NewsContract.NewsEntry.COLUMN_NEWS_DATE, articleDate);
            values.put(NewsContract.NewsEntry.COLUMN_NEWS_IMAGE_URL, articleImageUrl);
            String text = "";
            for(int i = 0; i < articleText.size(); i++) {
                text += articleText.get(i) + "\n\n";
            }
            values.put(NewsContract.NewsEntry.COLUMN_NEWS_ARTICLE, text);

            getContentResolver().insert(NewsContract.NewsEntry.CONTENT_URI, values);
        }
        cursor.close();
    }

    /**
     * Deletes a row/article from the database
     * @param id Id of article to be deleted
     */
    private boolean deleteArticle(long id) {
        String stringId = "" + id;
        NewsDBHelper helper = new NewsDBHelper(getBaseContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(NewsContract.NewsEntry.TABLE_NAME, NewsContract.NewsEntry.COLUMN_NEWS_ID
        + "=" + stringId, null) > 0;
    }
}
