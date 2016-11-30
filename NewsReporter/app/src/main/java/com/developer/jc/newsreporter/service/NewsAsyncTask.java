package com.developer.jc.newsreporter.service;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developer.jc.newsreporter.News;
import com.developer.jc.newsreporter.Result;
import com.developer.jc.newsreporter.adapters.NewsListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fetches results from New York Times most popular news query
 * @author Jesse Cochran
 */
public class NewsAsyncTask extends AsyncTask<Void, Void, Void> {

    private String baseUrl = "https://api.nytimes.com";
    private String apiKey = "API_KEY_HERE";
    public static final String ACTION_DATA_UPDATE = "com.developer.jc.newsreporter.ACTION_DATA_UPDATE";
    private List<Result> mResults;
    private Context mContext;
    private ListView mListView;
    private boolean mTwoPane;
    private FragmentActivity mActivity;

    public NewsAsyncTask(Context context, ListView listView, boolean twoPane, FragmentActivity activity) {
        mContext = context;
        mListView = listView;
        mTwoPane = twoPane;
        mActivity = activity;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(mResults != null) {
            NewsListAdapter newsListAdapter = new NewsListAdapter(mContext, mResults);
            mListView.setAdapter(newsListAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Result result = mResults.get(position);
                    ArticleAsyncTask articleAsyncTask = new ArticleAsyncTask(mContext, result, mTwoPane, mActivity);
                    articleAsyncTask.execute();
                }
            });
        } else if(!isNetworkAvailable()){
            Toast.makeText(mContext, "Sorry, No Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        if(isNetworkAvailable()) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("api-key", apiKey)
                            .build();

                    Request.Builder requestBuilder = original.newBuilder().url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NYTService nytService = retrofit.create(NYTService.class);

            Call<News> call = nytService.listResults();

            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if (response.body() != null) {
                        mResults = response.body().getResults();
                        onPostExecute(null);
                        Intent dataUpdated = new Intent(ACTION_DATA_UPDATE).setPackage(mContext.getPackageName());
                        ArrayList<String> resultTitles = new ArrayList<String>();
                        for (int i = 0; i < mResults.size(); i++) {
                            resultTitles.add(mResults.get(i).getTitle());
                        }
                        dataUpdated.putStringArrayListExtra("titles", resultTitles);
                        mContext.sendBroadcast(dataUpdated);
                    }

                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    Log.d("TAG", "No Internet");
                }
            });
        }

        return null;
    }

    /**
     * Checks if there is currently an active network available
     * @return whether or not there is a network connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
