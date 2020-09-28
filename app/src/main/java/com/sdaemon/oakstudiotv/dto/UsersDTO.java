package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UsersDTO implements Serializable{

    @SerializedName("result")
    @Expose
    private Result result=null;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private Integer status;


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }




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




    public class Result{
        @SerializedName("ActiveStatus")
        @Expose
        private boolean ActiveStatus;

        @SerializedName("CustomerId")
        @Expose
        private Integer CustomerId;

        @SerializedName("CustEmail")
        @Expose
        private String CustEmail;

        @SerializedName("UniqueIdentifire")
        @Expose
        private String UniqueIdentifire;

        @SerializedName("FirstName")
        @Expose
        private String FirstName;

        @SerializedName("LastName")
        @Expose
        private String LastName;

        @SerializedName("SubscriptionID")
        @Expose
        private Integer SubscriptionID;

        @SerializedName("PlanID")
        @Expose
        private Integer PlanID;

        @SerializedName("CurrencyCode")
        @Expose
        private String  CurrencyCode;

        @SerializedName("CountryID")
        @Expose
        private Integer CountryID;

        @SerializedName("CountryName")
        @Expose
        private String CountryName;

        public boolean isActiveStatus() {
            return ActiveStatus;
        }

        public void setActiveStatus(boolean activeStatus) {
            ActiveStatus = activeStatus;
        }

        public Integer getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(Integer customerId) {
            CustomerId = customerId;
        }

        public String getCustEmail() {
            return CustEmail;
        }

        public void setCustEmail(String custEmail) {
            CustEmail = custEmail;
        }

        public String getUniqueIdentifire() {
            return UniqueIdentifire;
        }

        public void setUniqueIdentifire(String uniqueIdentifire) {
            UniqueIdentifire = uniqueIdentifire;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public Integer getSubscriptionID() {
            return SubscriptionID;
        }

        public void setSubscriptionID(Integer subscriptionID) {
            SubscriptionID = subscriptionID;
        }

        public Integer getPlanID() {
            return PlanID;
        }

        public void setPlanID(Integer planID) {
            PlanID = planID;
        }

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public Integer getCountryID() {
            return CountryID;
        }

        public void setCountryID(Integer countryID) {
            CountryID = countryID;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }
    }


}
