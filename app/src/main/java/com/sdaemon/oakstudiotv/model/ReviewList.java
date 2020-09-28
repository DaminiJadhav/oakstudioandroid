package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class ReviewList implements Serializable {

    private String userName;
    private String year;
    private String reviewDesc;
    private int userImage;



    public ReviewList() {
    }

    public ReviewList(int userImage) {
        this.userImage = userImage;
    }

    public ReviewList(int Image ,String userName, String year, String reviewDesc) {
        this.userName = userName;
        this.year = year;
        this.reviewDesc = reviewDesc;
        this.userImage = Image;
    }


    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }
}
