package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.API.models.DataEvents;
import com.pemsa.pemsamonitoreoapp.AperturaCierre;
import com.pemsa.pemsamonitoreoapp.EventoAlarma;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaEA;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.TableDynamic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;

public class getEventos extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    public static Parametros parametros = new Parametros();
    @SuppressLint("StaticFieldLeak")
    public Activity activity;
    String fechaInicio,fechaFinal, token;
    int[] accounts;
    int report;

    public getEventos(int[] accounts, String fechaInicio,String fechaFinal,int report){
        this.accounts=accounts;
        this.fechaInicio=fechaInicio;
        this.fechaFinal=fechaFinal;
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
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    JSONArray eventos = new JSONArray(data.getString("datos"));
                    JSONObject porcentajes = new JSONObject(data.getString("porcentajes"));
                    String total = data.getString("total");
                    String a ="";
                    String[] separado;
                    ArrayList<String> Datos=new ArrayList<>();
                    switch (this.report){
                        case 1:
                            if(AperturaCierre.Grafica.isChecked()){
                                AperturaCierre.lp.setMargins(20,0,20,0);
                                AperturaCierre.linearLayoutGraf.setLayoutParams(AperturaCierre.lp);
                                GraficaApCi grafica = new GraficaApCi();
                                grafica.setActivity(activity);
                                AperturaCierre.graficas.removeAllViews();
                                grafica.borrar();
                                AperturaCierre.graficas = grafica.Grafica(1,AperturaCierre.graficas,porcentajes,total);
                                AperturaCierre.g1=true;
                                AperturaCierre.g2=false;
                                AperturaCierre.g3=false;
                            }
                            for (int i=0;i<eventos.length();i++){
                                JSONObject datos =new JSONObject(eventos.get(i).toString());
                                separado=(datos.getString("FechaHora")).trim().split(" ");
                                a=separado[0].trim()+" "+separado[1].trim().substring(0,8)+" "+(datos.getString("DescripcionEvent")).trim()+" "+(datos.getString("Particion")).trim()+" "+(datos.getString("CodigoZona")).trim()+" "+(datos.getString("NombreUsuario")).replace(" ",".").trim();
                                Datos.add(a);
                            }
                            AperturaCierre.porcentajes=porcentajes;
                            AperturaCierre.total = total;
                            AperturaCierre.Nombre = data.getString("nombre").trim();
                            AperturaCierre.Direccion = data.getString("direccion").trim();
                            AperturaCierre.nombre.setText("NOMBRE: "+AperturaCierre.Nombre);
                            AperturaCierre.CodigoAbonado.setText("DIRECCIÓN: "+AperturaCierre.Direccion);
                            AperturaCierre.tableDynamic=new TableDynamic(AperturaCierre.tableLayout,activity.getApplicationContext());
                            AperturaCierre.tableDynamic.addHeader(AperturaCierre.header);
                            AperturaCierre.tableDynamic.addData(Datos,1);
                            AperturaCierre.rows=Datos;
                            AperturaCierre.creapdf=true;
                        break;
                        case 2:
                            //Toast.makeText(activity.getApplicationContext(),porcentajes.toString(),Toast.LENGTH_LONG).show();
                            if(EventoAlarma.Grafica.isChecked()){
                                EventoAlarma.lp.setMargins(20,0,20,0);
                                EventoAlarma.linearLayoutGraf.setLayoutParams(EventoAlarma.lp);
                                GraficaEA grafica = new GraficaEA();
                                grafica.setActivity(activity);
                                EventoAlarma.graficas.removeAllViews();
                                grafica.borrar();
                                EventoAlarma.graficas = grafica.Grafica(1,EventoAlarma.graficas,porcentajes,total);
                                EventoAlarma.g1=true;
                                EventoAlarma.g2=false;
                                EventoAlarma.g3=false;
                            }
                            EventoAlarma.Nombre = data.getString("Nombre").trim();
                            EventoAlarma.Direccion = data.getString("Direccion").trim();
                            EventoAlarma.nombre.setText("NOMBRE: "+EventoAlarma.Nombre);
                            EventoAlarma.CodigoAbonado.setText("DIRECCIÓN: "+EventoAlarma.Direccion);
                            for (int i=0;i<eventos.length();i++){
                                JSONObject datos =new JSONObject(eventos.get(i).toString());
                                separado=(datos.getString("FechaHora")).trim().split(" ");
                                a=separado[0].trim()+" "+separado[1].trim().substring(0,8)+" "+(datos.getString("Particion")).trim()+" "+(datos.getString("DescripcionEvent")).replace(" ",".").trim()+" "+(datos.getString("CodigoZona")).trim()+" "+(datos.getString("Zona")).trim()+" "+(datos.getString("NombreUsuario")).replace(" ",".").trim()+(datos.getString("NombreZona")).replace(" ",".").trim();
                                Datos.add(a);
                            }
                            EventoAlarma.porcentajes = porcentajes;
                            EventoAlarma.total = total;
                            EventoAlarma.tableDynamic=new TableDynamic(EventoAlarma.tableLayout,activity.getApplicationContext());
                            EventoAlarma.tableDynamic.addHeader(EventoAlarma.header);
                            EventoAlarma.rows.clear();
                            EventoAlarma.tableDynamic.addData(Datos,2);
                            EventoAlarma.rows=Datos;
                            EventoAlarma.creapdf=true;
                        break;
                    }
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
        JsonObject data= new DataEvents(this.accounts,1,this.fechaInicio,this.fechaFinal,false).dataJson();
        try {
            System.out.println(data);
            Response<JsonElement> response = this.report==1?api.getApCi(data).execute(): api.getEA(data).execute();
            if(response.isSuccessful()){
                return response.body();
            }else {
                JsonObject json = new JsonObject();
                json.addProperty("status", false);
                json.addProperty("msg", response.toString());
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
