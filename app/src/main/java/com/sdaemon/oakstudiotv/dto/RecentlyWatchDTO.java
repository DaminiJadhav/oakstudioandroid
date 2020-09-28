package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.RecentlyWatched;

import java.util.List;

public class RecentlyWatchDTO {
    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("ContentDetail")
    @Expose
    private ContentDetail contentDetail;
    @SerializedName("Message")
    @Expose
    private String  Message;



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

    public class ContentDetail{
        @SerializedName("RecentlyWatched")
        @Expose
        private List<RecentlyWatched> recentlyWatcheds;

        public List<RecentlyWatched> getRecentlyWatcheds() {
            return recentlyWatcheds;
        }

        public void setRecentlyWatcheds(List<RecentlyWatched> recentlyWatcheds) {
            this.recentlyWatcheds = recentlyWatcheds;
        }
    }


}
