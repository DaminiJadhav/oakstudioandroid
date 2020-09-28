package com.sdaemon.oakstudiotv.model;

public class SearchData {
    public String name;
    public int age;
    public int image;
    public SearchData(String name, int age,int image) {
        this.name = name;
        this.age = age;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
