package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {
    @SerializedName("ContentID")
    @Expose
    private Integer contentID;
    @SerializedName("ContentTitle")
    @Expose
    private String contentTitle;
    @SerializedName("ContentDescription")
    @Expose
    private String contentDescription;
    @SerializedName("ContentURL")
    @Expose
    private String contentURL;
    @SerializedName("ReleaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("Year")
    @Expose
    private Integer year;
    @SerializedName("ContentType")
    @Expose
    private String contentType;
    @SerializedName("ContentTitle1")
    @Expose
    private String ContentTitle1;
    @SerializedName("ProviderFirstName")
    @Expose
    private String ProviderFirstName;
    @SerializedName("ProviderLastName")
    @Expose
    private String ProviderLastName;
    @SerializedName("Laguageame")
    @Expose
    private String Laguageame;
    @SerializedName("StudioDescription")
    @Expose
    private String StudioDescription;
    @SerializedName("FeaturesDescription")
    @Expose
    private String FeaturesDescription;
    @SerializedName("RatingDescription")
    @Expose
    private String RatingDescription;
    @SerializedName("CategoryDescription")
    @Expose
    private String CategoryDescription;
    @SerializedName("ContentBanerImg")
    @Expose
    private String ContentBanerImg;
    @SerializedName("SmallDescription")
    @Expose
    private String SmallDescription;
    @SerializedName("MovieLength")
    @Expose
    private String MovieLength;
    @SerializedName("cc")
    @Expose
    private String cc;
    @SerializedName("CreaterName")
    @Expose
    private String CreaterName;
    @SerializedName("Column1")
    @Expose
    private Integer Column1;
    @SerializedName("SqImage")
    @Expose
    private String SqImage;
    @SerializedName("TrailerImage")
    @Expose
    private String TrailerImage;
    @SerializedName("ViewCount")
    @Expose
    private String ViewCount;
    @SerializedName("Ratings")
    @Expose
    private Integer Ratings;



    public Integer getContentID() {
        return contentID;
    }

    public void setContentID(Integer contentID) {
        this.contentID = contentID;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTitle1() {
        return ContentTitle1;
    }

    public void setContentTitle1(String contentTitle1) {
        ContentTitle1 = contentTitle1;
    }

    public String getProviderFirstName() {
        return ProviderFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        ProviderFirstName = providerFirstName;
    }

    public String getProviderLastName() {
        return ProviderLastName;
    }

    public void setProviderLastName(String providerLastName) {
        ProviderLastName = providerLastName;
    }

    public String getLaguageame() {
        return Laguageame;
    }

    public void setLaguageame(String laguageame) {
        Laguageame = laguageame;
    }

    public String getStudioDescription() {
        return StudioDescription;
    }

    public void setStudioDescription(String studioDescription) {
        StudioDescription = studioDescription;
    }

    public String getFeaturesDescription() {
        return FeaturesDescription;
    }

    public void setFeaturesDescription(String featuresDescription) {
        FeaturesDescription = featuresDescription;
    }

    public String getRatingDescription() {
        return RatingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        RatingDescription = ratingDescription;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    public String getContentBanerImg() {
        return ContentBanerImg;
    }

    public void setContentBanerImg(String contentBanerImg) {
        ContentBanerImg = contentBanerImg;
    }

    public String getSmallDescription() {
        return SmallDescription;
    }

    public void setSmallDescription(String smallDescription) {
        SmallDescription = smallDescription;
    }

    public String getMovieLength() {
        return MovieLength;
    }

    public void setMovieLength(String movieLength) {
        MovieLength = movieLength;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCreaterName() {
        return CreaterName;
    }

    public void setCreaterName(String createrName) {
        CreaterName = createrName;
    }

    public Integer getColumn1() {
        return Column1;
    }

    public void setColumn1(Integer column1) {
        Column1 = column1;
    }

    public String getSqImage() {
        return SqImage;
    }

    public void setSqImage(String sqImage) {
        SqImage = sqImage;
    }

    public String getTrailerImage() {
        return TrailerImage;
    }

    public void setTrailerImage(String trailerImage) {
        TrailerImage = trailerImage;
    }

    public String getViewCount() {
        return ViewCount;
    }

    public void setViewCount(String viewCount) {
        ViewCount = viewCount;
    }

    public Integer getRatings() {
        return Ratings;
    }

    public void setRatings(Integer ratings) {
        Ratings = ratings;
    }
}