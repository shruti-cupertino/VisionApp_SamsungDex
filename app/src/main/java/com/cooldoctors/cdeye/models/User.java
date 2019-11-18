package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shree on 3/2/16.
 */
public class User implements Serializable {

    @Expose
    String degree;

    @Expose
    String id;

    @Expose
    String email;

    @Expose
    String pharmacyName;

    @Expose
    String pharmacyAddress;

    @Expose
    String profileImageId;

    @Expose
    String profileImageName;

    @Expose
    AboutMe aboutMe;

    @Expose
    ContactInfo contactInfo;

    @Expose
    PatientInfo patientInfo;

    @Expose
    DoctorInfo doctorInfo;

    @Expose
    Role role;

    @Expose
    Timezone timezone;

    @Expose
    String createdAt;

    User doctor;

    @Expose(serialize = false)
    String isEmailVerified, invited, validated, activated, managedUser;

    boolean isValidated;

    @Expose
    String forgotPasswordToken;

    @Expose
    Boolean isManagedUser;

    @Expose
    List<User> familyMembers;

    @Expose
    List<InsuranceCard> insurenceCards;

    @Expose
    User parentUser;

    @Expose
    String parentUserRelation;


    String twilioAccessToken;
    //    (serialize = false,deserialize = true)
    @Expose
    String ssnId;

    @Expose
    String password;

    @Expose
    String rating;

    @Expose
    List<GrantedAuthority> authorities;

    @Expose
    List<Pharmacy> preferredPharmacies;

    @Expose
    String kandyUserName;

    @Expose
    String kandyUserPassword;

    @Expose
    String kandyFullUserId;

    @Expose
    String oldPassword;

    @Expose
    String loginStatus;

    @Expose
    String stripeCustId;

    @Expose
    String profileImagePath;

    @Expose
    String isReferredDoctor;

    @Expose
    String randomtoken;

    Boolean markPreferedDoc;

    @Expose
    List<DoctorsGroup> doctorsGroups;

    @Expose
    List<String> preferredDoctors;

    int dashboardChooseDoc;

    public User() {
    }

    private static User userInstance = null;

    public static User getInstance() {
        if (userInstance == null) {
            userInstance = new User();
        }
        return userInstance;
    }

    public static ArrayList<User> pendingTestAppointments = new ArrayList<User>();
    public static ArrayList<User> doctorArrayList1 = new ArrayList<User>();
    public static ArrayList<User> chooseDoctorArrayList = new ArrayList<User>();

    public static ArrayList<User> preferredDoctorList = new ArrayList<User>();
    public static List<String> preferredDoctorsIds = new ArrayList<>();

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public String getKandyFullUserId() {
        return kandyFullUserId;
    }

    public void setKandyFullUserId(String kandyFullUserId) {
        this.kandyFullUserId = kandyFullUserId;
    }

    public int getDashboardChooseDoc() {
        return dashboardChooseDoc;
    }

    public void setDashboardChooseDoc(int dashboardChooseDoc) {
        this.dashboardChooseDoc = dashboardChooseDoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    public String getProfileImageName() {
        return profileImageName;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(String isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getTwilioAccessToken() {
        return twilioAccessToken;
    }

    public void setTwilioAccessToken(String twilioAccessToken) {
        this.twilioAccessToken = twilioAccessToken;
    }

    public String getInvited() {
        return invited;
    }

    public void setInvited(String invited) {
        this.invited = invited;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }

    public String getManagedUser() {
        return managedUser;
    }

    public void setManagedUser(String managedUser) {
        this.managedUser = managedUser;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public AboutMe getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(AboutMe aboutMe) {
        this.aboutMe = aboutMe;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getForgotPasswordToken() {
        return forgotPasswordToken;
    }

    public void setForgotPasswordToken(String forgotPasswordToken) {
        this.forgotPasswordToken = forgotPasswordToken;
    }

    public String getSsnId() {
        return ssnId;
    }

    public void setSsnId(String ssnId) {
        this.ssnId = ssnId;
    }

    public Boolean getIsManagedUser() {
        return isManagedUser;
    }

    public void setIsManagedUser(Boolean isManagedUser) {
        this.isManagedUser = isManagedUser;
    }

    public List<User> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<User> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }

    public String getParentUserRelation() {
        return parentUserRelation;
    }

    public void setParentUserRelation(String parentUserRelation) {
        this.parentUserRelation = parentUserRelation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    public String getKandyUserName() {
        return kandyUserName;
    }

    public void setKandyUserName(String kandyUserName) {
        this.kandyUserName = kandyUserName;
    }

    public String getKandyUserPassword() {
        return kandyUserPassword;
    }

    public void setKandyUserPassword(String kandyUserPassword) {
        this.kandyUserPassword = kandyUserPassword;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getStripeCustId() {
        return stripeCustId;
    }

    public void setStripeCustId(String stripeCustId) {
        this.stripeCustId = stripeCustId;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getIsReferredDoctor() {
        return isReferredDoctor;
    }

    public void setIsReferredDoctor(String isReferredDoctor) {
        this.isReferredDoctor = isReferredDoctor;
    }

    public String getRandomtoken() {
        return randomtoken;
    }

    public void setRandomtoken(String randomtoken) {
        this.randomtoken = randomtoken;
    }


    public boolean isValidated() {
        return isValidated;
    }

    public void setIsValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public List<Pharmacy> getPreferredPharmacies() {
        return preferredPharmacies;
    }

    public void setPreferredPharmacies(List<Pharmacy> preferredPharmacies) {
        this.preferredPharmacies = preferredPharmacies;
    }

    public Boolean getMarkPreferedDoc() {
        return markPreferedDoc;
    }

    public void setMarkPreferedDoc(Boolean markPreferedDoc) {
        this.markPreferedDoc = markPreferedDoc;
    }

    public List<String> getPreferredDoctors() {
        return preferredDoctors;
    }

    public void setPreferredDoctors(List<String> preferredDoctors) {
        this.preferredDoctors = preferredDoctors;
    }

    public List<DoctorsGroup> getDoctorsGroups() {
        return doctorsGroups;
    }

    public void setDoctorsGroups(List<DoctorsGroup> doctorsGroups) {
        this.doctorsGroups = doctorsGroups;
    }

    public List<InsuranceCard> getInsurenceCards() {
        return insurenceCards;
    }

    public void setInsurenceCards(List<InsuranceCard> insurenceCards) {
        this.insurenceCards = insurenceCards;
    }
}
