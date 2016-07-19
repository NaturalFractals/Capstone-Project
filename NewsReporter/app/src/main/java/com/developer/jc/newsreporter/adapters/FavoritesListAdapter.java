package com.developer.jc.newsreporter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.jc.newsreporter.Article;
import com.developer.jc.newsreporter.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Loads the list of Favorites News Articles from the local SQLite Database
 * @author Jesse Cochran
 */
public class FavoritesListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Article> mArticleList;
    private LayoutInflater inflater;

    public FavoritesListAdapter(Context context, List<Article> list) {
        mContext = context;
        mArticleList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);
        }
        NewsListViewHolder newsListViewHolder = new NewsListViewHolder();
        newsListViewHolder.mImageView = (ImageView) convertView.findViewById((R.id.newsImage));
        newsListViewHolder.mTextView = (TextView) convertView.findViewById(R.id.newsTitle);
        if(mArticleList.get(position) != null) {
            newsListViewHolder.mTextView.setText(mArticleList.get(position).getTitle());
            Picasso.with(mContext).load(mArticleList.get(position).getImageUrl())
                    .into(newsListViewHolder.mImageView);
        }
        return convertView;
    }

    public void remove(int position) {
        if(mArticleList != null) {
            mArticleList.remove(position);
        }
    }
}
