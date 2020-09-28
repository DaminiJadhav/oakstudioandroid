package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastAndCrewList {

    @SerializedName("CastAndCrewID")
    @Expose
    private Integer CastAndCrewID;
    @SerializedName("CastAndCrewName")
    @Expose
    private String CastAndCrewName;
    @SerializedName("DateOfBirth")
    @Expose
    private String DateOfBirth;
    @SerializedName("CastandCrewRole")
    @Expose
    private String CastandCrewRole;
    @SerializedName("CastAndCrewType")
    @Expose
    private String CastAndCrewType;
    @SerializedName("profilephoto")
    @Expose
    private String profilephoto;
    @SerializedName("CastAndCrewInfo")
    @Expose
    private String CastAndCrewInfo;

    public Integer getCastAndCrewID() {
        return CastAndCrewID;
    }

    public void setCastAndCrewID(Integer castAndCrewID) {
        CastAndCrewID = castAndCrewID;
    }

    public String getCastAndCrewName() {
        return CastAndCrewName;
    }

    public void setCastAndCrewName(String castAndCrewName) {
        CastAndCrewName = castAndCrewName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getCastandCrewRole() {
        return CastandCrewRole;
    }

    public void setCastandCrewRole(String castandCrewRole) {
        CastandCrewRole = castandCrewRole;
    }

    public String getCastAndCrewType() {
        return CastAndCrewType;
    }

    public void setCastAndCrewType(String castAndCrewType) {
        CastAndCrewType = castAndCrewType;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getCastAndCrewInfo() {
        return CastAndCrewInfo;
    }

    public void setCastAndCrewInfo(String castAndCrewInfo) {
        CastAndCrewInfo = castAndCrewInfo;
    }
}
