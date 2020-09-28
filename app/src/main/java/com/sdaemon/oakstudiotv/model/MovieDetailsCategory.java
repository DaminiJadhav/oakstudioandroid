package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class MovieDetailsCategory implements Serializable {

   public String CategoryName;


    public MovieDetailsCategory(String categoryName) {
        CategoryName = categoryName;
    }

    public MovieDetailsCategory() {
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
