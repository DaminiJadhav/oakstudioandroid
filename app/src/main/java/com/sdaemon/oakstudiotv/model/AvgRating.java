package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvgRating {
    @SerializedName("AvgRating")
    @Expose
    private Integer AvgRating;

    public Integer getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(Integer avgRating) {
        AvgRating = avgRating;
    }
}
