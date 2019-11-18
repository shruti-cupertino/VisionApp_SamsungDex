package com.cooldoctors.cdeye.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooldoctors.cdeye.R;
import com.kaopiz.kprogresshud.KProgressHUD;

public class NetworkUtil {

    static KProgressHUD hud = null;

    public static boolean isNetworkAvailable(Context context) {
        Log.d("Inside NetworkUtil", "isNetworkAvailable ");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable() && cm
                .getActiveNetworkInfo().isConnected());
    }

    public static void showToastMsg(Context context, String Message) {
        Log.d("Inside NetworkUtil", "showToastMsg");
        Toast toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(7);
        toast.show();
    }


}
