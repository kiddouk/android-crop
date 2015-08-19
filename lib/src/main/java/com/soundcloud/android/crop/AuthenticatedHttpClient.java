package com.soundcloud.android.crop;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.OkHttpClient;

import android.util.Log;

public class AuthenticatedHttpClient {

    private final static String TAG = "SL - " + AuthenticatedHttpClient.class.toString();
    private OkHttpClient client;
    private static AuthenticatedHttpClient instance;

    public static OkHttpClient getInstance(String token) {
        if (instance == null) {
            instance = new AuthenticatedHttpClient(token);
        }

        return instance.getHttpClient();
    }

    public OkHttpClient getHttpClient() {
        return client;
    }

    public AuthenticatedHttpClient(String token) {
        client = new OkHttpClient();
        client.setFollowSslRedirects(true);
        client.setFollowRedirects(true);

        if (token == null) {
            return;
        }

        client.interceptors().add(chain -> {
                Request request = chain.request();
                Request newReq = request.newBuilder()
                    .addHeader("Authorization", token)
                    .build();
                return chain.proceed(newReq);
            });        
    }


}
