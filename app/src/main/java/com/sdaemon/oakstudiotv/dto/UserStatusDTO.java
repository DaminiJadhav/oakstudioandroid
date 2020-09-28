package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserStatusDTO {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("result")
    @Expose
    private List<Result> resultList = null;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public class Result {

        @SerializedName("Status")
        @Expose
        private Integer status;

        @SerializedName("UserProfileID")
        @Expose
        private Integer userProfileID;

        @SerializedName("CustID")
        @Expose
        private Integer custID;

        @SerializedName("SubscriptionID")
        @Expose
        private Integer subscriptionID;

        @SerializedName("StatusID")
        @Expose
        private Integer statusID;

        @SerializedName("CorePlanId")
        @Expose
        private Integer corePlanId;

        @SerializedName("PeriodId")
        @Expose
        private Integer periodId;

        @SerializedName("PlanCost")
        @Expose
        private Double planCost;


        @SerializedName("CreateDate")
        @Expose
        private String createDate;


        @SerializedName("ActualEndDate")
        @Expose
        private String actualEndDate;


        @SerializedName("FreePlanDays")
        @Expose
        private String freePlanDays;


        @SerializedName("FreePlanStatus")
        @Expose
        private String freePlanStatus;


        @SerializedName("CorePlanName")
        @Expose
        private String corePlanName;


        @SerializedName("Name")
        @Expose
        private String name;


        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUserProfileID() {
            return userProfileID;
        }

        public void setUserProfileID(Integer userProfileID) {
            this.userProfileID = userProfileID;
        }

        public Integer getCustID() {
            return custID;
        }

        public void setCustID(Integer custID) {
            this.custID = custID;
        }

        public Integer getSubscriptionID() {
            return subscriptionID;
        }

        public void setSubscriptionID(Integer subscriptionID) {
            this.subscriptionID = subscriptionID;
        }

        public Integer getStatusID() {
            return statusID;
        }

        public void setStatusID(Integer statusID) {
            this.statusID = statusID;
        }

        public Integer getCorePlanId() {
            return corePlanId;
        }

        public void setCorePlanId(Integer corePlanId) {
            this.corePlanId = corePlanId;
        }

        public Integer getPeriodId() {
            return periodId;
        }

        public void setPeriodId(Integer periodId) {
            this.periodId = periodId;
        }

        public Double getPlanCost() {
            return planCost;
        }

        public void setPlanCost(Double planCost) {
            this.planCost = planCost;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getActualEndDate() {
            return actualEndDate;
        }

        public void setActualEndDate(String actualEndDate) {
            this.actualEndDate = actualEndDate;
        }

        public String getFreePlanDays() {
            return freePlanDays;
        }

        public void setFreePlanDays(String freePlanDays) {
            this.freePlanDays = freePlanDays;
        }

        public String getFreePlanStatus() {
            return freePlanStatus;
        }

        public void setFreePlanStatus(String freePlanStatus) {
            this.freePlanStatus = freePlanStatus;
        }

        public String getCorePlanName() {
            return corePlanName;
        }

        public void setCorePlanName(String corePlanName) {
            this.corePlanName = corePlanName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
