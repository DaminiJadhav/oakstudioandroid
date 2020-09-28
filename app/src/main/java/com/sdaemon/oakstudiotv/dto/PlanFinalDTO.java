package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanFinalDTO {

    @SerializedName("CorePlan")
    @Expose
    private List<CorePlan> corePlanList = null;


    @SerializedName("Details")
    @Expose
    private List<Details> detailList = null;

    @SerializedName("Cost")
    @Expose
    private List<Cost> costList = null;


    @SerializedName("Period")
    @Expose
    private List<Period> periodList = null;


    public List<CorePlan> getCorePlanList() {
        return corePlanList;
    }

    public void setCorePlanList(List<CorePlan> corePlanList) {
        this.corePlanList = corePlanList;
    }

    public List<Details> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Details> detailList) {
        this.detailList = detailList;
    }

    public List<Cost> getCostList() {
        return costList;
    }

    public void setCostList(List<Cost> costList) {
        this.costList = costList;
    }

    public List<Period> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<Period> periodList) {
        this.periodList = periodList;
    }

    public class CorePlan {

        @SerializedName("CorePlanId")
        @Expose
        private Integer corePlanId;

        @SerializedName("CorePlanName")
        @Expose
        private String corePlanName;


        public Integer getCorePlanId() {
            return corePlanId;
        }

        public void setCorePlanId(Integer corePlanId) {
            this.corePlanId = corePlanId;
        }

        public String getCorePlanName() {
            return corePlanName;
        }

        public void setCorePlanName(String corePlanName) {
            this.corePlanName = corePlanName;
        }
    }

    public static class Details {

        @SerializedName("CorePlanId")
        @Expose
        private Integer corePlanId;

        @SerializedName("PeriodId")
        @Expose
        private Integer periodId;

        @SerializedName("SubPlanDescrId")
        @Expose
        private Integer subPlanDescrId;


        @SerializedName("SubDescription")
        @Expose
        private String subDescription;


        @SerializedName("AllowStatus")
        @Expose
        private Boolean allowStatus;


        @SerializedName("value")
        @Expose
        private Integer value;

        @SerializedName("status")
        @Expose
        private List<Status> statusList = null;


         public List<Status> getStatusList() {
            return statusList;
        }

        public void setStatusList(List<Status> statusList) {
            this.statusList = statusList;
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

        public Integer getSubPlanDescrId() {
            return subPlanDescrId;
        }

        public void setSubPlanDescrId(Integer subPlanDescrId) {
            this.subPlanDescrId = subPlanDescrId;
        }

        public String getSubDescription() {
            return subDescription;
        }

        public void setSubDescription(String subDescription) {
            this.subDescription = subDescription;
        }

        public Boolean getAllowStatus() {
            return allowStatus;
        }

        public void setAllowStatus(Boolean allowStatus) {
            this.allowStatus = allowStatus;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public static class Status {

        @SerializedName("allowStatus")
        @Expose
        private Boolean allowStatus = false;


        public Boolean getAllowStatus() {
            return allowStatus;
        }

        public void setAllowStatus(Boolean allowStatus) {
            this.allowStatus = allowStatus;
        }
    }



    public static class Cost {

        @SerializedName("PeriodId")
        @Expose
        private Integer periodId;

        @SerializedName("CorePlanId")
        @Expose
        private Integer corePlanId;

        @SerializedName("Name")
        @Expose
        private String name;


        @SerializedName("Amount")
        @Expose
        private Double amount;


        public Integer getPeriodId() {
            return periodId;
        }

        public void setPeriodId(Integer periodId) {
            this.periodId = periodId;
        }

        public Integer getCorePlanId() {
            return corePlanId;
        }

        public void setCorePlanId(Integer corePlanId) {
            this.corePlanId = corePlanId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    public class Period {


        @SerializedName("PeriodId")
        @Expose
        private Integer periodId;

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("NoOfDays")
        @Expose
        private Integer noOfDays;


        public Integer getPeriodId() {
            return periodId;
        }

        public void setPeriodId(Integer periodId) {
            this.periodId = periodId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNoOfDays() {
            return noOfDays;
        }

        public void setNoOfDays(Integer noOfDays) {
            this.noOfDays = noOfDays;
        }
    }


}
