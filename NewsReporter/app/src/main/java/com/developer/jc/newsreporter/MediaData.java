package com.developer.jc.newsreporter;


/**
 * This represents a MediaData object in the Media-metadata array. It contains the url of
 * the articles image along with other information that will not be used in this application.
 * @author Jesse Cochran
 */
public class MediaData{

    private String url;

    private String format;

    private Integer height;

    private Integer width;

    /**
     * Returns the image url
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the image url
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}
