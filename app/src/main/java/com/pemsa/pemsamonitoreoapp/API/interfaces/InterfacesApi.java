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

    @GET("api/reportes/obtenerfecha")
    Call<JsonElement> getfecha();

    @GET("api/reportes/obtenerContactos")
    Call<JsonElement> getContacto();

    @Headers("Content-Type: application/json")
    @POST("reports/ap-ci")
    Call<JsonElement> getApCi(@Body JsonElement data);

    @Headers("Content-Type: application/json")
    @POST("reports/event-alarm")
    Call<JsonElement> getEA(@Body JsonElement data);

    @GET("api/reportes/reporteBateria/{fecha}/{code}/{type}")
    Call<JsonElement> getPB( @Path("fecha") String fecha,@Path("code") String code,@Path("type") String type);

    @GET("api/reportes/reporteCuentasAbiertas/{fecha}/{code}/{type}")
    Call<JsonElement> getCuentasAbiertas( @Path("fecha") String fecha,@Path("code") String code,@Path("type") String type);

    @GET("api/reportes/reporteApCiG/{fecha}/{code}/{type}")
    Call<JsonElement> getApCiGoD( @Path("fecha") String fecha,@Path("code") String code,@Path("type") String type);

    @POST("api/usuarios/crearUsuario")
    Call<JsonElement> CrearUsuario(@Body JsonObject Datos);

    @POST("api/usuarios/forgetPassword")
    Call<JsonElement> olvidePass(@Body JsonObject Correo);

    @PUT("api/usuarios/actualizaUsuario/{Uid}/user")
    Call<JsonElement> cambiarPass(@Body JsonObject Datos,@Path("Uid") String Uid);

    @GET("api/reportes/obtenerVerApp")
    Call<JsonElement> getVersion();

}

