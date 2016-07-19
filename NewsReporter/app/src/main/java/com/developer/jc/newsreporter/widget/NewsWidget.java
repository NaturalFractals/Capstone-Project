package com.developer.jc.newsreporter.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.developer.jc.newsreporter.R;
import com.developer.jc.newsreporter.activities.DetailActivity;
import com.developer.jc.newsreporter.activities.MainActivity;
import com.developer.jc.newsreporter.service.NewsAsyncTask;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 * @author Jesse Cochran
 */
public class NewsWidget extends AppWidgetProvider {
    private static ArrayList<String> mTitles;
    private Intent mIntent;
    private static final String NEWS_UPDATE = "com.developer.jc.newsreporter.NEWS_UPDATE";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            //Check reference to remote view to create view on widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_collection);


            //Intent to launch main activity when item on widget list is clicked
            mIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            Intent intentRemote = new Intent(context, NewsWidgetRemoteService.class);
            intentRemote.putStringArrayListExtra("titles", mTitles);
            intentRemote.setData(Uri.parse(intentRemote.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.widget_list, intentRemote);

            Intent clickIntentTemplate = new Intent(context, DetailActivity.class);

            PendingIntent clickPendingIntent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                clickPendingIntent = TaskStackBuilder.create(context)
                        .addNextIntentWithParentStack(clickIntentTemplate)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntent);


            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(NewsAsyncTask.ACTION_DATA_UPDATE.equals(intent.getAction())) {
            mTitles = intent.getExtras().getStringArrayList("titles");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] widgetIds  = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.widget);

        }
    }
}

