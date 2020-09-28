package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.ContentInfo;

import java.io.Serializable;
import java.util.List;

public class SearchDTO implements Serializable {


    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("ContentDetail")
    @Expose
    private ContentDetail contentDetail;
    @SerializedName("Message")
    @Expose
    private String Message;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public ContentDetail getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(ContentDetail contentDetail) {
        this.contentDetail = contentDetail;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }



}
class ContentDetail implements Serializable{
    @SerializedName("ContentInfo")
    @Expose
    private List<ContentInfo> contentInfo;


    public List<ContentInfo> getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(List<ContentInfo> contentInfo) {
        this.contentInfo = contentInfo;
    }
}
//
//    class ContentInfo{
//        @SerializedName("ContentID")
//        @Expose
//        private Integer ContentID;
//        @SerializedName("ContentTitle")
//        @Expose
//        private ContentDetail ContentTitle;
//        @SerializedName("ContentDescription")
//        @Expose
//        private String ContentDescription;
//
//        @SerializedName("ContentURL")
//        @Expose
//        private Integer ContentURL;
//        @SerializedName("ReleaseDate")
//        @Expose
//        private String ReleaseDate ;
//        @SerializedName("Year")
//        @Expose
//        private String Year;
//        @SerializedName("ContentType")
//        @Expose
//        private String ContentType;
//        @SerializedName("ContentTitle1")
//        @Expose
//        private String ContentTitle1;
//        @SerializedName("ProviderFirstName")
//        @Expose
//        private String ProviderFirstName;
//        @SerializedName("ProviderLastName")
//        @Expose
//        private String ProviderLastName;
//        @SerializedName("Laguageame")
//        @Expose
//        private String Laguageame;
//        @SerializedName("StudioDescription")
//        @Expose
//        private String Laguageame;
//        @SerializedName("FeaturesDescription")
//        @Expose
//        private String FeaturesDescription;
//        @SerializedName("RatingDescription")
//        @Expose
//        private String RatingDescription;
//
//        @SerializedName("CategoryDescription")
//        @Expose
//        private String CategoryDescription;
//        @SerializedName("ContentBanerImg")
//        @Expose
//        private String ContentBanerImg;
//        @SerializedName("SmallDescription")
//        @Expose
//        private String SmallDescription;
//        @SerializedName("MovieLength")
//        @Expose
//        private String MovieLength;
//        @SerializedName("cc")
//        @Expose
//        private String cc;
//
//        @SerializedName("CreaterName")
//        @Expose
//        private String CreaterName;
//        @SerializedName("CountryName")
//        @Expose
//        private String CountryName;
//        @SerializedName("ContentGenre")
//        @Expose
//        private String ContentGenre;
//        @SerializedName("ViewCount")
//        @Expose
//        private String ViewCount;
//
//    }
