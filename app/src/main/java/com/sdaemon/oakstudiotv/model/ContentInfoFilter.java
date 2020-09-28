package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentInfoFilter {
    @SerializedName("RowNo")
    @Expose
    private Integer RowNo;
    @SerializedName("ContentID")
    @Expose
    private Integer contentID;
    @SerializedName("SeasonId")
    @Expose
    private Integer SeasonId;
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
    @SerializedName("CountryName")
    @Expose
    private String CountryName;
    @SerializedName("ContentGenre")
    @Expose
    private String ContentGenre;
    @SerializedName("ViewCount")
    @Expose
    private String ViewCount;
    @SerializedName("Ratings")
    @Expose
    private Integer Ratings;
    @SerializedName("SqImage")
    @Expose
    private String SqImage;
    @SerializedName("BannerImage")
    @Expose
    private String BannerImage;
    @SerializedName("TrailerImage")
    @Expose
    private String TrailerImage;
    @SerializedName("MovieSeason")
    @Expose
    private String MovieSeason;


    public Integer getRowNo() {
        return RowNo;
    }

    public void setRowNo(Integer rowNo) {
        RowNo = rowNo;
    }

    public Integer getContentID() {
        return contentID;
    }

    public void setContentID(Integer contentID) {
        this.contentID = contentID;
    }

    public Integer getSeasonId() {
        return SeasonId;
    }

    public void setSeasonId(Integer seasonId) {
        SeasonId = seasonId;
    }

    public String getContentTitle()  {
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

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getContentGenre() {
        return ContentGenre;
    }

    public void setContentGenre(String contentGenre) {
        ContentGenre = contentGenre;
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

    public String getSqImage() {
        return SqImage;
    }

    public void setSqImage(String sqImage) {
        SqImage = sqImage;
    }

    public String getBannerImage() {
        return BannerImage;
    }

    public void setBannerImage(String bannerImage) {
        BannerImage = bannerImage;
    }

    public String getTrailerImage() {
        return TrailerImage;
    }

    public void setTrailerImage(String trailerImage) {
        TrailerImage = trailerImage;
    }

    public String getMovieSeason() {
        return MovieSeason;
    }

    public void setMovieSeason(String movieSeason) {
        MovieSeason = movieSeason;
    }
}
