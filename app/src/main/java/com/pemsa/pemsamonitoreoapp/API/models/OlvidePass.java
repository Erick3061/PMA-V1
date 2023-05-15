package com.pemsa.pemsamonitoreoapp.API.models;

import com.google.gson.JsonObject;

public class OlvidePass {

    public OlvidePass (){

    }
    public JsonObject OlvidePass (String correo){
        JsonObject EnvCorreo = new JsonObject();
        EnvCorreo.addProperty("email",correo);
        return EnvCorreo;
    }
}