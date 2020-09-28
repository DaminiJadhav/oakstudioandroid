package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanNewDTO {

    @SerializedName("status")
    @Expose
    private Integer status;


    @SerializedName("CorePlan")
    @Expose
    private List<CorePlan> corePlanList = null;

    @SerializedName("Period")
    @Expose
    private List<Period> periodList = null;


    @SerializedName("Description")
    @Expose
    private List<Description> descriptionList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CorePlan> getCorePlanList() {
        return corePlanList;
    }

    public void setCorePlanList(List<CorePlan> corePlanList) {
        this.corePlanList = corePlanList;
    }

    public List<Period> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<Period> periodList) {
        this.periodList = periodList;
    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public class CorePlan {

        @SerializedName("CorePlanId")
        @Expose
        private Integer CorePlanId;
        @SerializedName("CorePlanName")
        @Expose
        private String CorePlanName;

        public Integer getCorePlanId() {
            return CorePlanId;
        }

        public void setCorePlanId(Integer corePlanId) {
            CorePlanId = corePlanId;
        }

        public String getCorePlanName() {
            return CorePlanName;
        }

        public void setCorePlanName(String corePlanName) {
            CorePlanName = corePlanName;
        }
    }

    public class Period {

        @SerializedName("CorePlanId")
        @Expose
        private Integer CorePlanId;
        @SerializedName("PeriodId")
        @Expose
        private Integer PeriodId;
        @SerializedName("Name")
        @Expose
        private String Name;
        @SerializedName("PlanCost")
        @Expose
        private Double PlanCost;

        public Integer getCorePlanId() {
            return CorePlanId;
        }

        public void setCorePlanId(Integer corePlanId) {
            CorePlanId = corePlanId;
        }

        public Integer getPeriodId() {
            return PeriodId;
        }

        public void setPeriodId(Integer periodId) {
            PeriodId = periodId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Double getPlanCost() {
            return PlanCost;
        }

        public void setPlanCost(Double planCost) {
            PlanCost = planCost;
        }
    }

    public class Description {

        @SerializedName("CorePlanId")
        @Expose
        private Integer CorePlanId;
        @SerializedName("PeriodId")
        @Expose
        private Integer PeriodId;
        @SerializedName("SubPlanDescrId")
        @Expose
        private Integer SubPlanDescrId;
        @SerializedName("SubDescription")
        @Expose
        private String SubDescription;
        @SerializedName("AllowStatus")
        @Expose
        private Boolean AllowStatus;

        public Integer getCorePlanId() {
            return CorePlanId;
        }

        public void setCorePlanId(Integer corePlanId) {
            CorePlanId = corePlanId;
        }

        public Integer getPeriodId() {
            return PeriodId;
        }

        public void setPeriodId(Integer periodId) {
            PeriodId = periodId;
        }

        public Integer getSubPlanDescrId() {
            return SubPlanDescrId;
        }

        public void setSubPlanDescrId(Integer subPlanDescrId) {
            SubPlanDescrId = subPlanDescrId;
        }

        public String getSubDescription() {
            return SubDescription;
        }

        public void setSubDescription(String subDescription) {
            SubDescription = subDescription;
        }

        public Boolean getAllowStatus() {
            return AllowStatus;
        }

        public void setAllowStatus(Boolean allowStatus) {
            AllowStatus = allowStatus;
        }
    }


}
