package com.pemsa.pemsamonitoreoapp.API.interfaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfacesApi {

    @POST("auth")
    Call<JsonElement> InicioSesion(@Body JsonElement inicioSesion);

    @Headers({ "Content-Type: application/json"})
    @GET("accounts/my-accounts")
    Call<JsonElement> getAllAccount();

    @Headers({ "Content-Type: application/json"})
    @GET("accounts/my-groups")
    Call<JsonElement> getGrupos();

    @GET("api/reportes/obtenerContactos")
    Call<JsonElement> getContacto();

    @Headers("Content-Type: application/json")
    @POST("reports/ap-ci")
    Call<JsonElement> getApCi(@Body JsonElement data);

    @Headers("Content-Type: application/json")
    @POST("reports/event-alarm")
    Call<JsonElement> getEA(@Body JsonElement data);

    @Headers("Content-Type: application/json")
    @POST("reports/batery")
    Call<JsonElement> getBattery(@Body JsonElement data);

    @POST("reports/state")
    Call<JsonElement> getReportState(@Body JsonElement data);

    @POST("reports/apci-week")
    Call<JsonElement> getApCiweek(@Body JsonElement data);
}

