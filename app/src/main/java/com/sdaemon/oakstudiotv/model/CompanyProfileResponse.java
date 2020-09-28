package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyProfileResponse {

    @SerializedName("CompanyId")
    @Expose
    Integer CompanyId;
    @SerializedName("CompanyName")
    @Expose
    String  CompanyName;
    @SerializedName("logo")
    @Expose
    String logo;
    @SerializedName("Address")
    @Expose
    String Address;
    @SerializedName("CountryId")
    @Expose
    Integer CountryId;
    @SerializedName("StateId")
    @Expose
    Integer StateId;
    @SerializedName("ZipCode")
    @Expose
    String  ZipCode;
    @SerializedName("Email")
    @Expose
    String Email;
    @SerializedName("Name")
    @Expose
    String Name;
    @SerializedName("SName")
    @Expose
    String SName;
    @SerializedName("SmtpPort")
    @Expose
    String SmtpPort;
    @SerializedName("SmtpHost")
    @Expose
    String SmtpHost;
    @SerializedName("LastModDate")
    @Expose
    String LastModDate;
    @SerializedName("ModifiedBy")
    @Expose
    Integer ModifiedBy;
    @SerializedName("ADTenant")
    @Expose
    String ADTenant;
    @SerializedName("ADClientId")
    @Expose
    String ADClientId;
    @SerializedName("ADClientSecret")
    @Expose
    String ADClientSecret;
    @SerializedName("ADRedirectUri")
    @Expose
    String ADRedirectUri;
    @SerializedName("ADAadInstance")
    @Expose
    String ADAadInstance;
    @SerializedName("ADSignUpSignInPolicyId")
    @Expose
    String ADSignUpSignInPolicyId;
    @SerializedName("ADEditProfilePolicyId")
    @Expose
    String ADEditProfilePolicyId;
    @SerializedName("ADResetPasswordPolicyId")
    @Expose
    String ADResetPasswordPolicyId;
    @SerializedName("ADTaskServiceUrl")
    @Expose
    String ADTaskServiceUrl;
    @SerializedName("ADApiIdentifier")
    @Expose
    String ADApiIdentifier;
    @SerializedName("ADReadScope")
    @Expose
    String ADReadScope;
    @SerializedName("ADWriteScope")
    @Expose
    String ADWriteScope;
    @SerializedName("FreePlanDays")
    @Expose
    String FreePlanDays;
    @SerializedName("FreePlanStatus")
    @Expose
    String FreePlanStatus;

    public Integer getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Integer companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getCountryId() {
        return CountryId;
    }

    public void setCountryId(Integer countryId) {
        CountryId = countryId;
    }

    public Integer getStateId() {
        return StateId;
    }

    public void setStateId(Integer stateId) {
        StateId = stateId;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getSmtpPort() {
        return SmtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        SmtpPort = smtpPort;
    }

    public String getSmtpHost() {
        return SmtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        SmtpHost = smtpHost;
    }

    public String getLastModDate() {
        return LastModDate;
    }

    public void setLastModDate(String lastModDate) {
        LastModDate = lastModDate;
    }

    public Integer getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getADTenant() {
        return ADTenant;
    }

    public void setADTenant(String ADTenant) {
        this.ADTenant = ADTenant;
    }

    public String getADClientId() {
        return ADClientId;
    }

    public void setADClientId(String ADClientId) {
        this.ADClientId = ADClientId;
    }

    public String getADClientSecret() {
        return ADClientSecret;
    }

    public void setADClientSecret(String ADClientSecret) {
        this.ADClientSecret = ADClientSecret;
    }

    public String getADRedirectUri() {
        return ADRedirectUri;
    }

    public void setADRedirectUri(String ADRedirectUri) {
        this.ADRedirectUri = ADRedirectUri;
    }

    public String getADAadInstance() {
        return ADAadInstance;
    }

    public void setADAadInstance(String ADAadInstance) {
        this.ADAadInstance = ADAadInstance;
    }

    public String getADSignUpSignInPolicyId() {
        return ADSignUpSignInPolicyId;
    }

    public void setADSignUpSignInPolicyId(String ADSignUpSignInPolicyId) {
        this.ADSignUpSignInPolicyId = ADSignUpSignInPolicyId;
    }

    public String getADEditProfilePolicyId() {
        return ADEditProfilePolicyId;
    }

    public void setADEditProfilePolicyId(String ADEditProfilePolicyId) {
        this.ADEditProfilePolicyId = ADEditProfilePolicyId;
    }

    public String getADResetPasswordPolicyId() {
        return ADResetPasswordPolicyId;
    }

    public void setADResetPasswordPolicyId(String ADResetPasswordPolicyId) {
        this.ADResetPasswordPolicyId = ADResetPasswordPolicyId;
    }

    public String getADTaskServiceUrl() {
        return ADTaskServiceUrl;
    }

    public void setADTaskServiceUrl(String ADTaskServiceUrl) {
        this.ADTaskServiceUrl = ADTaskServiceUrl;
    }

    public String getADApiIdentifier() {
        return ADApiIdentifier;
    }

    public void setADApiIdentifier(String ADApiIdentifier) {
        this.ADApiIdentifier = ADApiIdentifier;
    }

    public String getADReadScope() {
        return ADReadScope;
    }

    public void setADReadScope(String ADReadScope) {
        this.ADReadScope = ADReadScope;
    }

    public String getADWriteScope() {
        return ADWriteScope;
    }

    public void setADWriteScope(String ADWriteScope) {
        this.ADWriteScope = ADWriteScope;
    }

    public String getFreePlanDays() {
        return FreePlanDays;
    }

    public void setFreePlanDays(String freePlanDays) {
        FreePlanDays = freePlanDays;
    }

    public String getFreePlanStatus() {
        return FreePlanStatus;
    }

    public void setFreePlanStatus(String freePlanStatus) {
        FreePlanStatus = freePlanStatus;
    }
}

