package com.cooldoctors.cdeye.apis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.constants.NetworkUtil;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;
import com.cooldoctors.cdeye.models.AboutMe;
import com.cooldoctors.cdeye.models.FCMTokenRegister;
import com.cooldoctors.cdeye.retrofit.RetrofitInterface;
import com.google.firebase.FirebaseApp;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFCMToken {

    private Context context;
    private KProgressHUD progressHUD;
    private static int versionCode = 0;
    private SharedPreference sharedPreference;
    private TokenRegisterSuccess tokenObj;
    //private AppCompactAct appCompactAct;

    public RegisterFCMToken(Context context, KProgressHUD progressHUD, TokenRegisterSuccess successObj) {
        this.context = context;
        this.progressHUD = progressHUD;
        versionCode = getApplicationVersion();
        sharedPreference = new SharedPreference(context);
        //appCompactAct = new AppCompactAct();
        tokenObj = successObj;
    }

    public void registerToken(Context context) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            UtilClass.okButtonDialog(context, context.getString(
                    R.string.alert), context.getString(R.string.noInternetAvailable));
            return;
        }

        if (progressHUD != null)
            progressHUD.show();

        String fcmToken = sharedPreference.getData(SharedPreference.shareKeyFCMToken);
        String authToken = sharedPreference.getData(SharedPreference.userToken);
        Log.d("Inside registerToken", "registerToken " + fcmToken + "    " + authToken);


        /*if (fcmToken == null || fcmToken.isEmpty() || authToken == null)
            return;*/

        //FirebaseApp.initializeApp(context);
        FCMTokenRegister fcmTokenRegister = getFCMParams(fcmToken);
        RetrofitInterface.getRetrofitClient()
                .registerFCMToken(authToken, fcmTokenRegister)
                .enqueue(new Callback<AboutMe>() {
                    @Override
                    public void onResponse(Call<AboutMe> call, Response<AboutMe> response) {
                        //Log.d("TAG", response.body().toString());
                        UtilClass.dismissProgressDialog(progressHUD);
                        if (response.body() != null) {
                            AboutMe aboutMe = response.body();
                            Log.d("TAG", "About me >> " + aboutMe.toString());
                            if (aboutMe != null && aboutMe.getId() != null) {
                                //UtilClass.dismissProgressDialog(progressHUD);
                                tokenObj.success();
                                Log.d("Register", "FCM token registered");
                            } else if (response.body() != null) {

                                Log.d("Register", "FCM token registered but about me not received .... ");
                                tokenObj.success();
                            }
                        } else {
                            UtilClass.dismissProgressDialog(progressHUD);
                            Log.d("Register", "FCM token registered but about me not received .... ");
                            tokenObj.success();
                        }
                    }

                    @Override
                    public void onFailure(Call<AboutMe> call, Throwable t) {
                        UtilClass.dismissProgressDialog(progressHUD);
                        UtilClass.okButtonDialog(context, "Error", t.getMessage());
                    }
                });
    }

    @NotNull
    private FCMTokenRegister getFCMParams(String fcmToken) {
        FCMTokenRegister fcmTokenRegister = new FCMTokenRegister();
        fcmTokenRegister.setDeviceToken(fcmToken);
        fcmTokenRegister.setPlatform("Android");
        fcmTokenRegister.setAppVersion(versionCode);
        fcmTokenRegister.setDeviceOSVersion(Build.VERSION.SDK_INT);
        fcmTokenRegister.setDeviceName(Build.MODEL);
        return fcmTokenRegister;
    }

    private int getApplicationVersion() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public interface TokenRegisterSuccess {
        void success();
    }

}
