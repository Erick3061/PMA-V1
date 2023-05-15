package com.pemsa.pemsamonitoreoapp.API.models;
import com.google.gson.JsonObject;
public class User {
    public User(){ }
    public JsonObject getSession(String email, String password){
        JsonObject user= new JsonObject();
        user.addProperty("email",email);
        user.addProperty("password", password);
        return user;
    }
}
