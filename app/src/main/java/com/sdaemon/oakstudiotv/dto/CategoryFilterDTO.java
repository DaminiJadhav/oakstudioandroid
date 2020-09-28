package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.ContentInfoFilter;

import java.io.Serializable;
import java.util.List;

public class CategoryFilterDTO implements Serializable {

    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("ContentDetail")
    @Expose
    private ContentDetail contentDetail;
    @SerializedName("Message")
    @Expose
    private String  Message;

    public ContentDetail getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(ContentDetail contentDetail) {
        this.contentDetail = contentDetail;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public class ContentDetail{
        @SerializedName("ContentInfoFilter")
        @Expose
        private List<ContentInfoFilter> contentInfoFilters;

        public List<ContentInfoFilter> getContentInfoFilters() {
            return contentInfoFilters;
        }

        public void setContentInfoFilters(List<ContentInfoFilter> contentInfoFilters) {
            this.contentInfoFilters = contentInfoFilters;
        }
    }


}
