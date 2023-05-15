package com.pemsa.pemsamonitoreoapp.API.models;

import com.google.gson.JsonObject;

public class CambiarPass {

    public CambiarPass (){

    }
    public JsonObject CambiarPass ( String name, String Apassword, String Npassword ){
        JsonObject EnvCorreo = new JsonObject();
        EnvCorreo.addProperty("name",name);
        EnvCorreo.addProperty("Apassword",Apassword);
        EnvCorreo.addProperty("Npassword",Npassword);
        return EnvCorreo;
    }
}
