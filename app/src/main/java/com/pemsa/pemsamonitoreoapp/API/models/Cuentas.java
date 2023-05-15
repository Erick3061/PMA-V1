package com.pemsa.pemsamonitoreoapp.API.models;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
public class Cuentas {
    public Cuentas(){
    }
    public JsonObject Body(int[] cuentas){
        JsonObject sendData = new JsonObject();
        JsonArray array = new JsonArray();
        for(int i=0;i<cuentas.length;i++){
            array.add(cuentas[i]);
        }
        sendData.add("cuentas",array);
        return sendData;
    }
}
