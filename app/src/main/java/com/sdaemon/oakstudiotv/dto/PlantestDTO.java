package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdaemon.oakstudiotv.model.Datum;

import java.util.List;

public class PlantestDTO {

    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("Data")
    @Expose
    private List<Datum> Data;
    @SerializedName("Message")
    @Expose
    private String Message;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public List<Datum> getData() {
        return Data;
    }

    public void setData(List<Datum> data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


}
