package com.cooldoctors.cdeye.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.apis.RegisterFCMToken;
import com.cooldoctors.cdeye.constants.NetworkUtil;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;
import com.cooldoctors.cdeye.models.AboutMe;
import com.cooldoctors.cdeye.models.FCMTokenRegister;
import com.cooldoctors.cdeye.retrofit.HDMIDetectBR;
import com.cooldoctors.cdeye.retrofit.RetrofitInterface;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cooldoctors.cdeye.constants.UtilClass.okButtonDialog;
import static com.cooldoctors.cdeye.constants.UtilClass.setStatusBarGradiant;

public class MainActivity extends AppCompatActivity implements RegisterFCMToken.TokenRegisterSuccess{

    TextView tvHeader;
    ImageView ivMenuIcon, ivBackArrow;

    SharedPreference sharedPreference;
    Context context;
    KProgressHUD hud;

    LinearLayout llTwilio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHeader = findViewById(R.id.tvHeader);
        ivMenuIcon = findViewById(R.id.ivMenuIcon);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        llTwilio = findViewById(R.id.callingScreen);

        ivMenuIcon.setVisibility(View.VISIBLE);
        ivBackArrow.setVisibility(View.GONE);
        llTwilio.setVisibility(View.GONE);
        tvHeader.setText("Dashboard");

        context = this;

        setStatusBarGradiant(this);

        hud = UtilClass.initProgressDialog(context);

        sharedPreference = SharedPreference.getInstance(context);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        UtilClass.getFCMToken(sharedPreference, context);

        ivMenuIcon.setOnClickListener(view -> okButtonDialog2(context, context.getResources().getString(R.string.msg),
                context.getResources().getString(R.string.eyecare)));
        UtilClass.registerFCMToken(context, hud, this);
        requestNotificationPolicy();
        checkCameraAudioPermissions(this);

        Intent intent = new Intent(this, HDMIDetectBR.class);
        sendBroadcast(intent);
    }

    public void requestNotificationPolicy(){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            UtilClass.callActivity(intent,this);
        }
    }

    public void okButtonDialog2(Context context, String title, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok_cancel_button_bg_white);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogMessage = (TextView) dialog.findViewById(R.id.tvDialogMessage);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tvLogout);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);

        tvDialogTitle.setText(title);
        tvDialogTitle.setVisibility(View.GONE);
        tvDialogMessage.setText(message);
        tvDialogMessage.setTypeface(null, Typeface.BOLD);
        tvOk.setText("Sign Out");

        tvCancel.setOnClickListener(view -> dialog.dismiss());

        tvOk.setOnClickListener(v -> {
            dialog.dismiss();
            if (!NetworkUtil.isNetworkAvailable(context)) {
                okButtonDialog(context, context.getString(
                        R.string.alert), context.getString(R.string.noInternetAvailable));
                return;
            }
            removeFCMTokenKeyFromServer();
        });

        dialog.show();
    }

    private void removeFCMTokenKeyFromServer() {
        if (hud != null)
            hud.show();

        String fcmToken = sharedPreference.getData(SharedPreference.shareKeyFCMToken);
        String authToken = sharedPreference.getData(SharedPreference.userToken);

        if (fcmToken == null || authToken == null)
            return;

        FCMTokenRegister fcmTokenRegister = getFCMParams(fcmToken);
        RetrofitInterface.getRetrofitClient()
                .removeFCMToken(authToken, fcmTokenRegister)
                .enqueue(new Callback<AboutMe>() {
                    @Override
                    public void onResponse(Call<AboutMe> call, Response<AboutMe> response) {
                        UtilClass.dismissProgressDialog(hud);
                        AboutMe aboutMe = response.body();
                        if (aboutMe != null && aboutMe.getId() != null) {
                            removeSharedData();
                            Log.d("Register", "FCM token registered");
                        }
                    }

                    @Override
                    public void onFailure(Call<AboutMe> call, Throwable t) {
                        UtilClass.dismissProgressDialog(hud);
                        okButtonDialog(context, "Error", t.getMessage());
                    }
                });
    }

    private void removeSharedData() {
        sharedPreference.clearAllSharePreferenceData();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilClass.callActivity(intent,this);
//        startActivity(intent);
//        finish();
    }

    private FCMTokenRegister getFCMParams(String fcmToken) {
        FCMTokenRegister fcm = new FCMTokenRegister();
        fcm.setPlatform("Android");
        fcm.setDeviceToken(fcmToken);
        return fcm;
    }

    public boolean checkCameraAudioPermissions(Activity act) {
        int cameraPermission = ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA);
        int audioPermission = ContextCompat.checkSelfPermission(act, Manifest.permission.RECORD_AUDIO);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (audioPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(act, new String[listPermissionsNeeded.size()], 10);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == 10) {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        return;
                    } else {
                        //permission is denied (and never ask again is  check)    //shouldShowRequestPermissionRationale will return false
                            okButtonDialog(this, getResources().getString(R.string.error),
                                    "You need to give some mandatory permissions to continue. you have to go to app settings to allow them?");
                        }
                    }
            }
    }

    @Override
    public void success() {

    }
}
