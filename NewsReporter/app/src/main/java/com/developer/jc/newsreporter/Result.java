package com.developer.jc.newsreporter;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Results in the array of results returned by the query.
 * @author Jesse Cochran
 */
public class Result{

    private String url;

    private String column;

    private String section;

    private String byline;

    private String title;

    private String _abstract;
    @SerializedName("published_date")
    private String publishedDate;

    private String source;
    @SerializedName("asset_id")
    private String assetId;

    private Media[] media;

    /**
     * Returns the url for the article on New York Times website
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the Url of the article on the New York Times website
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Returns the "byline" this contains the article author and date
     * @return
     * The byline
     */
    public String getByline() {
        return byline;
    }

    /**
     * Sets the "byline" containing the article author and date
     * @param byline
     * The byline
     */
    public void setByline(String byline) {
        this.byline = byline;
    }

    /**
     * Returns the title of the article
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the article
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    /**
     * Returns the published date of the article
     * @return
     * The publishedDate
     */
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     * Sets the published date of the article
     * @param publishedDate
     * The published_date
     */
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Media[] getMedia() {
        return media;
    }

    public void setMedia(Media[] media) {
        this.media = media;
    }

    public void setSource(String source) {
        this.source = source;
    }


}