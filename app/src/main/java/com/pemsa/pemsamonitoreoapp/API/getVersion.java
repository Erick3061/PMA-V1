package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.BuildConfig;
import com.pemsa.pemsamonitoreoapp.InicioSesion;
import com.pemsa.pemsamonitoreoapp.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class getVersion extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    Parametros geturl = new Parametros();
    public static String JSON;
    public static  Activity activity;
    public getVersion(){

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

        String url = geturl.url;
        String regreso = "";

        if(strings[0].equals("0")){
            regreso = ObtenerVersion(url);
        }
        return regreso;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(String s) {
        progressDialog.hide();
        progressDialog.dismiss();
        try {
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.has("status")){
                Boolean status = false;
                if(respuesta.getBoolean("status")){
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    String version =data.getString("version");
                    String url =data.getString("url");
                    InicioSesion.ver=version;
                    InicioSesion.url=url;
                    InicioSesion.VersionApp.setText("Versión: "+version);
                    if(Float.parseFloat(BuildConfig.VERSION_NAME) < Float.parseFloat(version)){
                        //Toast.makeText(activity.getApplicationContext(),s+"/n"+Float.parseFloat(BuildConfig.VERSION_NAME.toString()),Toast.LENGTH_LONG).show();
                        InicioSesion.actualizarApp();
                    }
                }
            }
        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(),"Server error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    public static String ObtenerVersion(String cadena) {
        String result;
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection("Sin token");
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getVersion().execute();
            response2=response;
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        result= response2.body().toString();

        return result;

    }

}
