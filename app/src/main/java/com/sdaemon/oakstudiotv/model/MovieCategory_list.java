package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class MovieCategory_list extends Category implements Serializable {

    public MovieCategory_list() {
    }

    public String categoryName="";

    public MovieCategory_list(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}

