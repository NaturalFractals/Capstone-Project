package com.developer.jc.newsreporter;
;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Media object retrieved from the API. This contains the array of media-metadata,
 * which is an array that the image url can be retrieved from
 * @authro Jesse Cochran
 */
public class Media{

    private String type;

    private String subtype;

    private String caption;

    private String copyright;

    @SerializedName("media-metadata")
    private MediaData[] mediaMetadata;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The subtype
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * @param subtype The subtype
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    /**
     * @return The caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption The caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * @param copyright The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * @return The mediaMetadata
     */
    public MediaData[] getMediaMetadata() {
        return mediaMetadata;
    }

    /**
     * @param mediaMetadata The media-metadata
     */
    public void setMediaMetadata(MediaData[] mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }


}
