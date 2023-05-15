package com.pemsa.pemsamonitoreoapp.API;

import android.app.Activity;
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
    Parametros geturl = new Parametros();
    public static String JSON;
    Activity activity;
    public getSesion(){

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

        String cadena = geturl.url;
       Log.d("Cadena de consulta",JSON);
        String regreso = "";

        if(strings[0].equals("1")){
            //regreso = ObtenerSesion(cadena,strings[1]);
        }
        return regreso;
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
            Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    public static String ObtenerSesion(String cadena, String param){

        String email="",password="";
        String[] separado=param.split("___ESP___");
        email=separado[0];
        password=separado[1];
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection("Sin token");
        User user = new User();
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        try {
            Response<JsonElement> response = api.InicioSesion(user.getSession(email,password)).execute();
            if(response.isSuccessful()){
                JSON = response.body().toString();
            }else{
                JSON = response.toString();
            }
        } catch (IOException e) {
            JsonObject obj1 = new JsonObject();
            JsonObject obj2 = new JsonObject();
            JsonArray errors = new JsonArray();
            obj1.addProperty("status",false);
            obj2.addProperty("msg",e.getMessage());
            errors.add(obj2);
            obj1.add("errors",errors);
            JSON= obj1.toString();
        }
        return JSON;
    }

}
