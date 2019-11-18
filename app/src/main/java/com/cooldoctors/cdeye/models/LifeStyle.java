package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by shree on 3/2/16.
 */
public class LifeStyle implements Serializable {

    @Expose
    String id;

    @Expose
    String diet;

    @Expose
    String alcoholUse;

    @Expose
    String tobaccoUse;

    @Expose
    String alcoholDrinksPerWeek;

    @Expose
    String medicalCondition;

    @Expose
    String allergies;

    @Expose
    String vitaminSupplements;

    @Expose
    String medication;

    @Expose
    String allergiesToMedication;

    @Expose
    String smoke;

    @Expose
    String contactlenses;


    public LifeStyle() {
    }
    private static LifeStyle instance = null;

    public static LifeStyle getInstance() {
        if (instance == null) {
            instance = new LifeStyle();
        }
        return instance;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getContactlenses() {
        return contactlenses;
    }

    public void setContactlenses(String contactlenses) {
        this.contactlenses = contactlenses;
    }

    public String getAllergiesToMedication() {
        return allergiesToMedication;
    }

    public void setAllergiesToMedication(String allergiesToMedication) {
        this.allergiesToMedication = allergiesToMedication;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getAlcoholUse() {
        return alcoholUse;
    }

    public void setAlcoholUse(String alcoholUse) {
        this.alcoholUse = alcoholUse;
    }

    public String getTobaccoUse() {
        return tobaccoUse;
    }

    public void setTobaccoUse(String tobaccoUse) {
        this.tobaccoUse = tobaccoUse;
    }

    public String getAlcoholDrinksPerWeek() {
        return alcoholDrinksPerWeek;
    }

    public void setAlcoholDrinksPerWeek(String alcoholDrinksPerWeek) {
        this.alcoholDrinksPerWeek = alcoholDrinksPerWeek;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getVitaminSupplements() {
        return vitaminSupplements;
    }

    public void setVitaminSupplements(String vitaminSupplements) {
        this.vitaminSupplements = vitaminSupplements;
    }
}
