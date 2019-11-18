package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Ulhas on 2/26/16.
 */
public class Education implements Serializable {


   // String id;

    @Expose
    String degree, university, yearOfPasssing;




    private static Education instance = null;

    public static Education getInstance() {
        if (instance == null) {
            instance = new Education();
        }
        return instance;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYearOfPasssing() {
        return yearOfPasssing;
    }

    public void setYearOfPasssing(String yearOfPasssing) {
        this.yearOfPasssing = yearOfPasssing;
    }
}
