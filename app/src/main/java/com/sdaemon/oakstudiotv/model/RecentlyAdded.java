package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentlyAdded {
    @SerializedName("ContentID")
    @Expose
    private Integer ContentID;
    @SerializedName("SeasonID")
    @Expose
    private Integer SeasonID;
    @SerializedName("ContentTitle")
    @Expose
    private String  ContentTitle;
    @SerializedName("Year")
    @Expose
    private Integer Year;
    @SerializedName("ContentDescription")
    @Expose
    private String ContentDescription;
    @SerializedName("CategoryID")
    @Expose
    private Integer  CategoryID;
    @SerializedName("FeaturesDescription")
    @Expose
    private String  FeaturesDescription;
    @SerializedName("ViewCount")
    @Expose
    private Integer ViewCount;
    @SerializedName("ContentURL")
    @Expose
    private String  ContentURL;
    @SerializedName("Ratings")
    @Expose
    private Integer Ratings;
    @SerializedName("Square_Image")
    @Expose
    private String Square_Image;
    @SerializedName("ContentType")
    @Expose
    private String ContentType;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("Comments")
    @Expose
    private Integer  Comments;
    @SerializedName("MovieSeason")
    @Expose
    private String MovieSeason;

    public Integer getContentID() {
        return ContentID;
    }

    public void setContentID(Integer contentID) {
        ContentID = contentID;
    }

    public Integer getSeasonID() {
        return SeasonID;
    }

    public void setSeasonID(Integer seasonID) {
        SeasonID = seasonID;
    }

    public String getContentTitle() {
        return ContentTitle;
    }

    public void setContentTitle(String contentTitle) {
        ContentTitle = contentTitle;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public String getContentDescription() {
        return ContentDescription;
    }

    public void setContentDescription(String contentDescription) {
        ContentDescription = contentDescription;
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

    public String getContentURL() {
        return ContentURL;
    }

    public void setContentURL(String contentURL) {
        ContentURL = contentURL;
    }

    public Integer getRatings() {
        return Ratings;
    }

    public void setRatings(Integer ratings) {
        Ratings = ratings;
    }

    public String getSquare_Image() {
        return Square_Image;
    }

    public void setSquare_Image(String square_Image) {
        Square_Image = square_Image;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Integer getComments() {
        return Comments;
    }

    public void setComments(Integer comments) {
        Comments = comments;
    }

    public String getMovieSeason() {
        return MovieSeason;
    }

    public void setMovieSeason(String movieSeason) {
        MovieSeason = movieSeason;
    }
}
