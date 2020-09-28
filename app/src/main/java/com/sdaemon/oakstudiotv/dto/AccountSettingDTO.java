package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountSettingDTO {

    @SerializedName("UniqueIdentifire")
    @Expose
    private String UniqueIdentifire;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("CountryId")
    @Expose
    private Integer CountryId;
    @SerializedName("StateId")
    @Expose
    private Integer StateId;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("PostalCode")
    @Expose
    private Integer PostalCode;

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Integer getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(Integer postalCode) {
        PostalCode = postalCode;
    }
}
