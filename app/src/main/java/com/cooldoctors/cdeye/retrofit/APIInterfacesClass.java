package com.cooldoctors.cdeye.retrofit;

import com.cooldoctors.cdeye.constants.ServiceConstants;
import com.cooldoctors.cdeye.models.AboutMe;
import com.cooldoctors.cdeye.models.FCMTokenRegister;
import com.cooldoctors.cdeye.models.SignInDao;
import com.cooldoctors.cdeye.models.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterfacesClass {

    @Headers(ServiceConstants.contentTypeKey + ServiceConstants.contentType)
    @POST(ServiceConstants.signIn)
    Call<SignInResponse> signIn(
            @Body SignInDao sign);

    @Headers(ServiceConstants.contentTypeKey + ": application/json")
    @POST(ServiceConstants.registerFCM)
    Call<AboutMe> registerFCMToken(@Header(ServiceConstants.tokenKey) String token,
                                   @Body FCMTokenRegister fcmTokenRegister);

    @Headers(ServiceConstants.contentTypeKey + ": application/json")
    @POST(ServiceConstants.removeFCMToken)
    Call<AboutMe> removeFCMToken(@Header(ServiceConstants.tokenKey) String token,
                                 @Body FCMTokenRegister fcmTokenRegister);

}
