package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by shree on 3/2/16.
 */
public class Speciality implements Serializable {

    @Expose
    String id;

    @Expose
    String name;

    @Expose
    Questionaire questionaire;

    @Expose
    Report report;

    private static Speciality instance = null;

    public static Speciality getInstance() {
        if (instance == null) {
            instance = new Speciality();
        }
        return instance;
    }

    public Speciality() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Questionaire getQuestionaire() {
        return questionaire;
    }

    public void setQuestionaire(Questionaire questionaire) {
        this.questionaire = questionaire;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
