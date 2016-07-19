package com.developer.jc.newsreporter.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.jc.newsreporter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private ArrayList<String> articleText;
    private String articleTitleText;
    private String articleImageUrl;
    private String articleAuthorText;
    private String favoritesCheck;
    private String articleTextFull;

    private TextView articleView;
    private ImageView articleImage;
    private TextView articleTitle;
    private TextView articleAuthor;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        articleView = (TextView) view.findViewById(R.id.articleText);
        articleTitle = (TextView) view.findViewById(R.id.articleTitle);
        articleImage = (ImageView) view.findViewById(R.id.articleImage);
        articleAuthor = (TextView) view.findViewById(R.id.articleAuthor);
        setUpView();
        return view;
    }

    private void setUpView() {
        if(articleTitleText != null) {
            articleTitle.setText(articleTitleText);
            String text = "";
            if (favoritesCheck == null) {
                for (int i = 0; i < articleText.size(); i++) {
                    text += articleText.get(i) + "\n\n";
                }
            } else {
                text = articleTextFull;
            }
            articleView.setText(text);
            Picasso.with(getContext()).load(articleImageUrl).resize(1400, 1400)
                    .into(articleImage);
            articleAuthor.setText(articleAuthorText);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check to see if the app is in two pane mode
        if(getArguments() == null) {
            Intent intent = getActivity().getIntent();
            favoritesCheck = intent.getStringExtra("check");
            if (favoritesCheck == null) {
                articleText = intent.getStringArrayListExtra("article");
            } else {
                articleTextFull = intent.getStringExtra("article");
            }
            articleImageUrl = intent.getStringExtra("image");
            articleTitleText = intent.getStringExtra("title");
            articleAuthorText = intent.getStringExtra("author");
        } else {
            favoritesCheck = getArguments().getString("check");
            if(favoritesCheck == null) {
                articleText = getArguments().getStringArrayList("article");
            } else {
                articleTextFull = getArguments().getString("article");
            }
            articleImageUrl = getArguments().getString("image");
            articleTitleText = getArguments().getString("title");
            articleAuthorText = getArguments().getString("author");
        }
    }
}
