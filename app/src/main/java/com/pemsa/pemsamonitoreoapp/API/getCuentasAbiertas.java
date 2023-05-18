package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.API.models.Report;
import com.pemsa.pemsamonitoreoapp.AdapterFormato;
import com.pemsa.pemsamonitoreoapp.CuentasAbiertas;
import com.pemsa.pemsamonitoreoapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import retrofit2.Response;
import retrofit2.Retrofit;

public class getCuentasAbiertas extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    Parametros parametros = new Parametros();
    public static String JSON;
    public Activity activity;
    public static Response<JsonElement> response2 = null;
    Report data;
    public getCuentasAbiertas (Report data){
        this.data=data;
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
            Retrofit retrofit= parametros.Connection(strings[0]);
            InterfacesApi api = retrofit.create(InterfacesApi.class);
            Response<JsonElement> response=api.getReportState(this.data.getJson()).execute();
            if(response.isSuccessful()){
                return  response.body().toString();
            }else{
                JsonObject json=new JsonObject();
                json.addProperty("status",false);
                json.addProperty("msg",response.toString()+"\n\n"+this.data.getJson());
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
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.has("status")){
                if(respuesta.has("msg") && !respuesta.getBoolean("status")){
                    throw new RuntimeException(respuesta.getString("msg"));
                }else{
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    CuentasAbiertas.nombreGrupo.setText(data.getString("Nombre"));
                    JSONArray cuentasObj = new JSONArray(data.getString("datos"));
                    ArrayList<String> Datos=new ArrayList<>();
                    String cadena="";
                    for (int i=0;i<cuentasObj.length();i++){
                        JSONObject cuenta = new JSONObject(cuentasObj.get(i).toString());
                        cadena=cuenta.getString("particion")+" "+cuenta.getString("estado").replace(" ",".")+" "+(cuenta.getString("nombre")).replace(" ","_");
                        Datos.add(cadena);
                    }
                    CuentasAbiertas.textoextra.setText( ""+Datos.size() );
                    CuentasAbiertas.llenarFormato(Datos);
                    AdapterFormato adapterFormato= new AdapterFormato(CuentasAbiertas.LisDatos);
                    CuentasAbiertas.recyclerFormato.setAdapter(adapterFormato);
                }
            } else{
                throw new RuntimeException("Server error");
            }
        } catch (Exception e) {
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
