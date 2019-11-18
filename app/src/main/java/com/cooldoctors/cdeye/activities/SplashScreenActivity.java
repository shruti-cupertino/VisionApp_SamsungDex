package com.cooldoctors.cdeye.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;

public class SplashScreenActivity extends AppCompatActivity {

    Context context;
    String TAG = "Inside SplashActivity";
    // Splash screen timer
    int SPLASH_TIME_OUT = 2000;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        Log.d(TAG, "onCreate");
        context = this;
        sharedPreference = SharedPreference.getInstance(context);

        new Handler().postDelayed(() -> {

            if (sharedPreference.getData(SharedPreference.userToken).equals("")) {
                Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UtilClass.callActivity(intent,this);
            } else {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UtilClass.callActivity(intent,this);

            }
        }, SPLASH_TIME_OUT);
    }

}
