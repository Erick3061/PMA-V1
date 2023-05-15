package com.pemsa.pemsamonitoreoapp.API;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Parametros {
    public Parametros (){

    }
    public String Token,Uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String url ="https://consultas.pem-sa.com.mx";

    public Retrofit Connection(String token){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        String finalToken = token;
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("x-token",finalToken).build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.connectTimeout(300, TimeUnit.SECONDS).writeTimeout(300, TimeUnit.SECONDS).readTimeout(300,TimeUnit.SECONDS).build())
                .build();
        return retrofit;

    }
}