package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewsList {
    @SerializedName("ContentReviewID")
    @Expose
    private Integer ContentReviewID;
    @SerializedName("ContentID")
    @Expose
    private Integer ContentID;
    @SerializedName("CustID")
    @Expose
    private Integer CustID;
    @SerializedName("StartRating")
    @Expose
    private Integer StartRating;
    @SerializedName("Review")
    @Expose
    private String Review;
    @SerializedName("CreateDate")
    @Expose
    private String CreateDate;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;

    public Integer getContentReviewID() {
        return ContentReviewID;
    }

    public void setContentReviewID(Integer contentReviewID) {
        ContentReviewID = contentReviewID;
    }

    public Integer getContentID() {
        return ContentID;
    }

    public void setContentID(Integer contentID) {
        ContentID = contentID;
    }

    public Integer getCustID() {
        return CustID;
    }

    public void setCustID(Integer custID) {
        CustID = custID;
    }

    public Integer getStartRating() {
        return StartRating;
    }

    public void setStartRating(Integer startRating) {
        StartRating = startRating;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
