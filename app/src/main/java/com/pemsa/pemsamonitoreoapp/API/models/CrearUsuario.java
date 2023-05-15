package com.pemsa.pemsamonitoreoapp.API.models;
import com.google.gson.JsonObject;
public class CrearUsuario {
    public CrearUsuario(){

    }
    public JsonObject CrearUsuario(String name, String email, String cuentaNombre, String direccionSA){
        JsonObject a= new JsonObject();
        JsonObject b= new JsonObject();
        a.addProperty("name",name);
        a.addProperty("email", email);
        b.addProperty("cuentaNombre",cuentaNombre);
        b.addProperty("direccionSA",direccionSA);
        a.add("data",b);
        return a;
    }

}
