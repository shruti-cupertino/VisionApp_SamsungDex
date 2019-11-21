package com.cooldoctors.cdeye.twilio;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;
import com.cooldoctors.cdeye.services.FireBaseMessagingService;
import com.google.android.material.appbar.AppBarLayout;
import com.twilio.video.CameraCapturer;
import com.twilio.video.ConnectOptions;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.Room;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoRenderer;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class TwilioMainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    ToggleButton ivTwilioSwitchCamera, ivTwilioVideo, ivTwilioSwitchView, ivTwilioAudio;
    boolean previousMicrophoneMute, disconnectedFromOnDestroy;
    VideoView primaryVideoView, localVideoView;
    VideoRenderer localVideoViewRenderer;
    String TAG = "Inside TwilioMainAct";
    SharedPreference sharedPreference;
    LocalParticipant localParticipant;
    int cameraAudioPermission = 10;
    TwilioCameraCapturer cameraCapturer;
    Timer callHandler = new Timer();
    String roomName, twilioToken;
    private int previousAudioMode;
    LocalVideoTrack localVideoTrack;
    LocalAudioTrack localAudioTrack;
    private long delay = 30000;
    String participantIdentity;
    VideoTrack tempVideoTrack;
    AudioManager audioManager;
    LinearLayout llTwilio, llView;
    ImageView ivHangUp;
    TextView tvStatus;
    Context context;
    Room room;
    ImageView imageView;
    TextView tvMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        context = this;
        sharedPreference = new SharedPreference(context);

        ivTwilioSwitchCamera = (ToggleButton) findViewById(R.id.ivTwilioSwitchCamera);
        ivTwilioSwitchView = (ToggleButton) findViewById(R.id.ivTwilioSwitchView);
        ivTwilioVideo = (ToggleButton) findViewById(R.id.ivTwilioVideo);
        ivTwilioAudio = (ToggleButton) findViewById(R.id.ivTwilioAudio);

        ivHangUp = (ImageView) findViewById(R.id.ivHangUp);

        ivTwilioSwitchCamera.setOnCheckedChangeListener(this);
        ivTwilioSwitchView.setOnCheckedChangeListener(this);
        ivTwilioVideo.setOnCheckedChangeListener(this);
        ivTwilioAudio.setOnCheckedChangeListener(this);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvStatus.setVisibility(View.GONE);

        ivHangUp.setOnClickListener(this);

        primaryVideoView = (VideoView) findViewById(R.id.primaryVideoView);
        localVideoView = (VideoView) findViewById(R.id.localVideoView);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        previousAudioMode = audioManager.getMode();

        llTwilio = (LinearLayout) findViewById(R.id.callingScreen);
        imageView = findViewById(R.id.imageView);
        tvMessage = findViewById(R.id.tvMessage);

        llTwilio.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);

        roomName = sharedPreference.getData(SharedPreference.twilioRoomName);
        twilioToken = sharedPreference.getData(SharedPreference.twilioToken);

        Toast.makeText(context, "In OnCreate of Twilio Main Activity", Toast.LENGTH_SHORT).show();

        if (checkCameraAudioPermissions(this)) {
            Log.d(TAG, "checkCameraAudioPermissions");
            llTwilio.setVisibility(View.VISIBLE);
            createAudioAndVideoTracks();
            connectToRoom(sharedPreference.getData(SharedPreference.twilioRoomName));
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setViewEditable(false);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
    }

    public void setViewEditable(boolean isEnable) {
        ivTwilioSwitchCamera.setEnabled(isEnable);
        ivTwilioSwitchView.setEnabled(isEnable);
        ivTwilioVideo.setEnabled(isEnable);
        ivTwilioAudio.setEnabled(isEnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivHangUp:
                Log.d(TAG, "ivHangUp");
                try {
                    setDisconnectAction();
                    FireBaseMessagingService.isUserOnCall = false;
                    Toast.makeText(context, "Call hanged up", Toast.LENGTH_SHORT).show();
                    llTwilio.setVisibility(View.GONE);
                    TwilioMainActivity.this.finish();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.d(TAG, "ivHangUp Exception         " + ex.toString());
                    okButtonDialog(context, "Alert", ex.toString());
                    Toast.makeText(context, "Exception in call hangup >> " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
        Log.d(TAG, "localVideoTrack     " + localVideoTrack);

        if (localVideoTrack == null && checkCameraAudioPermissions(this)) {
            if (cameraCapturer != null) {
                localVideoTrack = LocalVideoTrack.create(this, true, cameraCapturer.getVideoCapturer());
                localVideoTrack.addRenderer(localVideoViewRenderer);
                Toast.makeText(context, "checking permissions", Toast.LENGTH_SHORT).show();
            }
            /*
             * If connected to a Room then share the local video track.
             */
            //Log.d(TAG, "localParticipant " + localParticipant);
            if (localParticipant != null) {
                //Log.d(TAG, "onResume publishTrack          " + localVideoTrack);
                localParticipant.publishTrack(localVideoTrack);
                Toast.makeText(context, "on publish track", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void connectToRoom(String roomName) {
        Log.d(TAG, " connectToRoom =" + roomName);
        configureAudio(true);

        ConnectOptions connectOptions = new ConnectOptions.Builder(twilioToken)
                .roomName(roomName)
                .audioTracks(Collections.singletonList(localAudioTrack))
                .videoTracks(Collections.singletonList(localVideoTrack))
                .build();

        room = Video.connect(this, connectOptions, new Room.Listener() {

            @Override
            public void onConnected(@NonNull Room room) {
                try {

                    Log.d(TAG, "Room.Listener onConnected2");
                    // Get the first participant from the room
                    if (room.getRemoteParticipants() != null && room.getRemoteParticipants().size() > 0) {
                        RemoteParticipant participant = room.getRemoteParticipants().get(0);
                        Log.i(TAG, "HandleParticipants         " + participant.getIdentity() + " is in the room.");
                        Toast.makeText(TwilioMainActivity.this, "OnConnected in room", Toast.LENGTH_SHORT).show();
                        for (RemoteParticipant remoteParticipant : room.getRemoteParticipants()) {
                            remoteParticipant.setListener(remoteParticipantListener());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }

            }

            @Override
            public void onConnectFailure(@NonNull Room room, @NonNull TwilioException twilioException) {
                try {
                    tvStatus.setText("Failed to connect room");
                    Log.d(TAG, "onConnectFailure");
                    Toast.makeText(TwilioMainActivity.this, "onConnectFailure", Toast.LENGTH_SHORT).show();
                    configureAudio(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }
            }

            @Override
            public void onReconnecting(@NonNull Room room, @NonNull TwilioException twilioException) {
                try {
                    Log.d(TAG, "Room.Listener onReconnecting     " + room);
                    Toast.makeText(TwilioMainActivity.this, "on Reconnecting room", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }

            }

            @Override
            public void onReconnected(@NonNull Room room) {
                try {
                    Log.d(TAG, "Room.Listener onReconnected     " + room);
                    Toast.makeText(TwilioMainActivity.this, "on Reconnected room", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }

            }

            @Override
            public void onDisconnected(@NonNull Room room, @Nullable TwilioException twilioException) {
                try {
                    Log.d(TAG, "Room.Listener onDisconnected twilioException      " + twilioException);
                    localParticipant = null;
                    //Log.d(TAG, "onParticipantDisconnected");
                    tvStatus.setText("Disconnected from " + room.getName());
                    Toast.makeText(TwilioMainActivity.this, "onDisconnected from room", Toast.LENGTH_SHORT).show();
                    TwilioMainActivity.this.room = null;
                    if (!disconnectedFromOnDestroy) {
                        configureAudio(false);
                        moveLocalVideoToPrimaryView();
                    }

                    // Release the audio track to free native memory resources
                    if (localAudioTrack != null)
                        localAudioTrack.release();

                    // Release the video track to free native memory resources
                    if (localVideoTrack != null)
                        localVideoTrack.release();

                    // Release the video track to free native memory resources
                    if (primaryVideoView != null)
                        primaryVideoView.release();

                    ivHangUp.performClick();
                    room.disconnect();
                    room.disconnect();
                    room.disconnect();
                    room.disconnect();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.d(TAG, " Exception         " + ex.toString());
                    okButtonDialog(context, "Alert", ex.toString());
                    Toast.makeText(TwilioMainActivity.this, "Error in disconnecting >> " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onParticipantConnected(@NonNull Room room, @NonNull RemoteParticipant remoteParticipant) {
                try {
                    Log.d(TAG, "Room.Listener onParticipantConnected getName       " + room.getName());
                    Log.d(TAG, "Room.Listener onParticipantConnected getRemoteVideoTracks       " + remoteParticipant.getRemoteVideoTracks());
                    Toast.makeText(TwilioMainActivity.this, "onParticipantConnected", Toast.LENGTH_SHORT).show();
                    remoteParticipant.setListener(remoteParticipantListener());
                } catch (Exception e) {
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }
            }

            @Override
            public void onParticipantDisconnected(@NonNull Room room, @NonNull RemoteParticipant remoteParticipant) {
                try {
                    Log.d(TAG, "Room.Listener onParticipantDisconnected getName       " + room.getName());
                    Log.d(TAG, "Room.Listener onParticipantDisconnected getRemoteVideoTracks       " + remoteParticipant.getRemoteVideoTracks());
//                removeParticipant(remoteParticipant);
                    Log.d(TAG, "onParticipantDisconnected");
                    Toast.makeText(TwilioMainActivity.this, "onParticipantDisconnected", Toast.LENGTH_SHORT).show();
                    hangUpAction(true);
                } catch (Exception e){
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }
            }

            @Override
            public void onRecordingStarted(@NonNull Room room) {
                try {
                    Toast.makeText(TwilioMainActivity.this, "onRecording Started", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Room.Listener onRecordingStarted          " + room);
                } catch (Exception e){
                    e.printStackTrace();
                    okButtonDialog(context, "Alert", e.toString());
                }

            }

            @Override
            public void onRecordingStopped(@NonNull Room room) {
                try {
                    Log.d(TAG, "Room.Listener onRecordingStopped      " + room);
                    Toast.makeText(TwilioMainActivity.this, "onRecordingStopped", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    okButtonDialog(context, "Alert", e.toString());
                }

            }
        });

        //Log.d(TAG, "connectToRoom localParticipant " + localParticipant);
        if (localParticipant != null) {
            try {
                Log.d(TAG, "Room.Listener createAudioAndVideoTracks publishTrack          " + localVideoTrack);
                Toast.makeText(this, "Audio and Video tracks created", Toast.LENGTH_SHORT).show();
                localParticipant.publishTrack(localVideoTrack);
            } catch (Exception e){
                e.printStackTrace();
                okButtonDialog(context, "Alert", e.toString());
            }
        }
    }

    private RemoteParticipant.Listener remoteParticipantListener() {
        return new RemoteParticipant.Listener() {
            @Override
            public void onAudioTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteAudioTrackPublication remoteAudioTrackPublication) {
            }

            @Override
            public void onAudioTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteAudioTrackPublication remoteAudioTrackPublication) {
            }

            @Override
            public void onDataTrackPublished(RemoteParticipant remoteParticipant,
                                             RemoteDataTrackPublication remoteDataTrackPublication) {
            }

            @Override
            public void onDataTrackUnpublished(RemoteParticipant remoteParticipant,
                                               RemoteDataTrackPublication remoteDataTrackPublication) {
            }

            @Override
            public void onVideoTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteVideoTrackPublication remoteVideoTrackPublication) {
            }

            @Override
            public void onVideoTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteVideoTrackPublication remoteVideoTrackPublication) {
            }

            @Override
            public void onAudioTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteAudioTrackPublication remoteAudioTrackPublication,
                                               RemoteAudioTrack remoteAudioTrack) {
            }

            @Override
            public void onAudioTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                 RemoteAudioTrack remoteAudioTrack) {
            }

            @Override
            public void onAudioTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                       TwilioException twilioException) {
            }

            @Override
            public void onDataTrackSubscribed(final RemoteParticipant remoteParticipant,
                                              RemoteDataTrackPublication remoteDataTrackPublication,
                                              final RemoteDataTrack remoteDataTrack) {
                /*
                 * Data track messages are received on the thread that calls setListener. Post the
                 * invocation of setting the listener onto our dedicated data track message thread.
                 */
            }

            @Override
            public void onDataTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                RemoteDataTrackPublication remoteDataTrackPublication,
                                                RemoteDataTrack remoteDataTrack) {
            }

            @Override
            public void onDataTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                      RemoteDataTrackPublication remoteDataTrackPublication,
                                                      TwilioException twilioException) {
            }

            @Override
            public void onVideoTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteVideoTrackPublication remoteVideoTrackPublication,
                                               RemoteVideoTrack remoteVideoTrack) {
                Log.d(TAG, "onParticipantConnected onVideoTrackSubscribed");
                addParticipant2(remoteVideoTrack);
                setViewEditable(true);
            }

            @Override
            public void onVideoTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                 RemoteVideoTrack remoteVideoTrack) {
            }

            @Override
            public void onVideoTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                       TwilioException twilioException) {
            }

            @Override
            public void onAudioTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteAudioTrackPublication remoteAudioTrackPublication) {
            }

            @Override
            public void onAudioTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onVideoTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }

            @Override
            public void onVideoTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }
        };
    }

    private void setNormalAudio() {
        previousAudioMode = audioManager.getMode();
        //Log.d(TAG, " previousAudioMode =" + previousAudioMode);
        audioManager.setMode(AudioManager.MODE_NORMAL);
        Toast.makeText(context, "Normal audio set", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "Disconnected room normal audio set");
        //Jagdevi code
        try {
            audioManager.setSpeakerphoneOn(true);
        } catch (Exception e) {
            e.printStackTrace();
            okButtonDialog(context, "Alert", e.toString());
            Toast.makeText(context, "Error in setting audio to normal >> " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void configureAudio(boolean enable) {
        Log.d(TAG, " configureAudio =" + enable);
        Toast.makeText(context, "Audio Configured", Toast.LENGTH_SHORT).show();
        if (enable) {
            previousAudioMode = audioManager.getMode();
            // Request audio focus before making any device switch
            // requestAudioFocus();
            /*
             * Use MODE_IN_COMMUNICATION as the default audio mode. It is required
             * to be in this mode when playout and/or recording starts for the best
             * possible VoIP performance. Some devices have difficulties with
             * speaker mode if this is not set.
             */

            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            /*
             * Always disable microphone mute during a WebRTC call.
             */
            //Log.d(TAG, " previousAudioMode while call set =" + previousAudioMode);
            //Log.d(TAG, "setMode==MODE_IN_COMMUNICATION===" + AudioManager.MODE_IN_COMMUNICATION);
            try {
                audioManager.setSpeakerphoneOn(true);
            } catch (Exception e) {
                e.printStackTrace();
                okButtonDialog(context, "Alert", e.toString());
                Toast.makeText(context, "Error in audio configuration >> " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            previousMicrophoneMute = audioManager.isMicrophoneMute();
            //Log.d(TAG, " previousMicrophoneMute =" + previousMicrophoneMute);

            audioManager.setMicrophoneMute(false);
        } else {
            audioManager.setMode(previousAudioMode);
            audioManager.abandonAudioFocus(null);
            audioManager.setMicrophoneMute(previousMicrophoneMute);
            //Log.d(TAG, "  previousAudioMode while call set =" + previousAudioMode);
            //Log.d(TAG, " previousMicrophoneMute =" + previousMicrophoneMute);
            //Log.d(TAG, "setMode==MODE_IN_COMMUNICATION===" + AudioManager.MODE_IN_COMMUNICATION);
        }
    }


    private void setDisconnectAction() {
        if (room != null) {
            try {
                setNormalAudio();
                room.disconnect();
                room.disconnect();
                room.disconnect();
                room.disconnect();
                Log.d(TAG, "Disconnected room ");
                Toast.makeText(context, "Disconnected from room", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
                okButtonDialog(context, "Alert", e.toString());
            }
        }
    }

    private void hangUpAction(boolean isFromRemote) {
        try {
            setDisconnectAction();
            FireBaseMessagingService.isUserOnCall = false;
            Toast.makeText(context, "Call hanged up", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(TwilioMainActivity.this, CallThankUActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                    Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
            okButtonDialog(context, "Alert", ex.toString());
            Toast.makeText(context, "Error in call hangup >> " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addParticipant2(RemoteVideoTrack remoteVideoTrack) {
        try {
            primaryVideoView.setMirror(false);
            remoteVideoTrack.addRenderer(primaryVideoView);
            tempVideoTrack = remoteVideoTrack;
        } catch (Exception e){
            e.printStackTrace();
            okButtonDialog(context, "Alert", e.toString());
        }
    }

    private void moveLocalVideoToPrimaryView() {
        try {
            if (localVideoView.getVisibility() == View.VISIBLE) {
                localVideoView.setVisibility(View.GONE);
                if (localVideoTrack != null) {
                    localVideoTrack.removeRenderer(primaryVideoView);
                    localVideoTrack.addRenderer(localVideoView);
                }
                localVideoViewRenderer = primaryVideoView;
                primaryVideoView.setMirror(cameraCapturer.getCameraSource() ==
                        CameraCapturer.CameraSource.FRONT_CAMERA);
                Toast.makeText(context, "Local video...", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
            okButtonDialog(context, "Alert", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */
        //Log.d(TAG, "onDestroy");
        if (room != null && room.getState() != Room.State.DISCONNECTED) {
            try {
                room.disconnect();
                disconnectedFromOnDestroy = true;
                Toast.makeText(context, "OnDestroy room disconnected", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
                okButtonDialog(context, "Alert", e.toString());
            }
        }

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */
        if (localAudioTrack != null) {
            try {
                localAudioTrack.release();
                Toast.makeText(context, "onDestroy audio track released", Toast.LENGTH_SHORT).show();
                localAudioTrack = null;
            } catch (Exception e){
                e.printStackTrace();
                okButtonDialog(context, "Alert", e.toString());
            }
        }
        if (localVideoTrack != null) {
            try {
                localVideoTrack.release();
                Toast.makeText(context, "onDestroy video track released", Toast.LENGTH_SHORT).show();
                localVideoTrack = null;
            } catch (Exception e){
                e.printStackTrace();
                okButtonDialog(context, "Alert", e.toString());
            }
        }

        super.onDestroy();
    }


    public boolean checkCameraAudioPermissions(Activity act) {
        Log.d(TAG, "checkCameraAudioPermissions");
        int cameraPermission = ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA);
        int audioPermission = ContextCompat.checkSelfPermission(act, Manifest.permission.RECORD_AUDIO);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Toast.makeText(act, "Checking Permission", Toast.LENGTH_SHORT).show();

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (audioPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            Log.d(TAG, "listPermissionsNeeded.isEmpty()");
            Toast.makeText(act, "ListPermissionNeeded is empty", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(act, listPermissionsNeeded.toArray(new String[0]), cameraAudioPermission);
            return false;
        }
        return true;
    }


    private void createAudioAndVideoTracks() {
        try {
            Log.d(TAG, "createAudioAndVideoTracks");
            //Log.e(TAG, "createAudioAndVideoTracks");
            localAudioTrack = LocalAudioTrack.create(this, true);
            cameraCapturer = new TwilioCameraCapturer(this, getAvailableCameraSource());
            localVideoTrack = LocalVideoTrack.create(this, true, cameraCapturer.getVideoCapturer());
            Log.d(TAG, "localVideoTrack         " + localVideoTrack);
            primaryVideoView.setMirror(true);
            localVideoTrack.addRenderer(localVideoView);
//            localVideoViewRenderer = primaryVideoView;
            Log.d(TAG, "localVideoViewRenderer          " + localVideoViewRenderer);
            Toast.makeText(context, "Local audio and video tracks created", Toast.LENGTH_SHORT).show();
            // connectToRoom(roomName);

        } catch (Exception e) {
            Log.d(TAG, "createAudioAndVideoTracks e         " + e);
            e.printStackTrace();
            okButtonDialog(context, "Alert", e.toString());
            Toast.makeText(context, "Error in creating audio/video tracks >> " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public int getNumberOfCameras() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) try {
            return ((CameraManager) getSystemService(Context.CAMERA_SERVICE)).getCameraIdList().length;
        } catch (CameraAccessException e) {
            //Log.e(TAG, "CameraAccessException", e);
            e.printStackTrace();
            okButtonDialog(context, "Alert", e.toString());
        }
        return android.hardware.Camera.getNumberOfCameras();
    }

    public static boolean checkCameraFront(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return true;
        } else {
            return false;
        }
    }

    private CameraCapturer.CameraSource getAvailableCameraSource() {
        int numCameras = getNumberOfCameras();
        //Log.e(TAG, "getAvailableCameraSource numOfCamera=" + numCameras);
        if (numCameras > 0 && checkCameraFront(this)) {
            return CameraCapturer.CameraSource.FRONT_CAMERA;
        } else {
            return CameraCapturer.CameraSource.BACK_CAMERA;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == cameraAudioPermission) {
            boolean cameraAndMicPermissionGranted = false;
            //Log.d(TAG, "onRequestPermissionsResult cameraAndMicPermissionGranted    ");

            for (int grantResult : grantResults) {
                cameraAndMicPermissionGranted = (grantResult == PackageManager.PERMISSION_GRANTED);
            }

            if (cameraAndMicPermissionGranted) {
                Log.d(TAG, "cameraAndMicPermissionGranted");
                createAudioAndVideoTracks();
                connectToRoom(roomName);
            } else {
                Log.d(TAG, "onRequestPermissionsResult Else");
                UtilClass.okButtonDialog(context, context.getResources().getString(R.string.permissionNeeded),
                        context.getResources().getString(R.string.allUsPermission));
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

            case R.id.ivTwilioSwitchCamera:
                Log.d(TAG, "ivTwilioSwitchCamera     " + isChecked);
                Toast.makeText(context, "Camera switched", Toast.LENGTH_SHORT).show();
                try {
                    if (cameraCapturer != null) {
                        CameraCapturer.CameraSource cameraSource = cameraCapturer.getCameraSource();
                        cameraCapturer.switchCamera();
                        if (localVideoView.getVisibility() == View.VISIBLE) {
                            localVideoView.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
                        } else {
                            primaryVideoView.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    okButtonDialog(context, "Alert", ex.toString());
                    Toast.makeText(context, "Error in camera switch >> " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivTwilioSwitchView:
                Log.d(TAG, "ivTwilioSwitchView     " + isChecked);
                Toast.makeText(context, "View Switched", Toast.LENGTH_SHORT).show();
                try {
                    if (localVideoTrack != null && tempVideoTrack != null) {
                        if (isChecked) {
                            localVideoView.setVisibility(View.VISIBLE);
                            localVideoView.setMirror(false);
                            primaryVideoView.setMirror(true);
                            localVideoTrack.removeRenderer(localVideoView);
                            tempVideoTrack.removeRenderer(primaryVideoView);
                            localVideoTrack.addRenderer(primaryVideoView);
                            tempVideoTrack.addRenderer(localVideoView);

                            localVideoViewRenderer = primaryVideoView;
                            primaryVideoView.setMirror(cameraCapturer.getCameraSource() ==
                                    CameraCapturer.CameraSource.FRONT_CAMERA);
                        } else {
                            primaryVideoView.setMirror(false);
                            localVideoView.setMirror(true);
                            localVideoView.setVisibility(View.VISIBLE);
                            localVideoTrack.removeRenderer(primaryVideoView);
                            localVideoTrack.addRenderer(localVideoView);
                            localVideoViewRenderer = localVideoView;
                            localVideoView.setMirror(cameraCapturer.getCameraSource() ==
                                    CameraCapturer.CameraSource.FRONT_CAMERA);

                            tempVideoTrack.removeRenderer(localVideoView);
                            tempVideoTrack.addRenderer(primaryVideoView);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    okButtonDialog(context, "Alert", ex.toString());
                    Toast.makeText(context, "Error in view switch >> " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivTwilioVideo:
                Log.d(TAG, "ivTwilioVideo     " + isChecked);
                if (localVideoTrack != null) {
                    boolean enable = !localVideoTrack.isEnabled();
                    localVideoTrack.enable(enable);
                    Toast.makeText(context, "Twilio video status >> " + enable, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivTwilioAudio:
                Log.d(TAG, "ivTwilioAudio     " + isChecked);
                if (localAudioTrack != null) {
                    boolean enable = !localAudioTrack.isEnabled();
                    localAudioTrack.enable(enable);
                    Toast.makeText(context, "Twilio audio status >> " + enable, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void okButtonDialog(final Context context, String title, final String message) {
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

}
