package com.leonard.healthmanager.newsapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("url")
    @Expose
    private String mUrl;
    @SerializedName("urlToImage")
    @Expose
    private String mUrlToImage;
    @SerializedName("publishedAt")
    @Expose
    private String mPublishedTime;



    public static final int NEWS_IMAGE_TYPE = 0;
    public static final int NEWS_WITHOUT_IMAGE_TYPE = 1;

    public NewsModel(String mTitle, String mUrl, String mUrlToImage, String mPublishedTime) {
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mUrlToImage = mUrlToImage;
        this.mPublishedTime = mPublishedTime;
    }

    public int getType() {
        if (mUrlToImage.equals("null")) {
            return NEWS_WITHOUT_IMAGE_TYPE;
        } else {
            return NEWS_IMAGE_TYPE;
        }
    }

    public String getTitle() {
        return mTitle.replace("/<(.*?)\\>", "");
    }

    public String getUrl() {
        return mUrl;
    }

    public String getPublishedTime() {
        return mPublishedTime;
    }
    
    public String getUrlToImage() {

        //return mUrlToImage;
        if (mUrlToImage.equals("")) {
            return mUrlToImage = null;
        } else {
            return mUrlToImage;
        }
    }
}
