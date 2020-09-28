package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class RecentCategory_list implements Serializable {

    public RecentCategory_list() {
    }

    public String categoryName="";

    public RecentCategory_list(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}

