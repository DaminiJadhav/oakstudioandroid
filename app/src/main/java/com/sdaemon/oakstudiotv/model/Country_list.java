package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class Country_list implements Serializable {

    public String countryName="";

    public Country_list(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public Country_list() {
    }

    public String countryCode="";

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


}

