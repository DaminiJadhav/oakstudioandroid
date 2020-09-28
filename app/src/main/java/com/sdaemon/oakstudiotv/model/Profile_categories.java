package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class Profile_categories implements Serializable {

    public String profile_catName;


    public Profile_categories(String profile_catName) {
        this.profile_catName = profile_catName;
    }


    public Profile_categories() {
    }

    public String getProfile_catName() {
        return profile_catName;
    }

    public void setProfile_catName(String profile_catName) {
        this.profile_catName = profile_catName;
    }
}
