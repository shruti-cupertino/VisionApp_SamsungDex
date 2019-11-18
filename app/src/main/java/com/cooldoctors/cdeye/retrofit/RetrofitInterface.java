package com.cooldoctors.cdeye.retrofit;

import com.cooldoctors.cdeye.constants.EnvironmentData;
import com.cooldoctors.cdeye.constants.ServiceConstants;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInterface {

    private static String baseUrl = String.format(ServiceConstants.homeUrlFormat,
            EnvironmentData.ENVIRONMENT_IP,
            EnvironmentData.ENVIRONMENT_PORT);

    public static APIInterfacesClass getRetrofitClient(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .addInterceptor(httpLoggingInterceptor).build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        return retrofit.create(APIInterfacesClass.class);
    }

}
