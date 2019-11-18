package com.cooldoctors.cdeye.constants;


import android.content.Context;
import android.content.SharedPreferences;

/*
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
*/


public class SharedPreference {

    final String AppPreferenceName = "eyecareliveDataBase";
    private SharedPreferences sharedPreferences;
    static SharedPreference myAppPreference;

    //SharedPreferences Keys to Save Data

    public static String id = "Id";
    public static String userId = "userId";
    public static String userFName = "fName";
    public static String userLName = "lName";
    public static String userAddress = "address";
    public static String userToken = "userToken";
    public static String userName = "userName";
    public static String userPass = "userPassword";
    public static String appointment = "Appointment";


//    ==============GCM Shared==========================
    public static String twilioToken = "twilioToken";
    public static String docURL = "docURL";
    public static String docMessage = "docMessage";
    public static String doctorTwilioName = "doctorName";
    public static String twilioRoomName = "twilioRoomName";

    public static String shareKeyEyeCareDryData = "EyeCareDryData";
    public static String shareKeyEyeCareContactLensData = "EyeCareContactLensData";
    public static String shareKeyEyeCareVisionData = "EyeCareVisionData";
    public static String shareKeySelectedDocNameAppointment = "SelectedDoctorName";
    public static String shareKeyFCMToken = "FCMToken";



    public SharedPreference(Context context) {
        if (context != null)
            sharedPreferences = (SharedPreferences) context.getSharedPreferences(AppPreferenceName, Context.MODE_PRIVATE);
    }


    public static SharedPreference getInstance(Context context) {
        if (myAppPreference == null) {
            myAppPreference = new SharedPreference(context);
        }
        return myAppPreference;
    }

    //Save Boolean Data through these method
    public void saveBooleanData(String key, boolean value) {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    //Get Boolean Data through these method
    public boolean getBooleanData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    //Save String Data through these method
    public void saveData(String key, String value) {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    //Get String Data through these method
    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    //Save Integer Data through these method
    public void saveIntValue(String key, int value) {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    //Get Integer Data through these method
    public int getIntValue(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }

    //Save object data in shared preference
    /*public <T> void saveObject(String key, T obj){
        String data = new Gson().toJson(obj);
        sharedPreferences.edit().putString(key,data).apply();
    }

    //Get object data from shared preference
    public <T> ArrayList<T> getObject(String key){

        String details = sharedPreferences.getString(key,null);
        Type type = new TypeToken<ArrayList<T>>(){}.getType();
        if(details != null){
            return new Gson().fromJson(details,type);
        }
        return null;
    }*/

    //To check key is existing or not
    public boolean isKeyAvailable(String key){
        return sharedPreferences != null && sharedPreferences.contains(key);
    }

    //Clear specific data from SharePreference
    public void clearShareKeyData(String key) {
        if(key != null && isKeyAvailable(key)){
            sharedPreferences.edit().remove(key).apply();
        }
    }

    //Clear all share preference data
    public void clearAllSharePreferenceData(){
        if(sharedPreferences != null)
            sharedPreferences.edit().clear().apply();
    }
}
