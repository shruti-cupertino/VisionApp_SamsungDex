package com.cooldoctors.cdeye.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.activities.MainActivity;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;
import com.cooldoctors.cdeye.models.AssignUser;
import com.cooldoctors.cdeye.models.User;
import com.cooldoctors.cdeye.twilio.TwilioIncomingActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.media.RingtoneManager.getDefaultUri;

public class FireBaseMessagingService extends FirebaseMessagingService {

    String notificationChannelID = "101";
    String TAG = "Inside FireBaseMsgS";
    SharedPreference sharedPreference;
    private long waitDelayAnswer = 30000;//30sec
    public static boolean isUserOnCall = false;
    public static Timer callRingHandler = new Timer();
    public static NotificationManager notificationManager;
    public static Ringtone ringtone;
    public static AudioManager audioManager;
    Context context;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        if (!token.isEmpty()) {
            Log.d(TAG, "Token.isEmpty       ");
            SharedPreference sharedPreference = new SharedPreference(this);
            sharedPreference.saveData(SharedPreference.shareKeyFCMToken, token);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Message data payload: On " + remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " +
                    remoteMessage.getNotification().getBody());
        }
        context = this;
        sharedPreference = new SharedPreference(context);
        Log.d(TAG, "onMessageReceived        " + remoteMessage.toString());
        checkMessageType(remoteMessage);
    }

    private void checkMessageType(RemoteMessage remoteMessage) {
        Intent intent;
        String messageType = remoteMessage.getData().get("messageType");
        assert messageType != null;
        switch (messageType) {
            case "TWILIOCALL": {

                Log.d(TAG, "doctorImageUrl        " + remoteMessage.getData().get("doctorImageUrl"));
                Log.d(TAG, "onMessageReceived        " + remoteMessage.getData().get("DOCTOR_NAME"));
                Log.d(TAG, "DOCTOR_NAME        " + remoteMessage.getData().get("message"));
                Log.d(TAG, "twilioRoom        " + remoteMessage.getData().get("twilioRoom"));
                sharedPreference.saveData(SharedPreference.docURL, remoteMessage.getData().get("doctorImageUrl"));
                sharedPreference.saveData(SharedPreference.doctorTwilioName, remoteMessage.getData().get("message"));
                sharedPreference.saveData(SharedPreference.docMessage, remoteMessage.getData().get("message"));
                sharedPreference.saveData(SharedPreference.twilioRoomName, remoteMessage.getData().get("twilioRoom"));


                @SuppressLint("StaticFieldLeak") FragmentPostService fragmentPostService = new FragmentPostService(context, null, null) {
                    @Override
                    public void postResponseReceived(String result) {
                        Log.d(TAG, "postResponseReceived       " + result);

                        try {
                            //Log.i(TAG, "processFinish      " + result);
                            Gson gson = new Gson();
                            User user = gson.fromJson(result, User.class);
                            AssignUser assignUser = AssignUser.getInstance();
                            assignUser.setUser(user);

                            String jsonAppointment = gson.toJson(user);
                            sharedPreference.saveData(SharedPreference.appointment, jsonAppointment);

                            if (user != null) {
                                if (user.getTwilioAccessToken() != null) {
                                    Log.i(TAG, "user.getTwilioAccessToken()      " + user.getTwilioAccessToken());
                                    sharedPreference.saveData(SharedPreference.twilioToken,
                                            user.getTwilioAccessToken());
                                }
                            }
                            Log.i(TAG, "intent to sendNotif");
                            playRingtoneOfTwilio();
                            startCallNotAnswer(101);
                            showCallingScreen();
                        } catch (Exception e) {
                            Log.i(TAG, "Twilio Exception      " + e);
                            e.printStackTrace();
                        }

                    }
                };

                fragmentPostService.execute("assignTwilioTokenToPatient");

                break;
            }
            case "ADDTESTREPORT":
            case "ADDTREATMENTPLAN":
            case "NOTIFICATIONFORPROFILEUPDATE":
            case "REMINDERFORMEDICINE":
            case "REMINDERFORVISIT":
            case "SENTMESSAGE":
            case "CANCELVISIT":
            case "ENDVISIT": {
                intent = new Intent(this, MainActivity.class);
                sendNotification(remoteMessage, intent);
                break;
            }
        }
    }

    private void playRingtoneOfTwilio() {
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_RINGTONE);
        int previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        ringtone = RingtoneManager.getRingtone(context, getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        if (ringtone != null) {
            try {
                if (audioManager.getRingerMode() == 2) {
                    Log.i(TAG, "RingerMode is normal:- " + audioManager.getRingerMode());
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, previousVolume, 0);
                } else if (audioManager.getRingerMode() == 1) {
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, previousVolume, AudioManager.FLAG_VIBRATE + AudioManager.FLAG_SHOW_UI);
                } else if (audioManager.getRingerMode() == 0) {
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, previousVolume, AudioManager.FLAG_VIBRATE + AudioManager.FLAG_SHOW_UI);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            try {
                ringtone.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showCallingScreen() {

        Handler uiHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context,
                        TwilioIncomingActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("isFromNotify", true);
                UtilClass.callActivity(intent,context);
            }
        };
        uiHandler.postDelayed(myRunnable, 1000);
    }

    private void startCallNotAnswer(final int notificationId) {
        try {
            callRingHandler.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "dismissCallingScreen after :=" + waitDelayAnswer);
                    stopRingtone();
                    isUserOnCall = false;
                    if (notificationManager != null)
                        notificationManager.cancel(notificationId);

                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                            .getInstance(context);
                    localBroadcastManager.sendBroadcast(new Intent(
                            "twilio.call.close.if.notAnswered"));
                }
            }, waitDelayAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopRingtone() {
        if (ringtone != null) {
            if (ringtone.isPlaying()) {
                try {
                    ringtone.stop();
                    ringtone = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void sendNotification(RemoteMessage remoteMessage, Intent intent) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "");

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt(50000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel adminChannel = setupChannels();
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(adminChannel);
                notificationBuilder.setChannelId(notificationChannelID);
            }
        }

        notificationBuilder.setContentTitle(getString(R.string.app_name).toUpperCase())
                .setContentText(remoteMessage.getData().get("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel setupChannels() {
        CharSequence adminChannelName = getString(R.string.default_notification_channel_id);

        NotificationChannel adminChannel = new NotificationChannel(notificationChannelID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription("FCM body");
        adminChannel.enableLights(true);
        adminChannel.enableVibration(true);

        return adminChannel;
    }
}