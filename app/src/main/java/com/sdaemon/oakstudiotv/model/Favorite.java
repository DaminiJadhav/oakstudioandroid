package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {
    @SerializedName("Favourite")
    @Expose
    private Integer Favourite;

    public Integer getFavourite() {
        return Favourite;
    }

    public void setFavourite(Integer favourite) {
        Favourite = favourite;
    }
}
