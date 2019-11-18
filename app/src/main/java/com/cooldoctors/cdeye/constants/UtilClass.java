package com.cooldoctors.cdeye.constants;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.apis.RegisterFCMToken;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kaopiz.kprogresshud.KProgressHUD;

import okhttp3.internal.Util;

public class UtilClass {


    public static KProgressHUD initProgressDialog(Context context) {
        KProgressHUD hud = null;
        if (context != null) {
            hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f)
                    .setLabel(context.getResources().getString(R.string.pleaseWait))
                    .setCancellable(false);
        }
        return hud;
    }

    public static void dismissProgressDialog(KProgressHUD progressHUD) {
        if (progressHUD != null && progressHUD.isShowing())
            progressHUD.dismiss();
    }

    public static void getFCMToken(SharedPreference sharedPreference, Context context) {
        String token = "";
        Log.d("Inside getFCMToken", "FCM ");
        FirebaseApp.initializeApp(context);
        try {
            if (sharedPreference.isKeyAvailable(SharedPreference.shareKeyFCMToken)) {
                token = sharedPreference.getData(SharedPreference.shareKeyFCMToken);
                Log.d("Inside isKeyAvailable", "FCM " + token);

            } else {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(task -> {
                    if (task.getToken().isEmpty()) {
                        Log.w("FCM token fail", "FCM token fail" + task.getToken());
                        return;
                    }
                    sharedPreference.saveData(SharedPreference.shareKeyFCMToken, task.getToken());
                    Log.d("FCM Token", task.getToken());
                });
            }
        } catch (Exception e) {
            Log.d("FCM Exception", "Exception " + e);
            e.printStackTrace();
        }
    }

    public static void registerFCMToken(Context context, KProgressHUD hud, RegisterFCMToken.TokenRegisterSuccess activity) {
        RegisterFCMToken registerFCMToken = new RegisterFCMToken(context, hud, activity);
        registerFCMToken.registerToken(context);
    }

    public static void okButtonDialog(final Context context, String title, final String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ok_button_with_white_bg);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogMessage = (TextView) dialog.findViewById(R.id.tvDialogMessage);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);

        tvDialogTitle.setText("Alert");

        if (message.contains(context.getResources().getString(R.string.errorCode))) {
            tvDialogMessage.setText(message.substring(13, message.length()));
        } else
            tvDialogMessage.setText(message);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (message.equals("Error Occur :Session expired, please login again.")) {
                    // doLogout(context);
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        Window window = activity.getWindow();
        Drawable background = activity.getResources().getDrawable(R.drawable.two_color_dashboard);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
    }

    //get window display size
    private static Display getWindowSize(Context context){
        DisplayManager dm = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = dm.getDisplays("com.samsung.android.hardware.display.category.DESKTOP");
        return  displays[0];
    }

    private static Display getPhoneSize(Context context){
        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        return dm.getDisplay(Display.DEFAULT_DISPLAY);
    }

    private static boolean isAppCurrentlyRunningOnDeX(Context context) {
        Configuration config = context.getResources().getConfiguration();
        try {
            Class configClass = config.getClass();
            if (configClass.getField("SEM_DESKTOP_MODE_ENABLED").getInt(configClass) == configClass.getField("semDesktopModeEnabled").getInt(config)) {
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public static void callActivity(Intent intent, Context context){
        ActivityOptions activityOptions = ActivityOptions.makeBasic();

        if(UtilClass.isAppCurrentlyRunningOnDeX(context)){
            activityOptions.setLaunchDisplayId(UtilClass.getWindowSize(context).getDisplayId());
            context.startActivity(intent,activityOptions.toBundle());
        }else {
            activityOptions.setLaunchDisplayId(UtilClass.getPhoneSize(context).getDisplayId());
            context.startActivity(intent,activityOptions.toBundle());
        }

    }
}
