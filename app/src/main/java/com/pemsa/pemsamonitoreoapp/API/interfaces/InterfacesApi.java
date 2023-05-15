package com.pemsa.pemsamonitoreoapp.API.interfaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfacesApi {

    @POST("api/auth/loginUser")
    Call<JsonElement> InicioSesion(@Body JsonElement inicioSesion);

    @GET("api/usuarios/getTodasCuentas/{Uid}")
    Call<JsonElement> getAllAccount( @Path("Uid") String Uid);

    @GET("api/reportes/obtenerfecha")
    Call<JsonElement> getfecha();

    @GET("api/reportes/obtenerContactos")
    Call<JsonElement> getContacto();

    @GET("api/usuarios/getCuentasGrupales/{Uid}")
    Call<JsonElement> getGrupos( @Path("Uid") String Uid);

    @GET("api/reportes/reporteBateria/{fecha}/{code}/{type}")
    Call<JsonElement> getPB( @Path("fecha") String fecha,@Path("code") String code,@Path("type") String type);

    @GET("api/reportes/reporteApCiI/{FechaInicio}/{FechaFinal}/{cuenta}")
    Call<JsonElement> getApCi(@Path("cuenta") String cuenta,@Path("FechaInicio") String FechaInicio,@Path("FechaFinal") String FechaFinal);

    @GET("api/reportes/reporteEAI/{FechaInicio}/{FechaFinal}/{cuenta}")
    Call<JsonElement> getEA(@Path("cuenta") String cuenta,@Path("FechaInicio") String FechaInicio,@Path("FechaFinal") String FechaFinal);

    @GET("api/reportes/reporteCuentasAbiertas/{fecha}/{code}/{type}")
    Call<JsonElement> getCuentasAbiertas( @Path("fecha") String fecha,@Path("code") String code,@Path("type") String type);

    @GET("api/reportes/reporteApCiG/{fecha}/{code}/{type}")
    Call<JsonElement> getApCiGoD( @Path("fecha") String fecha,@Path("code") String code,@Path("type") String type);

    @POST("api/reportes/reporteAvanzadoApCi/{FechaInicio}/{FechaFinal}")
    Call<JsonElement> getApCiA(@Body JsonObject Cuentas,@Path("FechaInicio") String FechaInicio,@Path("FechaFinal") String FechaFinal);

    @POST("api/reportes/reporteAvanzadoEA/{FechaInicio}/{FechaFinal}")
    Call<JsonElement> getEAA(@Body JsonObject Cuentas,@Path("FechaInicio") String FechaInicio,@Path("FechaFinal") String FechaFinal);

    @POST("api/usuarios/crearUsuario")
    Call<JsonElement> CrearUsuario(@Body JsonObject Datos);

    @POST("api/usuarios/forgetPassword")
    Call<JsonElement> olvidePass(@Body JsonObject Correo);

    @PUT("api/usuarios/actualizaUsuario/{Uid}/user")
    Call<JsonElement> cambiarPass(@Body JsonObject Datos,@Path("Uid") String Uid);

    @GET("api/reportes/obtenerVerApp")
    Call<JsonElement> getVersion();

}

