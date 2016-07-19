package com.developer.jc.newsreporter.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.developer.jc.newsreporter.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.Tracker;

/**
 * Main Activity of Application
 * @author Jesse Cochran
 */
public class MainActivity extends AppCompatActivity {

    private Tracker tracker;
    public boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(isNetworkAvailable()) {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        if(findViewById(R.id.fragment_detail_twopane) != null) {
            mTwoPane = true;
            if(savedInstanceState == null) {
                DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_twopane, detailActivityFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putBoolean("twoPane", mTwoPane).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        MainActivityFragment fragment = (MainActivityFragment) fm.findFragmentById(R.id.fragment);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorites) {
            fragment.updateNews();
        } else if(id == R.id.action_main) {
            fragment.updateNewsMain();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean getTwoPane() {
        return mTwoPane;
    }

    /**
     * Checks if there is currently an active network available
     * @return whether or not there is a network connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
