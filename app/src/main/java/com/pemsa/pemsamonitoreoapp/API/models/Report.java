package com.pemsa.pemsamonitoreoapp.API.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Report {
    ArrayList<Integer> accounts;
    int typeAccount;

    public Report(ArrayList<Integer> accounts, int typeAccount){
        this.accounts=accounts;
        this.typeAccount=typeAccount;
    }

    public JsonObject getJson(){
        JsonObject json=new JsonObject();
        JsonArray accounts= new JsonArray();
        for (int account: this.accounts) {
            accounts.add(account);
        }
        json.add("accounts",accounts);
        json.addProperty("typeAccount", this.typeAccount);
        return json;
    }
}
