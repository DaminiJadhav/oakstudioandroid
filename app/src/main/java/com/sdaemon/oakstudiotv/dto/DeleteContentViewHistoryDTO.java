package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteContentViewHistoryDTO {
    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("History")
    @Expose
    private History History;
    @SerializedName("Message")
    @Expose
    private String  Message;


    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public DeleteContentViewHistoryDTO.History getHistory() {
        return History;
    }

    public void setHistory(DeleteContentViewHistoryDTO.History history) {
        History = history;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public class History{
        @SerializedName("ContentId")
        @Expose
        private Integer ContentId;
        @SerializedName("CreatedDate")
        @Expose
        private String  CreatedDate;
        @SerializedName("CustId")
        @Expose
        private Integer  CustId;
        @SerializedName("SeenUpTo")
        @Expose
        private Integer  SeenUpTo;

        public Integer getContentId() {
            return ContentId;
        }

        public void setContentId(Integer contentId) {
            ContentId = contentId;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            CreatedDate = createdDate;
        }

        public Integer getCustId() {
            return CustId;
        }

        public void setCustId(Integer custId) {
            CustId = custId;
        }

        public Integer getSeenUpTo() {
            return SeenUpTo;
        }

        public void setSeenUpTo(Integer seenUpTo) {
            SeenUpTo = seenUpTo;
        }
    }

}
