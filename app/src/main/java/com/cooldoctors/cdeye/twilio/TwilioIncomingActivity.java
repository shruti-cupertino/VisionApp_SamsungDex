package com.cooldoctors.cdeye.twilio;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;
import com.cooldoctors.cdeye.services.FireBaseMessagingService;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TwilioIncomingActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    RingToneBroadcast ringToneBroadcast;
    TextView tvDoctorName, tvConnecting;
    SharedPreference sharedPreference;
    String TAG = "Inside TwilioIncCall";
    LocalBroadcastManager localBroadcastManager;
    ImageView ivDocImg, ivAcceptCall, ivDeclineCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setFinishOnTouchOutside(false);
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setContentView(R.layout.twilio_incoming);
        window.setType(WindowManager.LayoutParams.TYPE_TOAST);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setLayout(MATCH_PARENT, MATCH_PARENT);
        window.setGravity(Gravity.CENTER_VERTICAL);

        context = this;
        sharedPreference = new SharedPreference(context);

        Toast.makeText(this, "In OnCreate of Incoming Activity", Toast.LENGTH_SHORT).show();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.EXTRA_KEY_EVENT);
        ringToneBroadcast = new RingToneBroadcast();
        registerReceiver(ringToneBroadcast, filter);

        Log.d(TAG, "onCreate()");

        tvDoctorName = (TextView) findViewById(R.id.tvDoctorName);
        tvConnecting = (TextView) findViewById(R.id.tvConnecting);

        ivDeclineCall = (ImageView) findViewById(R.id.ivDeclineCall);
        ivAcceptCall = (ImageView) findViewById(R.id.ivAcceptCall);
        ivDocImg = (ImageView) findViewById(R.id.ivDocImg);

        ivAcceptCall.setOnClickListener(this);
        ivDeclineCall.setOnClickListener(this);

        Log.d(TAG, "doctorTwilioName          " + sharedPreference.getData(SharedPreference.doctorTwilioName));
        tvDoctorName.setText(sharedPreference.getData(SharedPreference.doctorTwilioName));

        if (!sharedPreference.getData(SharedPreference.docURL).equals("")) {
            Picasso.get().load(sharedPreference.getData(SharedPreference.docURL)).into(ivDocImg);
        } else {
            ivDocImg.setImageResource(R.mipmap.default_doctor);
        }

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.cooldoctors.actioncall.close");
        mIntentFilter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION);
        localBroadcastManager.registerReceiver(closeTCallBroadReceiver, mIntentFilter);
    }

    BroadcastReceiver closeTCallBroadReceiver = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "mBroadcastReceiver intent          " + intent.getAction());

            if (Objects.equals(intent.getAction(), "twilio.call.close.if.notAnswered")) {
                Log.d(TAG, "finishing Activity from BroadcastReceiver");
                Toast.makeText(context, "finishing Activity from BroadcastReceiver", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };

    public static void cancelNotification(Context ctx, int notifyId) {
        if (ctx != null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
            if (nMgr != null)
                nMgr.cancel(notifyId);
            Toast.makeText(ctx, "On Cancel Notification", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivAcceptCall:
                try {
                    tvConnecting.setVisibility(View.VISIBLE);
                    FireBaseMessagingService.isUserOnCall = true;
                    FireBaseMessagingService.stopRingtone();
                    Log.d(TAG, "ivAcceptCall ringToneBroadcast        " + ringToneBroadcast);
                    Toast.makeText(context, "Accepted call", Toast.LENGTH_SHORT).show();
                    if (ringToneBroadcast != null)
                        unregisterReceiver(ringToneBroadcast);

                    cancelNotification(context, 101);

                    Intent intent = new Intent(TwilioIncomingActivity.this, TwilioMainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    UtilClass.callActivity(intent,this);
//                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.d(TAG, "Exception ivAcceptCall        " + e);
                }
                break;

            case R.id.ivDeclineCall:

                FireBaseMessagingService.stopRingtone();
                sharedPreference.saveData(SharedPreference.doctorTwilioName, "");
                sharedPreference.saveData(SharedPreference.docURL, "");
                sharedPreference.saveData(SharedPreference.docMessage, "");
                if (FireBaseMessagingService.notificationManager != null)
                    FireBaseMessagingService.notificationManager.cancel(101);
                if (closeTCallBroadReceiver != null)
                    unregisterReceiver(closeTCallBroadReceiver);
                //Log.i(TAG, "NotificationService2.callRingHandler.purge()");
                FireBaseMessagingService.isUserOnCall = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    this.finishAndRemoveTask();
                }
                FireBaseMessagingService.ringtone = null;
                Toast.makeText(context, "Call Declined", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (ringToneBroadcast != null)
                unregisterReceiver(ringToneBroadcast);
            Toast.makeText(context, "OnDestroy of Incoming Activity...", Toast.LENGTH_SHORT).show();
//            localBroadcastManager.unregisterReceiver(localBroadcastManager);
        } catch (Exception e) {
            Log.d(TAG, "onDestroy()Exception    " + e);
        }
    }
}
