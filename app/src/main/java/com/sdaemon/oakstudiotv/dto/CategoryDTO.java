package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.SerializedName;

public class CategoryDTO {


    @SerializedName("albumId")
    private Integer albumId;
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
    @SerializedName("ID")
    private String ID;
    @SerializedName("Description")
    private String Description;
    @SerializedName("ContentID")
    private String ContentID;
    @SerializedName("ContentTitle")
    private String ContentTitle;
    @SerializedName("ContentDescription")
    private String ContentDescription;
    @SerializedName("ContentURL")
    private String ContentURL;
    @SerializedName("ReleaseDate")
    private String ReleaseDate;
    @SerializedName("FeaturesDescription")
    private String FeaturesDescription;
    @SerializedName("ParentContentNotes")
    private String ParentContentNotes;
    @SerializedName("LanguageID")
    private String LanguageID;
    @SerializedName("GenresID")
    private String GenresID;
    @SerializedName("Review")
    private String Review;
    @SerializedName("StartRating")
    private String StartRating;
    @SerializedName("ContentImageURL")
    private String ContentImageURL;
    @SerializedName("ViewCount")
    private String ViewCount;
    @SerializedName("ReleaseYear")
    private String ReleaseYear;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContentID() {
        return ContentID;
    }

    public void setContentID(String contentID) {
        ContentID = contentID;
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

    public String getContentURL() {
        return ContentURL;
    }

    public void setContentURL(String contentURL) {
        ContentURL = contentURL;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getFeaturesDescription() {
        return FeaturesDescription;
    }

    public void setFeaturesDescription(String featuresDescription) {
        FeaturesDescription = featuresDescription;
    }

    public String getParentContentNotes() {
        return ParentContentNotes;
    }

    public void setParentContentNotes(String parentContentNotes) {
        ParentContentNotes = parentContentNotes;
    }

    public String getLanguageID() {
        return LanguageID;
    }

    public void setLanguageID(String languageID) {
        LanguageID = languageID;
    }

    public String getGenresID() {
        return GenresID;
    }

    public void setGenresID(String genresID) {
        GenresID = genresID;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getStartRating() {
        return StartRating;
    }

    public void setStartRating(String startRating) {
        StartRating = startRating;
    }

    public String getContentImageURL() {
        return ContentImageURL;
    }

    public void setContentImageURL(String contentImageURL) {
        ContentImageURL = contentImageURL;
    }

    public String getViewCount() {
        return ViewCount;
    }

    public void setViewCount(String viewCount) {
        ViewCount = viewCount;
    }

    public String getReleaseYear() {
        return ReleaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        ReleaseYear = releaseYear;
    }

    public CategoryDTO(){

    }


    public CategoryDTO(Integer albumId, Integer id, String title, String url, String thumbnailUrl, String ID, String Description, String ContentID,String ContentTitle, String ContentDescription, String ReleaseYear, String ContentURL, String ReleaseDate, String FeaturesDescription, String ParentContentNotes, String LanguageID, String GenresID, String Review, String StartRating, String ContentImageURL, String ViewCount ) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.ID=ID;
        this.Description=Description;
        this.ContentID=ContentID;
        this.ContentTitle=ContentTitle;
        this.ContentDescription=ContentDescription;
        this.ContentURL=ContentURL;
        this.ReleaseDate=ReleaseDate;
        this.FeaturesDescription=FeaturesDescription;
        this.ParentContentNotes=ParentContentNotes;
        this.LanguageID=LanguageID;
        this.GenresID=GenresID;
        this.Review=Review;
        this.StartRating=StartRating;
        this.ContentImageURL=ContentImageURL;
        this.ViewCount=ViewCount;
        this.ReleaseYear=ReleaseYear;

    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


}