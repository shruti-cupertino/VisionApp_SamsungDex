package com.cooldoctors.cdeye.services;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.constants.EnvironmentData;
import com.cooldoctors.cdeye.constants.NetworkUtil;
import com.cooldoctors.cdeye.constants.ServiceConstants;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.cooldoctors.cdeye.constants.ServiceConstants.homeUrlFormat;

/**
 * Created by Ulhas on 4/01/2019.
 */

public abstract class FragmentPostService extends AsyncTask<String, Void, String> implements ServiceConstants {
    public abstract void postResponseReceived(String result);

    private String TAG = "Inside FragmentPostService", response = "";
    private String baseUrl = String.format(homeUrlFormat,
            EnvironmentData.ENVIRONMENT_IP,
            EnvironmentData.ENVIRONMENT_PORT);
    private SharedPreference sharedPreference;
    private String httpsPostURL;
    KProgressHUD hud = null;
    String flag, JSONString;
    Activity activity;
    Context context;
    private URL url;

    public FragmentPostService(Context mContext, Activity activity, KProgressHUD kPHUD) {
        Log.d(TAG, "FragmentPostService");
        this.context = mContext;
        this.activity = activity;
        this.hud = kPHUD;
        sharedPreference = new SharedPreference(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground() strings        " + strings[0]);
        flag = strings[0];
        if (NetworkUtil.isNetworkAvailable(context)) {

            if (flag.equals("removeCard")) {

                //DoctorOnDemand/api/patient/user/insurencecard/
                httpsPostURL = baseUrl + DoctorOnDemandApi + Patient + User + insuraneCard + strings[1];
                JSONString = strings[2];

                parseDeleteMethod();
            } else {

                if (flag.equals("markPreferredDoctor")) {
                    ///DoctorOnDemand/api/patient/user/markPreferredDoctor/{DocotrID}
                    httpsPostURL = baseUrl + DoctorOnDemandAuthPatientUser + markPreferredDoc + strings[1];
                } else if (flag.equals("unmarkPreferredDoctor")) {
                    ///DoctorOnDemand/api/patient/user/unmarkPreferredDoctor/{DocotrID}
                    httpsPostURL = baseUrl + DoctorOnDemandAuthPatientUser + unmarkPreferredDoc + strings[1];
                } else if (flag.equals("unmarkPreferredEclClinic")) {
                    ///DoctorOnDemand/api/patient/eclClinic/unmarkPreferredEclClinic/{ClinicID}
                    httpsPostURL = baseUrl + DoctorOnDemandAuthPatientEclClinic + UnmarkPreferredEclClinic + strings[1];
                } else if (flag.equals("markPreferredEclClinic")) {
                    ///DoctorOnDemand/api/patient/eclClinic/markPreferredEclClinic/{ClinicID}
                    httpsPostURL = baseUrl + DoctorOnDemandAuthPatientEclClinic + MarkPreferredEclClinic + strings[1];
                } else if (flag.equals("preUpload")) {
                    ///DoctorToDoctor/api/patient/followup/uploadFollowUpMessageFilesInfoIndividual"        //Pre Upload
                    httpsPostURL = baseUrl + DoctorOnDemandApi + Patient + ServiceConstants.UploadFollowUpMessageFilesInfoIndividual;

                    JSONString = strings[1];
                    Log.d(TAG, "JSONString 2        " + JSONString);
                } else if (flag.equals("preGrpUpload")) {
                    ///DoctorToDoctor/api/patient/grpfollowup/uploadFollowUpMessageFilesInfoIndividual"        //Pre Upload
                    httpsPostURL = baseUrl + DoctorOnDemandApi + Patient + ServiceConstants.UploadGrpFollowUpMessageFilesInfoIndividual;

                    JSONString = strings[1];
                    Log.d(TAG, "JSONString 2        " + JSONString);
                } else if (flag.equals("assignTwilioTokenToPatient")) {
                    ////DoctorOnDemand/api/patient/appointment/assignTwilioTokenToPatient/{patientId}/{twilioRoom}        //assignTwilioTokenToPatient
                    httpsPostURL = baseUrl + DoctorOnDemandPatient + ServiceConstants.Appointment +
                    TwilioCreateToken + sharedPreference.getData(SharedPreference.userId) + "/" + sharedPreference.getData(SharedPreference.twilioRoomName);

//                    JSONString = strings[1];
                    Log.d(TAG, "JSONString 2        " + JSONString);
                }
                executePostWithNoDataService();
            }
        } else {
            response = context.getResources().getString(R.string.noInternetAvailable);
        }

        Log.d(TAG, "httpsPostURL 3        " + httpsPostURL);
        return response;
    }

    private void executePostWithNoDataService() {

        try {
            //SSLCertificateHandler.trust();
            String basicAuth = sharedPreference.getData(SharedPreference.userToken);
            url = new URL(httpsPostURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            // conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            Log.d(TAG, "Token        " + basicAuth);
            conn.setRequestProperty("X-Auth-Token", basicAuth);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (flag.equals("doctorinfo") || flag.equals("changepassword") || flag.equals("deleteUploadedFile") || flag.equals("preUpload") || flag.equals("preGrpUpload") || flag.equals("addSecondNote") || flag.equals("addNoteReply")) {
                OutputStream os = conn.getOutputStream();
                os.write(JSONString.getBytes("UTF-8"));
                os.flush();
                os.close();
                os.close();
            }
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d(TAG, "responseCode        " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else if (responseCode == 404 || responseCode == 503 || responseCode == 504) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.someIssueWithServer);
            } else if (responseCode == 401) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.sessionExpires);
            } else if (responseCode == 405) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.wrongMethodCall);
            } else {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.pleaseTryAgainLater);
            }

        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException      " + e.toString());
            response = context.getResources().getString(R.string.errorCode) + e.toString();
            e.printStackTrace();
            if (hud != null)
                hud.dismiss();
        } catch (IOException e) {
            Log.d(TAG, "IOException      " + e.toString());
            response = context.getResources().getString(R.string.errorCode) + e.toString();
            e.printStackTrace();
            if (hud != null)
                hud.dismiss();
        }
        Log.d(TAG, "response      " + response);
    }



    public String parseDeleteMethod() {

        try {
            //SSLCertificateHandler.trust();
            url = new URL(httpsPostURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(9000);
            // conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/json");


            conn.setRequestMethod("DELETE");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            if (httpsPostURL.contains(ServiceConstants.EXCLUDE_AUTHENTICATION)) {

            } else {
                conn.setRequestProperty("X-Auth-Token", sharedPreference.getData(SharedPreference.userToken));
            }

            OutputStream os = conn.getOutputStream();
            os.write(JSONString.getBytes("UTF-8"));

            os.flush();
            os.close();
            os.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.i(TAG, "Url:-" + url + "  ResponseCode:-" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else if (responseCode == 500 || responseCode == 404 || responseCode == 503 || responseCode == 504) {
                response = "There seems to be some issue with the server at the moment.";
            } else {
                response = "Error " + responseCode + " Occur";
            }
            //Log.d(TAG, "response= " + response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    private void executePUTService() {

        try {
            //SSLCertificateHandler.trust();
            url = new URL(httpsPostURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(18000);
            // conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("PUT");
            if (httpsPostURL.contains(ServiceConstants.EXCLUDE_AUTHENTICATION)) {

            } else {
                conn.setRequestProperty("X-Auth-Token", sharedPreference.getData(SharedPreference.userToken));
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(JSONString.getBytes("UTF-8"));
            os.flush();
            os.close();
            os.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            //Log.i(TAG, "Url:-" + urlObj + "  ResponseCode:-" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else if (responseCode == 404 || responseCode == 503 || responseCode == 504) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.someIssueWithServer);
            } else if (responseCode == 401) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.sessionExpires);
            } else {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.pleaseTryAgainLater);
            }
            //Log.d(TAG, "response= " + response);
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException      " + e.toString());
            response = context.getResources().getString(R.string.errorCode) + e.toString();
            e.printStackTrace();
            if (hud != null)
                hud.dismiss();
        } catch (IOException e) {
            Log.d(TAG, "IOException      " + e.toString());
            response = context.getResources().getString(R.string.errorCode) + e.toString();
            e.printStackTrace();
            if (hud != null)
                hud.dismiss();
        }
        Log.d(TAG, "response      " + response);
    }


    private void executePostService() {
        try {
            //SSLCertificateHandler.trust();
            url = new URL(httpsPostURL);
            Log.d(TAG, "httpsPOSTURL        " + httpsPostURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Connection", "Close");
            conn.setReadTimeout(20000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            String basicAuth = sharedPreference.getData(SharedPreference.userToken);
            if (!flag.equals("forgotPass") || !flag.equals("loginAPI")) {
                conn.setRequestProperty("X-Auth-Token", basicAuth);
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(JSONString.getBytes("UTF-8"));
            os.flush();
            os.close();
            os.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d(TAG, "responseCode      " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else if (responseCode == 404 || responseCode == 503 || responseCode == 504) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.someIssueWithServer);
            } else if (responseCode == 401) {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.sessionExpires);
            } else {
                response = context.getResources().getString(R.string.errorCode) + context.getResources().getString(R.string.pleaseTryAgainLater);
            }

        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException      " + e.toString());
            response = context.getResources().getString(R.string.errorCode) + e.toString();
            e.printStackTrace();
            if (hud != null)
                hud.dismiss();
        } catch (IOException e) {
            Log.d(TAG, "IOException      " + e.toString());
            response = context.getResources().getString(R.string.errorCode) + e.toString();
            e.printStackTrace();
            if (hud != null)
                hud.dismiss();
        }
        Log.d(TAG, "response      " + response);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecute() Result        " + result);
        if (result.contains(context.getResources().getString(R.string.noInternetAvailable))) {
            NetworkUtil.showToastMsg(context, context.getResources().getString(R.string.noInternetAvailable));
            if (hud != null)
                hud.dismiss();
        } else if (result.contains("java.net.ConnectException: failed to connect") || result.contains("java.net.SocketTimeoutException")) {
            if (flag.equals("addFirstNote") || flag.equals("addSecondNote") || flag.equals("addNoteReply")) {
                postResponseReceived(context.getResources().getString(R.string.noInternetAvailable));
            } else
                NetworkUtil.showToastMsg(context, context.getResources().getString(R.string.tryAgain));
            if (hud != null)
                hud.dismiss();
        } else {
            Object json = null;
            try {
                json = new JSONTokener(result).nextValue();
                if (json instanceof JSONObject) {
                    Log.d(TAG, "json instanceof JSONObject");
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("id") || jsonObject.has("success") || result.contains("true") && !result.contains(context.getResources().getString(R.string.errorCode))) {
                        Log.d(TAG, "jsonObject.has(\"success\")");
                        postResponseReceived(result);
                    } else {
                        if (jsonObject.has(context.getResources().getString(R.string.message))) {
                            postResponseReceived(jsonObject.getString("message"));
                        } else
                            postResponseReceived(result);
                    }
                } else if (json instanceof JSONArray) {
                    Log.d(TAG, "json instanceof JSONArray");
                    postResponseReceived(result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (result.contains(context.getResources().getString(R.string.errorCode))) {
                Log.d(TAG, "result.contains");
                postResponseReceived(result);
            }
        }
    }
}
