package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ulhas on 2/26/16.
 */
public class DoctorInfo implements Serializable {

    @Expose
    String id;

    @Expose
    List<AwardReceived> awardsReceivedList;;

    @Expose
    List<LicenceState> licencedState;

    @Expose
    List<Education> educationList;

    @Expose
    List<HospitalAttachedWith> hospitalAttachedWithList;

    @Expose
    List<Speciality> specialities;

    @Expose
    String registrationNumber;

    @Expose
    String whyCoolDoctors;

    @Expose
    String bio;

    @Expose
    String profession;


    @Expose
    String npi;


    public DoctorInfo() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getWhyCoolDoctors() {
        return whyCoolDoctors;
    }

    public void setWhyCoolDoctors(String whyCoolDoctors) {
        this.whyCoolDoctors = whyCoolDoctors;
    }


    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public List<AwardReceived> getAwardsReceivedList() {
        return awardsReceivedList;
    }

    public void setAwardsReceivedList(List<AwardReceived> awardsReceivedList) {
        this.awardsReceivedList = awardsReceivedList;
    }

    public List<LicenceState> getLicencedState() {
        return licencedState;
    }

    public void setLicencedState(List<LicenceState> licencedState) {
        this.licencedState = licencedState;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<HospitalAttachedWith> getHospitalAttachedWithList() {
        return hospitalAttachedWithList;
    }

    public void setHospitalAttachedWithList(List<HospitalAttachedWith> hospitalAttachedWithList) {
        this.hospitalAttachedWithList = hospitalAttachedWithList;
    }

    public String getNpi() {
        return npi;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }
}
