package com.cooldoctors.cdeye.constants;

public interface ServiceConstants {


    String signIn = "/DoctorOnDemand/api/auth/login";

    String registerFCM = "/DoctorOnDemand/api/auth/addDeviceToken";
    String removeFCMToken = "/DoctorOnDemand/api/auth/removeDeviceToken";

    String tokenKey = "X-Auth-Token";
    String contentTypeKey = "Content-Type";
    String contentType = ": application/json";


    String EclClinic = "eclClinic/";
    String Clinic = "clinic/";
    String GetEclClinic = "getEclClinic/";
    String Grpfollowup2 = "grpfollowup";
    String Grpfollowup3 = "grpfollowup/";
    String Getchatidbyeclclinicid = "getchatidbyeclclinicid/";
    String GetDoctorsByEclClinic = "getDoctorsByEclClinic/";
    String GrpfollowupGetMsgByChatId = "grpfollowup/getmsgbychatid/";
    String Grpfollowup = "grpfollowup/getchatuserlist";
    String SearchDoctorAndEclClinicByName = "searchDoctorAndEclClinicByName/v3";
    String SearchDoctorAndEclClinicByZip = "searchDoctorAndEclClinicByZip/v3/";
    String GetDoctorsAndEclClinicByStateAndZip = "getDoctorsAndEclClinicsByStateAndZip/v3/";
    String DoctorOnDemand = "DoctorOnDemand/api/auth/";
    String DoctorOnDemandApi = "DoctorOnDemand/api/";
    String DoctorOnDemandPatient = "DoctorOnDemand/api/patient/";
    String DoctorOnDemandUser = "DoctorOnDemand/api/user/";
    String DoctorOnDemandAuthPatientUser = "DoctorOnDemand/api/patient/user/";
    String DoctorOnDemandAuthPatientEclClinic = "DoctorOnDemand/api/patient/eclClinic/";
    String MarkPreferredEclClinic = "markPreferredEclClinic/";
    String UnmarkPreferredEclClinic = "unmarkPreferredEclClinic/";
    String getEyetestimage = "appointment/getEyetestimage/";
    String SignUp = "signup/v3";
    String GetAllDoctorsAndEclClinics = "getAllDoctorsAndEclClinics/v3";
    String PaymentModuleAPI = "PaymentModule/api/card/";
    String Login = "login";
    String LogOut = "logout";
    String Id = "id";
    String AddDeviceToke = "addDeviceToken";
    String RemoveDeviceToke = "removeDeviceToken";
    String resetPass = "changeforgotpassword";
    String followup = "followup/";
    String userID = "UserID";
    String getinbox = "getinbox";
    String getsent = "getsent";
    String updatestatus = "updatestatus/";
    String updatestatuswithoutApp = "updatestatusWithoutAppointmentId/";
    String featuredDoctor = "featuredDoctors";
    String forgotPassword = "forgotpassword";
    String checkExistingMail = "checkExistingEmail";
    String faq = "faq";
    String faqGetBySpecialization = "faq/getbyspecialization/EyeCare";
    String Patient = "patient/";
    String DiseaseImagesNew = "diseaseImagesNew";
    String PatientFollowup = "patient/followup";
    String PatientGrpFollowup = "patient/grpfollowup";
    String Appointment = "appointment/";
    String TwilioCreateToken = "assignTwilioTokenToPatient/";
    String UploadFollowUpMessageFilesInfoIndividual = "followup/uploadFollowUpMessageFilesInfoIndividual";
    String UploadGrpFollowUpMessageFilesInfoIndividual = "grpfollowup/uploadFollowUpMessageFilesInfoIndividual";
    String Pharmacy = "pharmacy";
    String FolloUp = "followup/";
    String getCounts = "getCounts";
    String FollowUpChat = "followup";
    String getURL = "/getUrl/";


    //PAYMENT RELATED API's
    String PaymentAPI = "PaymentModule/api/";
    String TokenCreate = "token/create";
    String CustomerCreate = "customer/create";
    String Customer = "customer/";
    String CardCreate = "card/create";
    String AddStripeCustomer = "addstripecustomer/";
    String getStripeCustomerId = "patient/user/getstripecustomerid";

    String uploadFollowUpMessageFilesInfo = "/uploadFollowUpMessageFilesInfo/";
    String uploadFollowUpMessageFilesInfoIndividual = "/uploadFollowUpMessageFilesInfoIndividual";
    String uploadFollowUpMessageFilesIndividual = "/uploadFollowUpMessageFilesIndividual/";
    String uploadFollowUpImgFilesInfo = "/uploadFollowUpMessageFiles/";
    String getinboxofindividuals = "getinboxofindividuals";
    String getsentofindividuals = "getsentofindividuals";
    String GetMessages = "getmessages/";
    String GetChatUserList = "getchatuserlist/v3";
    String GetMsgByChatId = "getmsgbychatid/";
    String GetMsgByApntId = "getmsgbyapntid/";

    String GetMsgByDocId = "getchatidbydocis/";
    String GetMsgByDocIdAndChatId = "updtmsgstsbychtidanddocid/";
    String User = "user/";
    String GetStateZipcodes = "state/get";
    String EyeTest = "eyetest/";
    String GetLatestEyeTest = "getLatestEyeTest/";
    String GetAllEyeTests = "getAllEyeTests";
    String CheckUserEligibility = "eligible/checkUserEligiblity";
    String GetAllPayers = "getAllPayers";
    String GetAllEyeTestsByTestType = "getAllEyeTestsByTestType/";
    String insuraneCard = "insurencecard/";
    String insurencecardWithoutImage = "insurencecardWithoutImage/";
    String insuraneCardURL = "insurencecardurl/";
    String signedUrlVideoMsg = "getSignedUrlforVideoMessage/";
    String Eligible = "eligible/";
    String getDiseaseImage = "getDiseaseImage/";
    String getDashboardInfoV3 = "getDashboardInfo/v3";
    String startConsultationV3 = "startConsultation/v3";
    String GetDates = "getDates/v3";
    String visionTest = "visionTest";
    String UpdateProfileNotification = "updateProfileNotification/";
    String Upcoming = "upcoming";
    String saved = "saved";
    String UpdateAppointment = "updateappointmentdoctor";
    String Reschedule = "reschedule/";
    String CancelQueued = "cancelqueued/";
    String CancelCall = "cancel/";
    String deleteSavedAppointment = "deleteSavedAppointment/";
    String GetPatientHistory = "getPatientHistory/";
    String PatientInfo = "patientinfo";
    String Profile = "profile/";
    String ProfileUpdate = "profile";
    String familyMemberHistory = "/familyMember/";
    String familyMember = "familymember/";
    String Delete = "delete/";
    String FamilyMember = "familyMember";
    String EXCLUDE_AUTHENTICATION = "DoctorOnDemand/api//auth";
    String GetDrByState = "doctorsbystate/";

    String GetUrl = "getUrl/";
    String GetLastLasikVisit = "getLastLasikVisit";
    String GetAvailabilityOfCurrentWeek = "getAvailabilityOfCurrentWeek";
    String GetAvailabilityOfNextWeek = "getAvailabilityOfNextWeek";

    String RemoveStripeCustomer = "removeStripeCustomer";
    String GetDrByGroup = "doctorsbygroup/";
    String PaymentModule = "PaymentModule/api/card/listall/";
    String Doctor = "doctor/";
    String getLatestSpeedTest = "getLatestSpeedTestNew/";
    String getSpeedTestByTime = "getSpeedTestByTimeNew/";
    String Week = "week/";
    String Month = "month/";
    String All = "all/";


    String CreateVisitWithAvailability = "createVisitWithAvailability";
    String UpdateVisitV3 = "updateVisit/v3/";
    String CreateVisitV3 = "createVisit/v3";
    String CreateClinicVisitV3 = "createEclClinicVisit/v3";
    String DiseaseImages = "diseaseImages";
    String UploadVideoMsg = "uploadVideoMessage";

    String UploadSolutionBottleImage = "uploadSolutionBottleImage";
    String ContactLenseImagesNew = "contactLenseImagesNew";
    String EyeTestImages = "eyetestimage/";
    String Doctors = "doctors";
    String getDoctorsByCountry = "getDoctorsByCountry/";
    String getPreferredDoc = "getPreferredDoctors";
    String markPreferredDoc = "markPreferredDoctor/";
    String unmarkPreferredDoc = "unmarkPreferredDoctor/";
    String GetPreferredDoctorAndEclClinic = "getPreferredDoctorAndEclClinic";
    String CreateContactLens = "test/createContactlens/v3";
    String CreateDryEye = "test/createDry/v3";

    /**
     * My Eye care constant api calls
     */
    String consultantHistory = DoctorOnDemandApi+"patient/appointment/getPatientHistory/";
    String dryEyeDetails = DoctorOnDemandApi+"patient/test/getListDryEyeById/v3/";
    String contactEyeDetails = DoctorOnDemandApi+"patient/test/getListContactLensById/v3/";
    String visionEyeDetails = DoctorOnDemandApi+"patient/test/getListVisionById/v3/";
    String testReportFile = DoctorOnDemandApi+"patient/appointment/getUrl/";
    /*String registerFCM = DoctorOnDemandApi+"auth/addDeviceToken";
    String removeFCMToken = DoctorOnDemandApi+"auth/removeDeviceToken";

    String tokenKey = "X-Auth-Token";
    String contentTypeKey = "Content-Type";*/

    /**
     * Vision test api calls
     */
    String visionScanQR = DoctorOnDemandApi+"patient/vision/validateQrCode/";
    String visionUploadDetails = DoctorOnDemandApi+"patient/vision/updateTestNew";
    String visionTestResult = DoctorOnDemandApi+"patient/test/createVision/v3";
    String vTestWithAppointmentId = DoctorOnDemandApi+"patient/appointment/doctor/updateVisit/v3/";

    /**
     * Contact Lens Survey constant api calls
     * */

    ///DoctorOnDemand/api/patient/appointment/doctor/updateVisit/v3/
    //DoctorOnDemand/api/faq/getbyspecialization/EyeCare

    String surveyResultAppointment = DoctorOnDemandApi + Patient + Appointment + Doctor + UpdateVisitV3;
    String surveyResult = DoctorOnDemandApi + Patient + CreateContactLens;
    String surveySolutionImage = DoctorOnDemandApi + Patient + Appointment + UploadSolutionBottleImage;
    String frequentQuestions = DoctorOnDemandApi + faqGetBySpecialization;

    String signInEmail = DoctorOnDemand + "verifyOnBoardEmail/v3";
    String signInPhone = DoctorOnDemand + "verifyOnBoardPhone/v3";
    String signInGeneratePasswordAndEmail = DoctorOnDemand + "generatePasswordAndUpdateEmail/v3";
    String signInUpdateEmail = DoctorOnDemand + "updateEmail/v3";
    String signInGeneratePassword = DoctorOnDemand + "generatePassword/v3";
    String signInLogin = DoctorOnDemand + "login";
    String employerDetails = DoctorOnDemandAuthPatientUser + userID;
    String employerName = DoctorOnDemandAuthPatientUser + "listEmployerDetail/v3";
    String updateEmployerDetails = DoctorOnDemandAuthPatientUser + "updateEmployerDetail/v3";
    String removeStripeCustomer = "/DoctorOnDemand/api/patient/user/removeStripeCustomer";

    //============MAIN LINKS OF SERVER================================================================================

    /*public static final String homeUrlFormat = "https://%s:%s/"; //Prod
    public static final String DEV_PORT = "443";                     //SERVER
    public static final String DEV_IP = "prd.api.eyecarelive.com";*/

//    public static final String DEV_IP = "52.7.255.163";              // productionIP

    //=========================================DEMO LINKS===================================================================
    //https://dev.api.cooldoctors.io:8443
    public static final String homeUrlFormat = "https://%s:%s/";     //Demo
    public static final String DEV_PORT = "8443";                    //Demo
    public static final String DEV_IP = "dev.api.cooldoctors.io";                  //Demo
    // public static final String DEV_IP = "18.211.185.254";                  //Demo


}
