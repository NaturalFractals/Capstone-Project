package com.developer.jc.newsreporter.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.developer.jc.newsreporter.R;

import java.util.ArrayList;

/**
 * This class provides the remote views factory used to populate the collection view for the widget.
 */
public class NewsWidgetRemoteService extends RemoteViewsService {
    private ArrayList<String> mTitles;
    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        return new RemoteViewsFactory() {

            @Override
            public void onCreate() {
                if(intent.getExtras() != null) {
                    mTitles = intent.getExtras().getStringArrayList("titles");
                }
            }

            @Override
            public void onDataSetChanged() {
                if(intent.getExtras() != null) {
                    mTitles = intent.getExtras().getStringArrayList("titles");
                }
            }

            @Override
            public void onDestroy() {
            }

            @Override
            public int getCount() {
                if(mTitles != null)
                    return mTitles.size();
                return 0;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if(mTitles == null) {
                    return null;
                }
                String articleTitle = mTitles.get(position);

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_collection_item);

                views.setTextViewText(R.id.widgetArticleTitle, articleTitle);


                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_collection);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
