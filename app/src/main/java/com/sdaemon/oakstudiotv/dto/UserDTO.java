package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("result")
    @Expose
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("UserFirstName")
        @Expose
        private String UserFirstName;
        @SerializedName("UserLastName")
        @Expose
        private String UserLastName;
        @SerializedName("UserProfileID")
        @Expose
        private Integer UserProfileID;
        @SerializedName("UserName")
        @Expose
        private String UserName;
        @SerializedName("UserEmail")
        @Expose
        private String UserEmail;
        @SerializedName("CustID")
        @Expose
        private Integer CustID;
        @SerializedName("CustFirstName")
        @Expose
        private String CustFirstName;
        @SerializedName("CustLastName")
        @Expose
        private String CustLastName;
        @SerializedName("PlanID")
        @Expose
        private Integer PlanID;
        @SerializedName("CreateDate")
        @Expose
        private String CreateDate;
        @SerializedName("SubscriptionID")
        @Expose
        private Integer SubscriptionID;
        @SerializedName("CurrencyID")
        @Expose
        private Integer CurrencyID;
        @SerializedName("CurrencyCode")
        @Expose
        private String CurrencyCode;
        @SerializedName("CountryId")
        @Expose
        private Integer CountryId;
        @SerializedName("CountryName")
        @Expose
        private String CountryName;


        public String getUserFirstName() {
            return UserFirstName;
        }

        public void setUserFirstName(String userFirstName) {
            UserFirstName = userFirstName;
        }

        public String getUserLastName() {
            return UserLastName;
        }

        public void setUserLastName(String userLastName) {
            UserLastName = userLastName;
        }

        public Integer getUserProfileID() {
            return UserProfileID;
        }

        public void setUserProfileID(Integer userProfileID) {
            UserProfileID = userProfileID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getUserEmail() {
            return UserEmail;
        }

        public void setUserEmail(String userEmail) {
            UserEmail = userEmail;
        }

        public Integer getCustID() {
            return CustID;
        }

        public void setCustID(Integer custID) {
            CustID = custID;
        }

        public String getCustFirstName() {
            return CustFirstName;
        }

        public void setCustFirstName(String custFirstName) {
            CustFirstName = custFirstName;
        }

        public String getCustLastName() {
            return CustLastName;
        }

        public void setCustLastName(String custLastName) {
            CustLastName = custLastName;
        }

        public Integer getPlanID() {
            return PlanID;
        }

        public void setPlanID(Integer planID) {
            PlanID = planID;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public Integer getSubscriptionID() {
            return SubscriptionID;
        }

        public void setSubscriptionID(Integer subscriptionID) {
            SubscriptionID = subscriptionID;
        }

        public Integer getCurrencyID() {
            return CurrencyID;
        }

        public void setCurrencyID(Integer currencyID) {
            CurrencyID = currencyID;
        }

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public Integer getCountryId() {
            return CountryId;
        }

        public void setCountryId(Integer countryId) {
            CountryId = countryId;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }
    }


}
