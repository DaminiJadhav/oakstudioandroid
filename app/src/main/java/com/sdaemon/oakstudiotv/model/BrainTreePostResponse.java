package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BrainTreePostResponse implements Serializable {

    @SerializedName("Id")
    @Expose
    String Id;
    @SerializedName("msg")
    @Expose
    String msg;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
