package com.developer.jc.newsreporter.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.developer.jc.newsreporter.Media;
import com.developer.jc.newsreporter.R;
import com.developer.jc.newsreporter.Result;
import com.developer.jc.newsreporter.activities.DetailActivity;
import com.developer.jc.newsreporter.activities.DetailActivityFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Fetches details about an article for Detail Activity
 * @author Jesse Cochran
 */
public class ArticleAsyncTask extends AsyncTask {

    private Context mContext;
    private Result mResult;
    private String mURL;
    private ArrayList<String> articleText;
    private boolean mTwoPane;
    private FragmentActivity mActivity;

    public ArticleAsyncTask(Context context, Result result, boolean twoPane, FragmentActivity activity) {
        mContext = context;
        mResult = result;
        mTwoPane = twoPane;
        mActivity = activity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        mURL = mResult.getUrl();
        String baseUrl = "http://www.nytimes.com";
        String remainingUrl = "";
        for(int i = baseUrl.length(); i < mURL.length(); i++) {
            remainingUrl += mURL.charAt(i);
        }
        WebParser webParser = new WebParser(baseUrl);
        try {
            articleText = webParser.extractParagraphs(remainingUrl);
        } catch(IOException e) {
            Log.d("error", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(!mTwoPane) {
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putStringArrayListExtra("article", articleText);
            detailIntent.putExtra("title", mResult.getTitle());
            detailIntent.putExtra("id", mResult.getAssetId());
            detailIntent.putExtra("author", mResult.getByline());
            detailIntent.putExtra("date", mResult.getPublishedDate());
            Media[] media = mResult.getMedia();
            String urlString = getUrlString(media);
            detailIntent.putExtra("image", urlString);
            mContext.startActivity(detailIntent);
        } else {
            Bundle args = new Bundle();
            args.putStringArrayList("article", articleText);
            args.putString("title", mResult.getTitle());
            args.putString("id", mResult.getAssetId());
            args.putString("author", mResult.getByline());
            args.putString("date", mResult.getPublishedDate());
            Media[] media = mResult.getMedia();
            String urlString = getUrlString(media);
            args.putString("image", urlString);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(args);

            mActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_twopane, fragment, "DF")
                    .commit();
        }
    }

    private String getUrlString(Media[] media) {
        String urlString = "";
        if (media[0] != null && media[0].getMediaMetadata()[1] != null) {
            urlString = media[0].getMediaMetadata()[1].getUrl();
        }
        return urlString;
    }
}
