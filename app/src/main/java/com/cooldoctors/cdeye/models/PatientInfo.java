package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by shree on 3/2/16.
 */
public class PatientInfo implements Serializable {

    @Expose
    private String id;

    @Expose
    LifeStyle lifeStyle;

    @Expose
    FamilyHistory familyHistory;

    public PatientInfo() {
    }

    private static PatientInfo instance = null;

    public static PatientInfo getInstance() {
        if (instance == null) {
            instance = new PatientInfo();
        }
        return instance;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LifeStyle getLifeStyle() {
        return lifeStyle;
    }

    public void setLifeStyle(LifeStyle lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public FamilyHistory getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(FamilyHistory familyHistory) {
        this.familyHistory = familyHistory;
    }
}
