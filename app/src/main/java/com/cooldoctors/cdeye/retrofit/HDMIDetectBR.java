package com.cooldoctors.cdeye.retrofit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class HDMIDetectBR extends BroadcastReceiver {

    private static String HDMIINTENT = "android.intent.action.HDMI_PLUGGED";

    @Override
    public void onReceive(Context ctxt, Intent receivedIt) {
        String action = receivedIt.getAction();

        if (action != null && action.equals(HDMIINTENT)) {
            boolean state = receivedIt.getBooleanExtra("state", false);

            if (state) {
                Log.d("HDMIListner", "BroadcastReceiver.onReceive() : Connected HDMI-TV");
                Toast.makeText(ctxt, "HDMI connected >>", Toast.LENGTH_LONG).show();
            } else {
                Log.d("HDMIListner", "HDMI >>: Disconnected HDMI-TV");
                Toast.makeText(ctxt, "HDMI DisConnected>>", Toast.LENGTH_LONG).show();
            }
        }
    }
}