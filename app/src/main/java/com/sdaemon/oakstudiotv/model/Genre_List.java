package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class Genre_List implements Serializable {

    private String genreId;
    private String genreType;

    public Genre_List(String genreId, String genreType) {
        this.genreId = genreType;
        this.genreType = genreType;
    }

    public Genre_List() {
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }
    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }


}
