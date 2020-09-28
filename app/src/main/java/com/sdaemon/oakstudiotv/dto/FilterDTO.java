package com.sdaemon.oakstudiotv.dto;

public class FilterDTO {

    String GenresID = "",test="";
    public FilterDTO(){

    }
    public FilterDTO(String GenresID){
      this.GenresID = GenresID;
  }

    public String getGenresID() {
        return GenresID;
    }

    public void setGenresID(String genresID) {
        GenresID = genresID;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
