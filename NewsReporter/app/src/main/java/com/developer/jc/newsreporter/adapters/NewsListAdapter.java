package com.developer.jc.newsreporter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.jc.newsreporter.Media;
import com.developer.jc.newsreporter.R;
import com.developer.jc.newsreporter.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for news list
 */
public class NewsListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Result> mResultList;
    private LayoutInflater inflater;

    public NewsListAdapter(Context context, List<Result> list) {
        mContext = context;
        mResultList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);
        }
        NewsListViewHolder newsListViewHolder = new NewsListViewHolder();
        newsListViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.newsImage);
        newsListViewHolder.mTextView = (TextView) convertView.findViewById(R.id.newsTitle);
        if(mResultList.get(position) != null) {
            String title = mResultList.get(position).getTitle();
            newsListViewHolder.mTextView.setText(title);
            Media[] media = mResultList.get(position).getMedia();
            if(media.length > 0 && media[0].getMediaMetadata().length > 0) {
                String imageUrl = media[0].getMediaMetadata()[0].getUrl();
                Picasso.with(mContext).load(imageUrl)
                        .into(newsListViewHolder.mImageView);
            }
        }
        return convertView;
    }
}
