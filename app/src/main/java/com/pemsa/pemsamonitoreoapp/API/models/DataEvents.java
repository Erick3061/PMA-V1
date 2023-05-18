package com.pemsa.pemsamonitoreoapp.API.models;

import android.provider.Settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class DataEvents {
    int[] accounts;
    ArrayList<Integer> list;
    int typeAccount;
    String dateStart;
    String dateEnd;
    boolean advanced;

    public DataEvents(int[] accounts, int typeAccount, String dateStart, String dateEnd, boolean advanced){
        this.accounts=accounts;
        this.typeAccount=typeAccount;
        this.dateStart=dateStart;
        this.dateEnd=dateEnd;
        this.advanced=advanced;
    }

    public DataEvents(ArrayList<Integer> accounts, int typeAccount, String dateStart, String dateEnd, boolean advanced){
        this.list=accounts;
        this.typeAccount=typeAccount;
        this.dateStart=dateStart;
        this.dateEnd=dateEnd;
        this.advanced=advanced;
    }


    public JsonObject dataJson(){
        JsonObject jsonObject = new JsonObject();
        JsonArray accounts= new JsonArray();
        if(this.list==null){
            for (int account : this.accounts) {
                accounts.add(account);
            }
        }else{
            for (int account : this.list) {
                accounts.add(account);
            }
        }
        jsonObject.add("accounts",accounts);
        jsonObject.addProperty("typeAccount",this.typeAccount);
        jsonObject.addProperty("dateStart",this.dateStart);
        jsonObject.addProperty("dateEnd",this.dateEnd);
        jsonObject.addProperty("advanced",this.advanced);
        return jsonObject;
    }

}
