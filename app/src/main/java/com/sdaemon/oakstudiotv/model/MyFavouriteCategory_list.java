package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class MyFavouriteCategory_list implements Serializable {

    public MyFavouriteCategory_list() {
    }

    public String categoryName="";

    public MyFavouriteCategory_list(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}

