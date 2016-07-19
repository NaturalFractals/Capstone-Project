package com.developer.jc.newsreporter.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.developer.jc.newsreporter.AnalyticsApplication;
import com.developer.jc.newsreporter.Article;
import com.developer.jc.newsreporter.R;
import com.developer.jc.newsreporter.adapters.FavoritesListAdapter;
import com.developer.jc.newsreporter.content.NewsContract;
import com.developer.jc.newsreporter.service.NewsAsyncTask;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 * @author Jesse Cochran
 */
public class MainActivityFragment extends Fragment {

    private ListView mListView;

    private static final String[] NEWS_COLUMNS = {
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_NEWS_ID,
            NewsContract.NewsEntry.COLUMN_NEWS_TITLE,
            NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR,
            NewsContract.NewsEntry.COLUMN_NEWS_DATE,
            NewsContract.NewsEntry.COLUMN_NEWS_IMAGE_URL,
            NewsContract.NewsEntry.COLUMN_NEWS_ARTICLE
    };

    private boolean mTwoPane;

    public static final int COLUMN_NEWS_ID = 1;
    public static final int COLUMN_NEWS_TITLE = 2;
    public static final int COLUMN_NEWS_AUTHOR = 3;
    public static final int COLUMN_NEWS_DATE = 4;
    public static final int COLUMN_NEWS_IMAGE_URL = 5;
    public static final int COLUMN_NEWS_ARTICLE = 6;

    public static FavoritesListAdapter favoritesListAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Starts Tracking for Google Analytics
        Tracker tracker = ((AnalyticsApplication) getActivity().getApplication()).getTracker();
        tracker.setScreenName("MainActivityFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder()
                .build());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mTwoPane = preferences.getBoolean("twoPane", false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) view.findViewById(R.id.newsList);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity a = getActivity();
        NewsAsyncTask newsAsyncTask = new NewsAsyncTask(getContext(), mListView, mTwoPane, a);
        newsAsyncTask.execute();
    }

    public void updateNews() {
        FavoritesAsyncTask favoritesAsyncTask = new FavoritesAsyncTask(getContext(), mListView, mTwoPane);
        favoritesAsyncTask.execute();
    }

    public void updateNewsMain() {
        FragmentActivity a = getActivity();
        NewsAsyncTask newsAsyncTask = new NewsAsyncTask(getContext(), mListView, mTwoPane, a);
        newsAsyncTask.execute();
    }

    /**
     * Fetches the favorites news articles from the local database
     */
    private class FavoritesAsyncTask extends AsyncTask<Void, Void, List<Article>> {
        private Context mContext;
        private ListView mListView;
        private boolean twoPaneScreen;

        public FavoritesAsyncTask(Context context, ListView listView, boolean twoPane) {
            mContext = context;
            mListView = listView;
            twoPaneScreen = twoPane;
        }


        private List<Article> getArticlesFromDatabase(Cursor cursor) {
            List<Article> articles = new ArrayList<Article>();
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    Article article = new Article(cursor);
                    articles.add(article);
                } while(cursor.moveToNext());
            }
            return articles;
        }


        @Override
        protected List<Article> doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    NewsContract.NewsEntry.CONTENT_URI,
                    NEWS_COLUMNS,
                    null,
                    null,
                    null
            );
            return getArticlesFromDatabase(cursor);
        }

        @Override
        protected void onPostExecute(final List<Article> articles) {
            super.onPostExecute(articles);
            favoritesListAdapter = new FavoritesListAdapter(mContext, articles);
            this.mListView.setAdapter(favoritesListAdapter);
            this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!twoPaneScreen) {
                        Intent detailIntent = new Intent(mContext, DetailActivity.class);
                        Article article = articles.get(position);
                        detailIntent.putExtra("title", article.getTitle());
                        detailIntent.putExtra("id", article.getId());
                        detailIntent.putExtra("author", article.getAuthor());
                        detailIntent.putExtra("date", article.getDate());
                        detailIntent.putExtra("image", article.getImageUrl());
                        detailIntent.putExtra("article", article.getArticle());
                        detailIntent.putExtra("position", position);
                        //checks to see if the fragment is displaying favorites
                        detailIntent.putExtra("check", "check");
                        mContext.startActivity(detailIntent);
                    } else {
                        Article article = articles.get(position);
                        Bundle args = new Bundle();
                        args.putString("article", article.getArticle());
                        args.putString("title", article.getTitle());
                        args.putString("author", article.getAuthor());
                        args.putString("date", article.getDate());
                        args.putString("image", article.getImageUrl());
                        args.putString("check", "check");
                        args.putInt("position", position);
                        DetailActivityFragment fragment = new DetailActivityFragment();
                        fragment.setArguments(args);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_detail_twopane, fragment, "DF")
                                .commit();
                    }
                }
            });
        }
    }

}
