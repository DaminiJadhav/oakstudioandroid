package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.SerializedName;

public class PlanMasterDTO {

//    @SerializedName("albumId")
//    private Integer albumId;

    @SerializedName("CorePlanName")
    private String CorePlanName;

    @SerializedName("SubDescription")
    private String SubDescription;


    @SerializedName("AllowStatus1")
    private Boolean AllowStatus1;


    @SerializedName("Monthly")
    private Double Monthly;

    @SerializedName("Quatarly")
    private Double Quatarly;

    @SerializedName("Yearly")
    private Double Yearly;


    public String getCorePlanName() {
        return CorePlanName;
    }

    public void setCorePlanName(String corePlanName) {
        CorePlanName = corePlanName;
    }

    public String getSubDescription() {
        return SubDescription;
    }

    public void setSubDescription(String subDescription) {
        SubDescription = subDescription;
    }

    public Boolean getAllowStatus1() {
        return AllowStatus1;
    }

    public void setAllowStatus1(Boolean allowStatus1) {
        AllowStatus1 = allowStatus1;
    }

    public Double getMonthly() {
        return Monthly;
    }

    public void setMonthly(Double monthly) {
        Monthly = monthly;
    }

    public Double getQuatarly() {
        return Quatarly;
    }

    public void setQuatarly(Double quatarly) {
        Quatarly = quatarly;
    }

    public Double getYearly() {
        return Yearly;
    }

    public void setYearly(Double yearly) {
        Yearly = yearly;
    }
}