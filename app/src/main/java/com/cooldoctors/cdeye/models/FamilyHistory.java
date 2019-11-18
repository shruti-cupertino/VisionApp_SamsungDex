package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shree on 3/2/16.
 */
public class FamilyHistory implements Serializable {

    @Expose
    String id;

    @Expose
    String description;

    @Expose
    List<DiseaseRelation> diseaseRelation;

    private static FamilyHistory instance = null;

    public static FamilyHistory getInstance() {
        if (instance == null) {
            instance = new FamilyHistory();
        }
        return instance;
    }

    public FamilyHistory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DiseaseRelation> getDiseaseRelation() {
        return diseaseRelation;
    }

    public void setDiseaseRelation(List<DiseaseRelation> diseaseRelation) {
        this.diseaseRelation = diseaseRelation;
    }
}
