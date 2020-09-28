package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogDetailDTO {
    @SerializedName("BlogId")
    @Expose
    private Integer blogId;
    @SerializedName("SmallTitle")
    @Expose
    private String smallTitle;
    @SerializedName("LargeTitle")
    @Expose
    private String largeTitle;
    @SerializedName("SmallDescription")
    @Expose
    private String smallDescription;
    @SerializedName("LargeDescription")
    @Expose
    private String largeDescription;
    @SerializedName("DirectorName")
    @Expose
    private String directorName;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Description1Bullets")
    @Expose
    private String description1Bullets;
    @SerializedName("Description2")
    @Expose
    private String description2;
    @SerializedName("SquareImage")
    @Expose
    private Object squareImage;
    @SerializedName("BannerImage")
    @Expose
    private Object bannerImage;
    @SerializedName("Image1")
    @Expose
    private Object image1;
    @SerializedName("Image2")
    @Expose
    private Object image2;
    public Integer getBlogId() {
        return blogId;
    }
    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
    public String getSmallTitle() {
        return smallTitle;
    }
    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }
    public String getLargeTitle() {
        return largeTitle;
    }
    public void setLargeTitle(String largeTitle) {
        this.largeTitle = largeTitle;
    }
    public String getSmallDescription() {
        return smallDescription;
    }
    public void setSmallDescription(String smallDescription) {
        this.smallDescription = smallDescription;
    }
    public String getLargeDescription() {
        return largeDescription;
    }
    public void setLargeDescription(String largeDescription) {
        this.largeDescription = largeDescription;
    }
    public String getDirectorName() {
        return directorName;
    }
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDescription1Bullets() {
        return description1Bullets;
    }
    public void setDescription1Bullets(String description1Bullets) {
        this.description1Bullets = description1Bullets;
    }
    public String getDescription2() {
        return description2;
    }
    public void setDescription2(String description2) {
        this.description2 = description2;
    }
    public Object getSquareImage() {
        return squareImage;
    }
    public void setSquareImage(Object squareImage) {
        this.squareImage = squareImage;
    }
    public Object getBannerImage() {
        return bannerImage;
    }
    public void setBannerImage(Object bannerImage) {
        this.bannerImage = bannerImage;
    }
    public Object getImage1() {
        return image1;
    }
    public void setImage1(Object image1) {
        this.image1 = image1;
    }
    public Object getImage2() {
        return image2;
    }
    public void setImage2(Object image2) {
        this.image2 = image2;
    }
}