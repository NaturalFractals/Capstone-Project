package com.developer.jc.newsreporter;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Analytics application that returns a tracker to send data to Google Analytics
 * @author Jesse Cochran
 */
public class AnalyticsApplication extends Application {
    private Tracker mTracker;

    public void startTracking() {
        if(mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.track_app);
            analytics.enableAutoActivityReports(this);
        }
    }

    public Tracker getTracker() {
        startTracking();
        return mTracker;
    }

}
