package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shree on 3/2/16.
 */
public class Questionaire implements Serializable {

    @Expose
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*@Expose
    List<Question> questions;

    @Expose
    List<Question> contactLenseQuestions;

    @Expose
    List<Question> symptoms;

    @Expose
    List<Severity> severity;

    @Expose
    List<Frequency> frequency;

    @Expose
    List<Treatment> treatment;


    @Expose
    LasikQuestionnaire lasikQuestionnaire;

    @Expose
    Appointment appointment;*/



    public Questionaire() {
    }


    private static Questionaire questionaire = null;

    public static Questionaire getInstance() {
        if (questionaire == null) {
            questionaire = new Questionaire();
        }
        return questionaire;
    }

    /*public List<Question> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Question> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<Severity> getSeverity() {
        return severity;
    }

    public void setSeverity(List<Severity> severity) {
        this.severity = severity;
    }

    public List<Frequency> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<Frequency> frequency) {
        this.frequency = frequency;
    }

    public List<Treatment> getTreatment() {
        return treatment;
    }

    public void setTreatment(List<Treatment> treatment) {
        this.treatment = treatment;
    }

    public List<Question> getContactLensQuestions() {
        return contactLenseQuestions;
    }

    public void setContactLensQuestions(List<Question> contactLensQuestions) {
        this.contactLenseQuestions = contactLensQuestions;
    }

    public LasikQuestionnaire getLasikQuestionnaire() {
        return lasikQuestionnaire;
    }

    public void setLasikQuestionnaire(LasikQuestionnaire lasikQuestionnaire) {
        this.lasikQuestionnaire = lasikQuestionnaire;
    }*/
}
