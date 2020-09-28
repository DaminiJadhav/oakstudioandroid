package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class Recetly_ViewedDetails implements Serializable {
    int image;
    String  movieName;
    String  movieYear;
    int  movieRating;
    int  movieViews;



    public Recetly_ViewedDetails(int image, String movieName, String movieYear, int movieRating, int movieViews) {
        this.image = image;
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieRating = movieRating;
        this.movieViews = movieViews;
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
