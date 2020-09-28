package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.AvgRating;
import com.sdaemon.oakstudiotv.model.CastAndCrewList;
import com.sdaemon.oakstudiotv.model.CategoiesList;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.Favorite;
import com.sdaemon.oakstudiotv.model.ReviewsList;

import java.io.Serializable;
import java.util.List;

public class ContentDTO implements Serializable {

    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("ContentDetail")
    @Expose
    private ContentDetail ContentDetail;
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
        return ContentDetail;
    }

    public void setContentDetail(ContentDetail contentDetail) {
        ContentDetail = contentDetail;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeTypedObject(ContentDetail,0);
//
//    }


    public class ContentDetail implements Serializable{
//        @SerializedName("ContentInfoFilter")
//        @Expose
//        private List<ContentInfoFilter> contentInfoFilter;
        @SerializedName("ContentInfo")
        @Expose
        private List<ContentInfo> contentInfos;
        @SerializedName("CategoiesList")
        @Expose
        private List<CategoiesList> categoiesList;
        @SerializedName("CastAndCrew")
        @Expose
        private List<CastAndCrewList> CastAndCrew;
        @SerializedName("ReviewList")
        @Expose
        private List<ReviewsList> ReviewList;
        @SerializedName("Favorite")
        @Expose
        private List<Favorite> Favorite;
        @SerializedName("AvgRating")
        @Expose
        private List<AvgRating> AvgRating;

//        public List<ContentInfoFilter> getContentInfoFilter() {
//            return contentInfoFilter;
//        }
//
//        public void setContentInfoFilter(List<ContentInfoFilter> contentInfoFilter) {
//            this.contentInfoFilter = contentInfoFilter;
//        }

        public List<ContentInfo> getContentInfos() {
            return contentInfos;
        }

        public void setContentInfos(List<ContentInfo> contentInfos) {
            this.contentInfos = contentInfos;
        }

        public List<CategoiesList> getCategoiesList() {
            return categoiesList;
        }

        public void setCategoiesList(List<CategoiesList> categoiesList) {
            this.categoiesList = categoiesList;
        }

        public List<CastAndCrewList> getCastAndCrew() {
            return CastAndCrew;
        }

        public void setCastAndCrew(List<CastAndCrewList> castAndCrew) {
            CastAndCrew = castAndCrew;
        }

        //        public List<Object> getCastAndCrew() {
//            return CastAndCrew;
//        }
//
//        public void setCastAndCrew(List<Object> castAndCrew) {
//            CastAndCrew = castAndCrew;
//        }

        public List<ReviewsList> getReviewList() {
            return ReviewList;
        }

        public void setReviewList(List<ReviewsList> reviewList) {
            ReviewList = reviewList;
        }

        public List<com.sdaemon.oakstudiotv.model.Favorite> getFavorite() {
            return Favorite;
        }

        public void setFavorite(List<com.sdaemon.oakstudiotv.model.Favorite> favorite) {
            Favorite = favorite;
        }

        public List<AvgRating> getAvgRating() {
            return AvgRating;
        }

        public void setAvgRating(List<AvgRating> avgRating) {
            AvgRating = avgRating;
        }

//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel parcel, int i) {
//            parcel.writeTypedList(contentInfos);
//        }
    }

}
