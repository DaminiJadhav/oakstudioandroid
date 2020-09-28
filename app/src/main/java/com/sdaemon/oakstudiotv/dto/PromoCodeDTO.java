package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PromoCodeDTO {

    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("Message")
    @Expose
    private String Message;


    @SerializedName("CoupanDetail")
    @Expose
    private List<CoupanDetail> coupanDetailList = null;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<CoupanDetail> getCoupanDetailList() {
        return coupanDetailList;
    }

    public void setCoupanDetailList(List<CoupanDetail> coupanDetailList) {
        this.coupanDetailList = coupanDetailList;
    }

    public class CoupanDetail {

        @SerializedName("CoupanCode")
        @Expose
        private String coupanCode;

        @SerializedName("ActiveStatus")
        @Expose
        private Boolean activeStatus;


        @SerializedName("DiscountType")
        @Expose
        private String discountType;

        @SerializedName("DiscountRate")
        @Expose
        private Double discountRate;

        @SerializedName("DiscountAmount")
        @Expose
        private Double discountAmount;

        public String getCoupanCode() {
            return coupanCode;
        }

        public void setCoupanCode(String coupanCode) {
            this.coupanCode = coupanCode;
        }

        public Boolean getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Boolean activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public Double getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(Double discountRate) {
            this.discountRate = discountRate;
        }

        public Double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Double discountAmount) {
            this.discountAmount = discountAmount;
        }
    }


}
