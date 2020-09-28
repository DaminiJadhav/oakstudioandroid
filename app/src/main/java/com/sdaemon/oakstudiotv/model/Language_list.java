package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class Language_list implements Serializable {

    String language;


    public Language_list() {
    }

    public Language_list(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
