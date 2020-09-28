package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.Table;

import java.util.List;

public class WishListDTO {

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
        @SerializedName("Table")
        @Expose
        private List<Table> table;

//        @SerializedName("ContentInfoFilter")
//        @Expose
//        private List<ContentInfoFilter> contentInfoFilters;

        public List<Table> getTable() {
            return table;
        }

        public void setTable(List<Table> table) {
            this.table = table;
        }


//        public List<ContentInfoFilter> getContentInfoFilters() {
//            return contentInfoFilters;
//        }
//
//        public void setContentInfoFilters(List<ContentInfoFilter> contentInfoFilters) {
//            this.contentInfoFilters = contentInfoFilters;
//        }
    }
}
