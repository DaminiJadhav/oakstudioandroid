package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanDescriptionDTO {

    @SerializedName("status")
    @Expose
    private Integer status;


    @SerializedName("result")
    @Expose
    private List<Result> resultList = null;


    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("Name")
        @Expose
        private String Name;

        @SerializedName("HD Available")
        @Expose
        private String HDAvailable;

        @SerializedName("Ultra HD Available")
        @Expose
        private String UltraHDAvailable;


        public String getHDAvailable() {
            return HDAvailable;
        }

        public void setHDAvailable(String HDAvailable) {
            this.HDAvailable = HDAvailable;
        }

        public String getUltraHDAvailable() {
            return UltraHDAvailable;
        }

        public void setUltraHDAvailable(String ultraHDAvailable) {
            UltraHDAvailable = ultraHDAvailable;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }


    }


}
