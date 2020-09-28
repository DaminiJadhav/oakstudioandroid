package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodeDTO {
    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("Episodes")
    @Expose
    private List<Episodes> episodes;

    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Episodes> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episodes> episodes) {
        this.episodes = episodes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Episodes{
        @SerializedName("ContentID")
        @Expose
        private Integer ContentID;
        @SerializedName("ContentTitle")
        @Expose
        private String ContentTitle;
        @SerializedName("ContentImageURL")
        @Expose
        private String ContentImageURL;

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

        public String getContentImageURL() {
            return ContentImageURL;
        }

        public void setContentImageURL(String contentImageURL) {
            ContentImageURL = contentImageURL;
        }
    }
}
