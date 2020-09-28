package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentViewHistoryDTO {
    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("History")
    @Expose
    private History episodes;
    @SerializedName("Message")
    @Expose
    private String Message;


    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public History getHistory() {
        return episodes;
    }

    public void setHistory(History episodes) {
        this.episodes = episodes;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public class History{

        @SerializedName("Id")
        @Expose
        private Integer Id;
        @SerializedName("CustId")
        @Expose
        private Integer CustId;
        @SerializedName("ContentId")
        @Expose
        private Integer ContentId;
        @SerializedName("CreatedDate")
        @Expose
        private String CreatedDate;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean IsDeleted;
        @SerializedName("ViewCount")
        @Expose
        private Integer ViewCount;
        @SerializedName("SeenUpTo")
        @Expose
        private String SeenUpTo;
        @SerializedName("Customer")
        @Expose
        private String Customer;
        @SerializedName("tblContent")
        @Expose
        private String tblContent;


        public Integer getId() {
            return Id;
        }

        public void setId(Integer id) {
            Id = id;
        }

        public Integer getCustId() {
            return CustId;
        }

        public void setCustId(Integer custId) {
            CustId = custId;
        }

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

        public Boolean getDeleted() {
            return IsDeleted;
        }

        public void setDeleted(Boolean deleted) {
            IsDeleted = deleted;
        }

        public Integer getViewCount() {
            return ViewCount;
        }

        public void setViewCount(Integer viewCount) {
            ViewCount = viewCount;
        }

        public String getSeenUpTo() {
            return SeenUpTo;
        }

        public void setSeenUpTo(String seenUpTo) {
            SeenUpTo = seenUpTo;
        }

        public String getCustomer() {
            return Customer;
        }

        public void setCustomer(String customer) {
            Customer = customer;
        }

        public String getTblContent() {
            return tblContent;
        }

        public void setTblContent(String tblContent) {
            this.tblContent = tblContent;
        }
    }
}
