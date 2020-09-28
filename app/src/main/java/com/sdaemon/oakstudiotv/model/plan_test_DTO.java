package com.sdaemon.oakstudiotv.model;

public class plan_test_DTO {

    private boolean expanded;
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public String getPlan() {
        return Plan;
    }

    public void setPlan(String plan) {
        Plan = plan;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    String Plan = "", Detail = "";
    Integer value = 0;

    public plan_test_DTO(String plan, String detail, Integer value) {
        Plan = plan;
        Detail = detail;
        this.value = value;
    }
}