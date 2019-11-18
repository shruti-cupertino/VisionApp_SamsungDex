package com.cooldoctors.cdeye.twilio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cooldoctors.cdeye.services.FireBaseMessagingService;

import java.util.Timer;
import java.util.TimerTask;

public class RingToneBroadcast extends BroadcastReceiver {
    private static int countPowerOff = 0;
    private boolean isUserOnCall = false;
    public static Timer callRingHandler = new Timer();
    String TAG = "Inside RingToneB";

    public RingToneBroadcast() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null)
            if (intent.getAction() != null)
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d(TAG, "In Method:  ACTION_SCREEN_OFF");
                    Log.d(TAG, "isUserOnCall              " + isUserOnCall);
                    if (!isUserOnCall) {
                        FireBaseMessagingService.stopRingtone();
                    }

                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d(TAG, "In Method:  ACTION_SCREEN_ON");
                    try {
                        isUserOnCall = false;
                        callRingHandler.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                //Log.i("Inside Broadcast", "dismissCallingScreen after :=");
                                isUserOnCall = true;
                            }
                        }, 30000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                    Log.d(TAG, "In Method:  ACTION_USER_PRESENT");
                } else if (intent.getAction().equals(Intent.EXTRA_KEY_EVENT)) {
                    Log.d(TAG, "EXTRA_KEY_EVENT");
                }
    }
}
