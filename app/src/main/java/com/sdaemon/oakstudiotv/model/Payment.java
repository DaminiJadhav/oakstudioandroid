package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("SubscriptionID")
    @Expose
    private Integer SubscriptionID;

    @SerializedName("CustID")
    @Expose
    private Integer CustID;


    @SerializedName("PlanID")
    @Expose
    private Integer PlanID;


    @SerializedName("StatusID")
    @Expose
    private Integer StatusID;

    @SerializedName("CreateDate")
    @Expose
    private String CreateDate;


    @SerializedName("CreatedBy")
    @Expose
    private Integer CreatedBy;

    @SerializedName("LastModifiedDate")
    @Expose
    private String LastModifiedDate;


    @SerializedName("LastModifiedBy")
    @Expose
    private Integer LastModifiedBy;


    public Integer getSubscriptionID() {
        return SubscriptionID;
    }

    public void setSubscriptionID(Integer subscriptionID) {
        SubscriptionID = subscriptionID;
    }

    public Integer getCustID() {
        return CustID;
    }

    public void setCustID(Integer custID) {
        CustID = custID;
    }

    public Integer getPlanID() {
        return PlanID;
    }

    public void setPlanID(Integer planID) {
        PlanID = planID;
    }

    public Integer getStatusID() {
        return StatusID;
    }

    public void setStatusID(Integer statusID) {
        StatusID = statusID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public Integer getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    public String getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
    }

    public Integer getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        LastModifiedBy = lastModifiedBy;
    }
}
