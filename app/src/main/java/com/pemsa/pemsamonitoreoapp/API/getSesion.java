package com.pemsa.pemsamonitoreoapp.API;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.API.models.User;
import com.pemsa.pemsamonitoreoapp.InicioSesion;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getSesion extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    String email, password;
    Parametros parametros = new Parametros();
    public static String JSON;
    Activity activity;
    public getSesion(String email,String password){
        this.password=password;
        this.email=email;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Cargando ...");
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressbar);
        progressDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        progressDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
           Retrofit retrofit= parametros.Connection("");
           InterfacesApi api = retrofit.create(InterfacesApi.class);
            Response<JsonElement> response=api.InicioSesion(new User().getSession(email, password)).execute();
            if(response.isSuccessful()){
               return  response.body().toString();
            }else{
                JsonObject json=new JsonObject();
                json.addProperty("status",false);
                json.addProperty("msg",response.toString());
                return json.toString();
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.hide();
        progressDialog.dismiss();

        try {
            String token,uid,name,email;
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.getBoolean("status")){
                JSONObject data = new JSONObject(respuesta.getString("data"));
                uid = data.getString("uid");
                token = data.getString("token");
                name = data.getString("name");
                email = data.getString("email");
                Toast.makeText(activity.getApplicationContext(),"Bienvenido\n"+name,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity.getApplicationContext(),menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Nombre",name);
                intent.putExtra("Token",token);
                intent.putExtra("Correo",email);
                intent.putExtra("Uid",uid);
                activity.startActivityForResult(intent,0);
            }else{
                JSONArray errors = new JSONArray(respuesta.getString("errors"));
                JSONObject resp = new JSONObject(errors.get(0).toString());
                Toast.makeText(activity.getApplicationContext(),resp.getString("msg"),Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            new AlertDialog.Builder(activity)
                    .setTitle("Error")
                    .setMessage(e.getMessage())
                    .show();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

}
