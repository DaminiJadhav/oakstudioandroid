package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DescriptionList {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("boolValue")
    @Expose
    private String boolValue;
    @SerializedName("screen")
    @Expose
    private String screen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(String boolValue) {
        this.boolValue = boolValue;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }
}
