package com.cooldoctors.cdeye.twilio;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.twilio.video.CameraCapturer;
import com.twilio.video.VideoCapturer;

public class TwilioCameraCapturer {
    private static final String TAG = "Inside "+ TwilioCameraCapturer.class.getSimpleName();

    private com.twilio.video.CameraCapturer camera1Capturer;
    private Pair<CameraCapturer.CameraSource, String> frontCameraPair;
    private Pair<CameraCapturer.CameraSource, String> backCameraPair;

    public TwilioCameraCapturer(Context context,
                                com.twilio.video.CameraCapturer.CameraSource cameraSource) {
        try {
            Log.d(TAG, "TwilioCameraCapturer");
            camera1Capturer = new com.twilio.video.CameraCapturer(context, cameraSource);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public com.twilio.video.CameraCapturer.CameraSource getCameraSource() {
        if (usingCamera1()) {
            Log.d(TAG, "usingCamera1");
            return camera1Capturer.getCameraSource();
        } else {
            Log.d(TAG, "usingCamera else");
            return camera1Capturer.getCameraSource();
        }
    }

    public void switchCamera() {
        if (usingCamera1()) {
            Log.d(TAG, "switchCamera");
            camera1Capturer.switchCamera();
        } else {
            Log.d(TAG, "switchCamera else");
            camera1Capturer.switchCamera();
        }
    }

    /*
     * This method is required because this class is not an implementation of VideoCapturer due to
     * a shortcoming in the Video Android SDK.
     */
    public VideoCapturer getVideoCapturer() {
        if (usingCamera1()) {
            Log.d(TAG, "getVideoCapturer");
            return camera1Capturer;
        } else {
            Log.d(TAG, "getVideoCapturer else");
            return camera1Capturer;
        }
    }

    private boolean usingCamera1() {
        return camera1Capturer != null;
    }
}
