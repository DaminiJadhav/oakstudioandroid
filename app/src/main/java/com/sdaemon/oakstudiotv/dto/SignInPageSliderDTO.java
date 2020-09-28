package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SignInPageSliderDTO implements Serializable {

    @SerializedName("Table")
    @Expose
    private List<Table> Table;


    public List<SignInPageSliderDTO.Table> getTable() {
        return Table;
    }

    public void setTable(List<SignInPageSliderDTO.Table> table) {
        Table = table;
    }

    public class Table {
        @SerializedName("ContentID")
        @Expose
        private Integer ContentID;
        @SerializedName("ContentTitle")
        @Expose
        private String ContentTitle;
        @SerializedName("Year")
        @Expose
        private String Year;
        @SerializedName("ContentImageURL")
        @Expose
        private String ContentImageURL;
        @SerializedName("Trailer_Image")
        @Expose
        private String Trailer_Image;
        @SerializedName("MovieSeason")
        @Expose
        private String MovieSeason;


        public Integer getContentID() {
            return ContentID;
        }

        public void setContentID(Integer contentID) {
            ContentID = contentID;
        }

        public String getContentTitle() {
            return ContentTitle;
        }

        public void setContentTitle(String contentTitle) {
            ContentTitle = contentTitle;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String year) {
            Year = year;
        }

        public String getContentImageURL() {
            return ContentImageURL;
        }

        public void setContentImageURL(String contentImageURL) {
            ContentImageURL = contentImageURL;
        }

        public String getTrailer_Image() {
            return Trailer_Image;
        }

        public void setTrailer_Image(String trailer_Image) {
            Trailer_Image = trailer_Image;
        }

        public String getMovieSeason() {
            return MovieSeason;
        }

        public void setMovieSeason(String movieSeason) {
            MovieSeason = movieSeason;
        }
    }
}
