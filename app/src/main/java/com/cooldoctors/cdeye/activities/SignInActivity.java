package com.cooldoctors.cdeye.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cooldoctors.cdeye.R;
import com.cooldoctors.cdeye.constants.NetworkUtil;
import com.cooldoctors.cdeye.constants.SharedPreference;
import com.cooldoctors.cdeye.constants.UtilClass;
import com.cooldoctors.cdeye.models.SignInDao;
import com.cooldoctors.cdeye.models.SignInResponse;
import com.cooldoctors.cdeye.retrofit.RetrofitInterface;
import com.google.android.material.appbar.AppBarLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    Button btnSignIn;
    ImageView ivshowHidePw;
    Context context;

    KProgressHUD hud;
    SharedPreference sharedPreference;
    Activity activity;

    String TAG = "Inside " + SignInActivity.class.getSimpleName();

    boolean isPasswordShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setStatusBarGradiant(this);

        context = this;
        sharedPreference = new SharedPreference(context);

        initializeViews();
    }

    private void initializeViews() {

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        ivshowHidePw = findViewById(R.id.showHidePw);

        hud = UtilClass.initProgressDialog(context);

        activity = SignInActivity.this;

        btnSignIn.setOnClickListener(view -> {
            if (checkValidation())
                login();
        });

        ivshowHidePw.setOnClickListener(view -> {
            if (!isPasswordShown) {
                //show password
                edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isPasswordShown = true;
                ivshowHidePw.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility));
                //showHidePw.getLayoutParams().height = 25;
            } else {
                //hide password
                edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isPasswordShown = false;
                ivshowHidePw.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide));
                //showHidePw.getLayoutParams().height = 20;
            }
        });
    }

    public void login() {

        if (hud != null)
            if (!hud.isShowing())
                hud.show();

        final SignInDao signIn = new SignInDao();
        signIn.setEmail(edEmail.getText().toString().trim().toLowerCase());
        signIn.setPassword(edPassword.getText().toString().trim());

        if (NetworkUtil.isNetworkAvailable(context)) {
            //TODO add retrofit call
            RetrofitInterface.getRetrofitClient()
                    .signIn(signIn)
                    .enqueue(new Callback<SignInResponse>() {
                        @Override
                        public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                            if (response.body() != null) {
                                Log.d(TAG, "response >>    " + response.body());
                                if (response.body().getToken() != null) {
                                    if (response.body().getToken().contains("patient")) {
                                        if (hud != null)
                                            if (hud.isShowing())
                                                hud.dismiss();
                                        Log.d(TAG, "user token >>   " + response.body().getToken());
                                        sharedPreference.saveData(SharedPreference.userName, signIn.getEmail());
                                        sharedPreference.saveData(SharedPreference.userPass, signIn.getPassword());
                                        sharedPreference.saveData(SharedPreference.userToken, response.body().getToken());
                                        sharedPreference.saveData(SharedPreference.userId, response.body().getUserId());
                                        success();
                                    } else {
                                        UtilClass.dismissProgressDialog(hud);
                                        if (response.body().getMessage() != null) {
                                            okButtonDialog(context, getResources().getString(R.string.alert), response.body().getMessage());
                                        }
                                    }
                                } else if (response.message() != null) {
                                    UtilClass.dismissProgressDialog(hud);
                                    okButtonDialog(context, getResources().getString(R.string.alert), response.message());
                                }
                            } else {
                                UtilClass.dismissProgressDialog(hud);
                                okButtonDialog(context, getResources().getString(R.string.alert), "Something went wrong, please try again!");
                            }
                        }

                        @Override
                        public void onFailure(Call<SignInResponse> call, Throwable t) {
                            UtilClass.dismissProgressDialog(hud);
                            okButtonDialog(context, getResources().getString(R.string.alert), t.getMessage());
                            t.printStackTrace();
                        }
                    });
        }

    }

    private boolean checkValidation() {

        Log.d(TAG, "edPassword.length()7           " + edPassword.getText().toString().length());
        Log.d(TAG, "edPassword.length()8           " + (edPassword.getText().toString().length() < 8));
        Log.d(TAG, "edPassword.length()20           " + (edPassword.getText().toString().length() > 20));
        if (edEmail.getText().toString().equals("")) {
            okButtonDialog(context, context.getResources().getString(R.string.error),
                    context.getResources().getString(R.string.enterEmail));
        } else if (edPassword.getText().toString().equals("")) {
            okButtonDialog(context, context.getResources().getString(R.string.error),
                    context.getResources().getString(R.string.enterPassword));
        } else if (edPassword.getText().toString().length() < 8 || edPassword.getText().toString().length() > 21) {
            Log.d(TAG, "etPassword.length()7           " + edPassword.getText().toString().length());
            okButtonDialog(context, context.getResources().getString(R.string.error),
                    context.getResources().getString(R.string.passLength));
        } else
            return true;
        return false;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.two_color_dashboard);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }


    public void success() {
        Log.d(TAG, "success           ");
        if (hud != null)
            if (hud.isShowing())
                hud.dismiss();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UtilClass.callActivity(intent,this);
//        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
