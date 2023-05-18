package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.API.models.Cuentas;
import com.pemsa.pemsamonitoreoapp.API.models.DataEvents;
import com.pemsa.pemsamonitoreoapp.API.models.User;
import com.pemsa.pemsamonitoreoapp.AperturaCierre;
import com.pemsa.pemsamonitoreoapp.EventoAlarma;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaEA;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.TableDynamic;
import com.pemsa.pemsamonitoreoapp.menu;
import com.pemsa.pemsamonitoreoapp.reporteAvanzado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getRepA extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    public static String token;

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        getRepA.token = token;
    }

    Parametros parametros = new Parametros();
    public static String JSON;
    @SuppressLint("StaticFieldLeak")
    Activity activity;

    ArrayList<Integer> accounts;
    String fi,ff;
    int report;
    public getRepA(ArrayList<Integer> accounts, String fi,String ff, int report ){
        this.accounts=accounts;
        this.fi=fi;
        this.ff=ff;
        this.report=report;
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
            this.token=strings[0];
            return getEvents().toString();
        }catch (Exception e){
            JsonObject json=new JsonObject();
            json.addProperty("status", false);
            json.addProperty("msg", e.getMessage());
            return json.toString();
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
                    JSONArray data = new JSONArray(respuesta.getString("data"));
                    JSONArray Cuenta,Nombres,Eventos;
                    String NC="",DC="",a="";
                    String[] separado;

                    for(int i=0;i< data.length();i++){
                        a="";
                        Cuenta = (JSONArray) data.get(i);
                        Nombres = (JSONArray) Cuenta.get(0);//Nombre y Direccion
                        Eventos = (JSONArray) Cuenta.get(1);//Eventos

                        String cambioCuenta="";
                        NC=Nombres.get(0).toString().trim().replace(" ","_");
                        DC=Nombres.get(1).toString().trim().replace(" ","___");

                        if(Nombres.get(0).toString().trim().equals(cambioCuenta)==false){
                            reporteAvanzado.ResultadoConsulta.add("NC "+NC+"***ER***"+DC);
                            cambioCuenta=NC;
                        }{
                            if(this.report==1){//Apertura y Cierre
                                JSONArray con = new JSONArray();
                                for (int ii=0;ii<Eventos.length();ii++){
                                    JSONObject datos =new JSONObject(Eventos.get(ii).toString());
                                    separado=(datos.getString("FechaHora")).trim().split(" ");
                                    a=i+" "+Nombres.get(0).toString().trim().replace(" ",".")+" "+separado[0].trim()+" "+separado[1].trim().substring(0,8)+" "+(datos.getString("DescripcionEvent")).trim()+" "+(datos.getString("Particion")).trim()+" "+(datos.getString("CodigoZona")).trim()+" "+(datos.getString("NombreUsuario")).replace(" ",".").trim();
                                    con.put(a);
                                }
                                reporteAvanzado.ResultadoConsulta.add(con.toString());
                            }
                            if(this.report==2){//Evento de alarma
                                JSONArray con = new JSONArray();
                                for (int ii=0;ii<Eventos.length();ii++){
                                    JSONObject datos =new JSONObject(Eventos.get(ii).toString());
                                    separado=(datos.getString("FechaHora")).trim().split(" ");
                                    a=i+" "+Nombres.get(0).toString().trim().replace(" ",".")+" "+separado[0].trim()+" "+separado[1].trim().substring(0,8)+" "+(datos.getString("Particion")).trim()+" "+(datos.getString("DescripcionEvent")).replace(" ",".").trim()+" "+(datos.getString("CodigoZona")).trim()+" "+(datos.getString("Zona")).trim()+" "+(datos.getString("NombreUsuario")).replace(" ",".").trim()+(datos.getString("NombreZona")).replace(" ",".").trim();
                                    con.put(a);
                                }
                                reporteAvanzado.ResultadoConsulta.add(con.toString());
                            }
                            reporteAvanzado.ResultadoConsulta.add("evento");
                        }
                    }
                    reporteAvanzado.tableDynamic= new TableDynamic(reporteAvanzado.tableLayout,activity.getApplicationContext());
                    reporteAvanzado.tableDynamic.addData(reporteAvanzado.ResultadoConsulta,4);

                }
            }
            else{
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

    public JsonElement getEvents() {
        Retrofit retrofit = parametros.Connection(this.token);
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        JsonObject data= new DataEvents(this.accounts,1,this.fi,this.ff,true).dataJson();
        try {
            System.out.println(data);
            Response<JsonElement> response = this.report==1?api.getApCi(data).execute(): api.getEA(data).execute();
            if(response.isSuccessful()){
                return response.body();
            }else {
                JsonObject json = new JsonObject();
                json.addProperty("status", false);
                json.addProperty("msg","Error en la petición:\n"+ response.toString()+"\n"+data);
                return json;
            }
        } catch (IOException e) {
            JsonObject json=new JsonObject();
            json.addProperty("status", false);
            json.addProperty("msg", e.getMessage());
            return json;
        }
    }

}
