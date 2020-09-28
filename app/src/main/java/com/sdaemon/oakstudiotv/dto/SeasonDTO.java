package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonDTO {
    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("Seasons")
    @Expose
    private List<Seasons> seasons;

    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class  Seasons{

        @SerializedName("SeasonId")
        @Expose
        private Integer SeasonId;

        @SerializedName("SeasonNo")
        @Expose
        private Integer SeasonNo;

        @SerializedName("Name")
        @Expose
        private String Name;


        public Integer getSeasonId() {
            return SeasonId;
        }

        public void setSeasonId(Integer seasonId) {
            SeasonId = seasonId;
        }

        public Integer getSeasonNo() {
            return SeasonNo;
        }

        public void setSeasonNo(Integer seasonNo) {
            SeasonNo = seasonNo;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }

}
