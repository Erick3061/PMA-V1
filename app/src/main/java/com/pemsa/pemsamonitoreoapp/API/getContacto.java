package com.pemsa.pemsamonitoreoapp.API;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.ui.home.HomeFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class getContacto extends AsyncTask<String, Void, String> {

    Parametros geturl = new Parametros();
    public static String JSON;
    public static  Activity activity;
    public getContacto(){

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
    }

    @Override
    protected String doInBackground(String... strings) {

        String url = geturl.url;
        String regreso = "";

        if(strings[0].equals("1")){
            regreso = ObtenerFecha(url);
        }
        return regreso;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.has("status")){
                if(respuesta.getBoolean("status")){
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    HomeFragment.Mensajeria=data.getString("M");
                    HomeFragment.FacebookId=data.getString("FID");
                    HomeFragment.FacebookUrl=data.getString("FURL");
                    HomeFragment.Instagram=data.getString("I");
                    HomeFragment.Twiter=data.getString("T");
                    HomeFragment.correo.setText("CENTRAL MONITOREO 24HRS\n"+data.getString("CCM"));
                }
            }
        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(),"Server error\n"+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    public static String ObtenerFecha(String cadena) {
        String result;
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection("Sin tooken");
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getContacto().execute();
            response2=response;
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        result= response2.body().toString();

        return result;

    }

}
