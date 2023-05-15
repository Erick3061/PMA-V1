package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.AperturaCierre;
import com.pemsa.pemsamonitoreoapp.EventoAlarma;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaEA;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.TableDynamic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;

public class getEventos extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    public static String tipo, token;
    public static boolean verGrafica;

    public static boolean isVerGrafica() {
        return verGrafica;
    }

    public static void setVerGrafica(boolean verGrafica) {
        getEventos.verGrafica = verGrafica;
    }

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        getEventos.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        getEventos.tipo = tipo;
    }


    public static Parametros geturl = new Parametros();
    public static String JSON;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    public getEventos(){

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        getEventos.activity = activity;
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

        if(strings[0].equals("1")){
            setTipo(strings[0]);
            regreso = ObtenerAPCI(url,strings[1]);
        }
        if(strings[0].equals("2")){
            setTipo(strings[0]);
            regreso = ObtenerEA(url,strings[1]);
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
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.has("status")){
                Boolean status = false;
                if(respuesta.getBoolean("status")){
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    JSONArray eventos = new JSONArray(data.getString("datos"));
                    JSONObject porcentajes = new JSONObject(data.getString("porcentajes"));
                    String total = data.getString("total");
                    String a ="";
                    String[] separado;
                    ArrayList<String> Datos=new ArrayList<>();
                    switch (getTipo()){
                        case "1":
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
                        case "2":
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
            if(respuesta.has("errors")){
                JSONArray errors = new JSONArray(respuesta.getString("errors"));
                JSONObject msg =new JSONObject(errors.get(0).toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), msg.getString("msg"), Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.dialogredondo);
                toast.show();
            }

        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(),"Server error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    public static String ObtenerAPCI(String cadena, String param) {

        String result = "",idCuenta="",Fi="",Ff="";
        String[] separado = param.split("___ESP___");
        idCuenta=separado[0].trim();
        Fi=separado[1].trim();
        Ff=separado[2].trim();
        Retrofit retrofit = geturl.Connection(getToken());
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getApCi(idCuenta,Fi,Ff).execute();
            if(response.isSuccessful()){
                response2=response;
            }
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(),"Error en la consulta\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        result= response2.body().toString();

        return result;

    }

    public static String ObtenerEA(String cadena, String param) {

        String result = "",idCuenta="",Fi="",Ff="";
        String[] separado = param.split("___ESP___");
        idCuenta=separado[0].trim();
        Fi=separado[1].trim();
        Ff=separado[2].trim();
        Retrofit retrofit = geturl.Connection(getToken());
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getEA(idCuenta,Fi,Ff).execute();
            response2=response;
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(),"Error en la consulta\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        result= response2.body().toString();

        return result;

    }

}
