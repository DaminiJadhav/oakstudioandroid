package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class DownloadedMovieDetails implements Serializable {
    int image;
    String  movieName;
    String  movieYear;
    int  movieRating;
    int  movieViews;
    String  uploadedDate;
    String  remaningDays;



    public DownloadedMovieDetails(int image, String movieName, String movieYear, int movieRating, int movieViews,String uploadedDate,String remaningDays) {
        this.image = image;
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieRating = movieRating;
        this.movieViews = movieViews;
        this.uploadedDate = uploadedDate;
        this.remaningDays = remaningDays;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getRemaningDays() {
        return remaningDays;
    }

    public void setRemaningDays(String remaningDays) {
        this.remaningDays = remaningDays;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public int getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(int movieRating) {
        this.movieRating = movieRating;
    }

    public int getMovieViews() {
        return movieViews;
    }

    public void setMovieViews(int movieViews) {
        this.movieViews = movieViews;
    }
}
