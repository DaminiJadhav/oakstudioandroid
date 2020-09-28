package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentlyWatched {
    @SerializedName("ContentId")
    @Expose
    private Integer ContentId;
    @SerializedName("SeenUpTo")
    @Expose
    private Integer SeenUpTo;
    @SerializedName("ContentTitle")
    @Expose
    private String ContentTitle;
    @SerializedName("ContentDescription")
    @Expose
    private String  ContentDescription;
    @SerializedName("ContentUrl")
    @Expose
    private String ContentUrl;
    @SerializedName("Year")
    @Expose
    private Integer Year;
    @SerializedName("CategoryID")
    @Expose
    private Integer  CategoryID;
    @SerializedName("FeaturesDescription")
    @Expose
    private String  FeaturesDescription;
    @SerializedName("ViewCount")
    @Expose
    private Integer ViewCount;
    @SerializedName("Ratings")
    @Expose
    private Integer  Ratings;
    @SerializedName("ContentType")
    @Expose
    private String ContentType;
    @SerializedName("Square_Image")
    @Expose
    private String Square_Image;
    @SerializedName("Comments")
    @Expose
    private Integer  Comments;


    public Integer getContentId() {
        return ContentId;
    }

    public void setContentId(Integer contentId) {
        ContentId = contentId;
    }

    public Integer getSeenUpTo() {
        return SeenUpTo;
    }

    public void setSeenUpTo(Integer seenUpTo) {
        SeenUpTo = seenUpTo;
    }

    public String getContentTitle() {
        return ContentTitle;
    }

    public void setContentTitle(String contentTitle) {
        ContentTitle = contentTitle;
    }

    public String getContentDescription() {
        return ContentDescription;
    }

    public void setContentDescription(String contentDescription) {
        ContentDescription = contentDescription;
    }

    public String getContentUrl() {
        return ContentUrl;
    }

    public void setContentUrl(String contentUrl) {
        ContentUrl = contentUrl;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Integer getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Integer categoryID) {
        CategoryID = categoryID;
    }

    public String getFeaturesDescription() {
        return FeaturesDescription;
    }

    public void setFeaturesDescription(String featuresDescription) {
        FeaturesDescription = featuresDescription;
    }

    public Integer getViewCount() {
        return ViewCount;
    }

    public void setViewCount(Integer viewCount) {
        ViewCount = viewCount;
    }

    public Integer getRatings() {
        return Ratings;
    }

    public void setRatings(Integer ratings) {
        Ratings = ratings;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getSquare_Image() {
        return Square_Image;
    }

    public void setSquare_Image(String square_Image) {
        Square_Image = square_Image;
    }

    public Integer getComments() {
        return Comments;
    }

    public void setComments(Integer comments) {
        Comments = comments;
    }
}
