package com.sdaemon.oakstudiotv.model;

public class VideoDTO {
    String videoUrl = "",date ="";
    Long date_time;

    String title;
    String type;
    int year;
    String img;
    String rating;
    String viewcount;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    String token="";

    public VideoDTO(){
    }
    public   VideoDTO(String videoUrl){
        this.videoUrl = videoUrl;
    }

//    public VideoDTO(String videoUrl,String date,Long date_time,String token){
//        this.token=token;
//        this.date_time=date_time;
//        this.videoUrl = videoUrl;
//        this.date = date;
//    }

    public Long getDate_time() {
        return date_time;
    }
    public void setDate_time(Long date_time) {
        this.date_time = date_time;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }



    public VideoDTO(String videoUrl,String date,Long date_time,String token,String ContentTilte,String contenttype,int year,String img,String count,String rating){
        this.token=token;
        this.date_time=date_time;
        this.videoUrl = videoUrl;
        this.date = date;

        this.title=ContentTilte;
        this.type=contenttype;
        this.year = year;
        this.img = img;
        this.viewcount=count;
        this.rating=rating;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getViewcount() {
        return viewcount;
    }

    public void setViewcount(String viewcount) {
        this.viewcount = viewcount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
