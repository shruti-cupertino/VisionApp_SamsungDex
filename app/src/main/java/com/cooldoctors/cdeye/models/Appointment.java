package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shree on 3/2/16.
 */
public class Appointment implements Serializable {

    @Expose
    String id;

    @Expose
    String startDate, chiefCompliant;

    @Expose
    String endDate;

    @Expose
    User doctor;

    @Expose
    User patient;

    /*@Expose
    Prescription prescription;*/

    @Expose
    Questionaire questionaire;

    /*@Expose
    SolutionBottleImage solutionBottleImage;

    @Expose
    VideoMessage videoMessage, audioMessage;*/

    @Expose
    Report report;
    /*@Expose
    List<Todo> todos;*/

    @Expose
    Speciality speciality;

    @Expose
    String createdAt;

    @Expose(serialize = false)
    boolean isCancelled;

    /*@Expose(serialize = false)
    ClinicDao eclClinic;*/

    @Expose(serialize = false)
    boolean isInProgress;

    /*@Expose(serialize = false)
    RecomendedTest recomendedTests;*/

    boolean isFinished;

    boolean isRescheduleApp;

    boolean isSavedAppointment;

    @Expose
    int appointmentPrice;

    @Expose
    String status;

    @Expose
    String visitPurpose;

    @Expose
    String visitType;

    @Expose(serialize = false)
    boolean isFollowUp, eclClinicVisit;

    @Expose
    int waitId;

    @Expose
    String currentLocationState;

    @Expose
    String isReferredDoctorAvailable;

    /*@Expose
    DiseaseImageFilePath diseaseImageFilePath;

    @Expose
     DiseaseImageFilePath contactlenseImageFilePath;*/

    @Expose
    String patientPreferredVisitLanguage;

    @Expose
    String symtons;

    @Expose
    String createdBy;

    @Expose(serialize = false)
    boolean isChargeble;

    @Expose
    String paymentStatus;

    /*@Expose
    VisionTest visionTest;*/

    @Expose
    String videoVisitPath;

    /*@Expose
    LasikSurgery lasikSurgery;*/

    @Expose
    String paymentMode;

    /*@Expose
    AvailabilityTimeSlot availabilityTimeSlot;*/

    @Expose
    String convertedStartDate;

    /*@Expose
    private EyeTest eyeTest;

    @Expose
    private Coverage coverageParam;*/

    String selectedPaymentOption;

    boolean isPreferredDocAvailable = false;

    boolean isPreferredClinicAvailable = false;

    @Expose
    private int testStatus;
    public boolean isPreferredClinicAvailable() {
        return isPreferredClinicAvailable;
    }

    public void setPreferredClinicAvailable(boolean preferredClinicAvailable) {
        isPreferredClinicAvailable = preferredClinicAvailable;
    }


    public Appointment() {
    }

    private static Appointment appointment = null;

    public static Appointment getInstance() {
        if (appointment == null) {
            appointment = new Appointment();
        }
        return appointment;
    }
    public boolean isPreferredDocAvailable() {
        return isPreferredDocAvailable;
    }

    public void setPreferredDocAvailable(boolean preferredDocAvailable) {
        isPreferredDocAvailable = preferredDocAvailable;
    }


    public boolean isEclClinicVisit() {
        return eclClinicVisit;
    }

    /*public RecomendedTest getRecomendedTests() {
        return recomendedTests;
    }

    public void setRecomendedTests(RecomendedTest recomendedTests) {
        this.recomendedTests = recomendedTests;
    }

    public void setEclClinicVisit(boolean eclClinicVisit) {
        this.eclClinicVisit = eclClinicVisit;
    }

    public ClinicDao getEclClinic() {
        return eclClinic;
    }

    public void setEclClinic(ClinicDao eclClinic) {
        this.eclClinic = eclClinic;
    }

    public VideoMessage getAudioMessage() {
        return audioMessage;
    }

    public void setAudioMessage(VideoMessage audioMessage) {
        this.audioMessage = audioMessage;
    }*/

    public boolean isSavedAppointment() {
        return isSavedAppointment;
    }

    public void setSavedAppointment(boolean savedAppointment) {
        isSavedAppointment = savedAppointment;
    }

    public String getChiefCompliant() {
        return chiefCompliant;
    }

    public void setChiefCompliant(String chiefCompliant) {
        this.chiefCompliant = chiefCompliant;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public static void reset(){
        appointment = new Appointment();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    /*public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }*/

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

    /*public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }*/

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(boolean isInProgress) {
        this.isInProgress = isInProgress;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public int getAppointmentPrice() {
        return appointmentPrice;
    }

    public void setAppointmentPrice(int appointmentPrice) {
        this.appointmentPrice = appointmentPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFollowUp() {
        return isFollowUp;
    }

    public void setIsFollowUp(boolean isFollowUp) {
        this.isFollowUp = isFollowUp;
    }

    public int getWaitId() {
        return waitId;
    }

    public void setWaitId(int waitId) {
        this.waitId = waitId;
    }

    public String getCurrentLocationState() {
        return currentLocationState;
    }

    public void setCurrentLocationState(String currentLocationState) {
        this.currentLocationState = currentLocationState;
    }

    public String getIsReferredDoctorAvailable() {
        return isReferredDoctorAvailable;
    }

    public void setIsReferredDoctorAvailable(String isReferredDoctorAvailable) {
        this.isReferredDoctorAvailable = isReferredDoctorAvailable;
    }

    /*public DiseaseImageFilePath getDiseaseImageFilePath() {
        return diseaseImageFilePath;
    }

    public void setDiseaseImageFilePath(DiseaseImageFilePath diseaseImageFilePath) {
        this.diseaseImageFilePath = diseaseImageFilePath;
    }

    public DiseaseImageFilePath getContactlenseImageFilePath() {
        return contactlenseImageFilePath;
    }

    public void setContactlenseImageFilePath(DiseaseImageFilePath contactlenseImageFilePath) {
        this.contactlenseImageFilePath = contactlenseImageFilePath;
    }

    public SolutionBottleImage getSolutionBottleImage() {
        return solutionBottleImage;
    }

    public void setSolutionBottleImage(SolutionBottleImage solutionBottleImage) {
        this.solutionBottleImage = solutionBottleImage;
    }*/

    public String getPatientPreferredVisitLanguage() {
        return patientPreferredVisitLanguage;
    }

    public void setPatientPreferredVisitLanguage(String patientPreferredVisitLanguage) {
        this.patientPreferredVisitLanguage = patientPreferredVisitLanguage;
    }

    public String getSymtons() {
        return symtons;
    }

    public void setSymtons(String symtons) {
        this.symtons = symtons;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isChargeble() {
        return isChargeble;
    }

    public void setIsChargeble(boolean isChargeble) {
        this.isChargeble = isChargeble;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getVideoVisitPath() {
        return videoVisitPath;
    }

    public void setVideoVisitPath(String videoVisitPath) {
        this.videoVisitPath = videoVisitPath;
    }

    /*public VisionTest getVisionTest() {
        return visionTest;
    }

    public void setVisionTest(VisionTest visionTest) {
        this.visionTest = visionTest;
    }*/

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    /*public LasikSurgery getLasikSurgery() {
        return lasikSurgery;
    }

    public AvailabilityTimeSlot getAvailabilityTimeSlot() {
        return availabilityTimeSlot;
    }

    public void setAvailabilityTimeSlot(AvailabilityTimeSlot availabilityTimeSlot) {
        this.availabilityTimeSlot = availabilityTimeSlot;
    }

    public void setLasikSurgery(LasikSurgery lasikSurgery) {
        this.lasikSurgery = lasikSurgery;
    }*/

    public boolean getIsRescheduleApp() {
        return isRescheduleApp;
    }

    public void setIsRescheduleApp(boolean isRescheduleApp) {
        this.isRescheduleApp = isRescheduleApp;
    }

    public String getConvertedStartDate() {
        return convertedStartDate;
    }

    public void setConvertedStartDate(String convertedStartDate) {
        this.convertedStartDate = convertedStartDate;
    }

    public String getSelectedPaymentOption() {
        return selectedPaymentOption;
    }

    public void setSelectedPaymentOption(String selectedPaymentOption) {
        this.selectedPaymentOption = selectedPaymentOption;
    }

    /*public Coverage getCoverageParam() {
        return coverageParam;
    }

    public void setCoverageParam(Coverage coverageParam) {
        this.coverageParam = coverageParam;
    }

    public EyeTest getEyeTest() {
        return eyeTest;
    }

    public void setEyeTest(EyeTest eyeTest) {
        this.eyeTest = eyeTest;
    }

    public VideoMessage getVideoMessage() {
        return videoMessage;
    }

    public void setVideoMessage(VideoMessage videoMessage) {
        this.videoMessage = videoMessage;
    }*/
    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }

}
