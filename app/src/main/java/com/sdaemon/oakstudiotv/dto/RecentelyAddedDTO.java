package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.RecentlyAdded;

import java.util.List;

public class RecentelyAddedDTO {

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
       @SerializedName("RecentlyAdded")
       @Expose
       private List<RecentlyAdded> recentlyAdded;

        public List<RecentlyAdded> getRecentlyAdded() {
            return recentlyAdded;
        }

        public void setRecentlyAdded(List<RecentlyAdded> recentlyAdded) {
            this.recentlyAdded = recentlyAdded;
        }
    }

}
