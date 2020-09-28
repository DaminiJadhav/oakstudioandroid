package com.sdaemon.oakstudiotv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CareerResponse implements Serializable {

    @SerializedName("tblCareerDetails")
    @Expose
    private List<TblCareerDetails> tblCareerDetails;
    @SerializedName("JobId")
    @Expose
    private Integer JobId;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("OpeningDate")
    @Expose
    private String OpeningDate;
    @SerializedName("LastDate")
    @Expose
    private String LastDate;
    @SerializedName("CreatedBy")
    @Expose
    private Integer CreatedBy;
    @SerializedName("ActiveStatus")
    @Expose
    private boolean ActiveStatus;
    @SerializedName("CreatedDate")
    @Expose
    private String CreatedDate;
    @SerializedName("ModifiedBy")
    @Expose
    private Integer ModifiedBy;
    @SerializedName("ModifiedDate")
    @Expose
    private String ModifiedDate;

    public List<TblCareerDetails> getTblCareerDetails() {
        return tblCareerDetails;
    }

    public void setTblCareerDetails(List<TblCareerDetails> tblCareerDetails) {
        this.tblCareerDetails = tblCareerDetails;
    }

    public Integer getJobId() {
        return JobId;
    }

    public void setJobId(Integer jobId) {
        JobId = jobId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOpeningDate() {
        return OpeningDate;
    }

    public void setOpeningDate(String openingDate) {
        OpeningDate = openingDate;
    }

    public String getLastDate() {
        return LastDate;
    }

    public void setLastDate(String lastDate) {
        LastDate = lastDate;
    }

    public Integer getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    public boolean isActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
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

    public static class TblCareerDetails{
        @SerializedName("JobDetId")
        @Expose
        private Integer JobDetId;
        @SerializedName("JobId")
        @Expose
        private Integer JobId;
        @SerializedName("Deparment")
        @Expose
        private String Deparment;
        @SerializedName("Position")
        @Expose
        private String Position;
        @SerializedName("Qualification")
        @Expose
        private String Qualification;
        @SerializedName("Experiance")
        @Expose
        private String Experiance;
        @SerializedName("Salary")
        @Expose
        private String  Salary;
        @SerializedName("JobDescription")
        @Expose
        private String JobDescription;


        public Integer getJobDetId() {
            return JobDetId;
        }

        public void setJobDetId(Integer jobDetId) {
            JobDetId = jobDetId;
        }

        public Integer getJobId() {
            return JobId;
        }

        public void setJobId(Integer jobId) {
            JobId = jobId;
        }

        public String getDeparment() {
            return Deparment;
        }

        public void setDeparment(String deparment) {
            Deparment = deparment;
        }

        public String getPosition() {
            return Position;
        }

        public void setPosition(String position) {
            Position = position;
        }

        public String getQualification() {
            return Qualification;
        }

        public void setQualification(String qualification) {
            Qualification = qualification;
        }

        public String getExperiance() {
            return Experiance;
        }

        public void setExperiance(String experiance) {
            Experiance = experiance;
        }

        public String getSalary() {
            return Salary;
        }

        public void setSalary(String salary) {
            Salary = salary;
        }

        public String getJobDescription() {
            return JobDescription;
        }

        public void setJobDescription(String jobDescription) {
            JobDescription = jobDescription;
        }
    }

}
