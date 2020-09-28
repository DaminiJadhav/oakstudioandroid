package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AboutUsResponse implements Serializable {

    @SerializedName("Id")
    @Expose
    Integer Id;
    @SerializedName("Full")
    @Expose
    String Full;
    @SerializedName("CreatedDate")
    @Expose
    String CreatedDate;
    @SerializedName("CreatedBy")
    @Expose
    Integer CreatedBy;
    @SerializedName("ModifiedBy")
    @Expose
    Integer ModifiedBy;
    @SerializedName("ModifiedDate")
    @Expose
    String ModifiedDate;
    @SerializedName("PrivacyPolicy")
    @Expose
    String PrivacyPolicy;
    @SerializedName("TermsOfUse")
    @Expose
    String TermsOfUse;
    @SerializedName("CookiePolicy")
    @Expose
    String CookiePolicy;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFull() {
        return Full;
    }

    public void setFull(String full) {
        Full = full;
    }

    public Object getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public Object getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    public Integer getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getPrivacyPolicy() {
        return PrivacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        PrivacyPolicy = privacyPolicy;
    }

    public String getTermsOfUse() {
        return TermsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        TermsOfUse = termsOfUse;
    }

    public String getCookiePolicy() {
        return CookiePolicy;
    }

    public void setCookiePolicy(String cookiePolicy) {
        CookiePolicy = cookiePolicy;
    }
}
