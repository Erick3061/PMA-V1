package com.pemsa.pemsamonitoreoapp.API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.API.models.Cuentas;
import com.pemsa.pemsamonitoreoapp.API.models.User;
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
    public static  int identificador;
    public static String token;

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        getRepA.token = token;
    }

    public static int Codigos[];

    public static int[] getCodigos() {
        return Codigos;
    }

    public static void setCodigos(int[] codigos) {
        Codigos = codigos;
    }

    public static int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    Parametros geturl = new Parametros();
    public static String JSON;
    Activity activity;
    public getRepA(){

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
        String regreso = "";

        if(strings[0].equals("1")){
            regreso = ObtenerEventos(cadena,strings[1],strings[2]);
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
                if(respuesta.getBoolean("status")){
                    JSONArray data = new JSONArray(respuesta.getString("data"));
                    ArrayList<String> Datos=new ArrayList<>();
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
                            if(getIdentificador()==1){//Apertura y Cierre
                                JSONArray con = new JSONArray();
                                for (int ii=0;ii<Eventos.length();ii++){
                                    JSONObject datos =new JSONObject(Eventos.get(ii).toString());
                                    separado=(datos.getString("FechaHora")).trim().split(" ");
                                    a=i+" "+Nombres.get(0).toString().trim().replace(" ",".")+" "+separado[0].trim()+" "+separado[1].trim().substring(0,8)+" "+(datos.getString("DescripcionEvent")).trim()+" "+(datos.getString("Particion")).trim()+" "+(datos.getString("CodigoZona")).trim()+" "+(datos.getString("NombreUsuario")).replace(" ",".").trim();
                                    con.put(a);
                                }
                                reporteAvanzado.ResultadoConsulta.add(con.toString());
                            }
                            if(getIdentificador()==2){//Evento de alarma
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
            if(respuesta.has("errors")){
                JSONArray errors = new JSONArray(respuesta.getString("errors"));
                JSONObject msg =new JSONObject(errors.get(0).toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), msg.getString("msg"), Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.dialogredondo);
                toast.show();
            }

        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(),"Server error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    public static String ObtenerEventos(String cadena, String param,String cuentas) {
        String result = "",Fi="",Ff="";
        String[] separado=param.split("___ESP___");
        Fi=separado[0];
        Ff=separado[1];

        try {
            JSONObject json = new JSONObject(cuentas);
            JSONArray array = new JSONArray(json.getString("cuentas"));
            int recorre[] = new int[array.length()];
            for(int i=0;i<recorre.length;i++){
                recorre[i]= (Integer.parseInt(array.get(i).toString()));
            }
            Codigos=recorre;
            //reporteAvanzado.pruebas.setText(array.get(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection(getToken());
        Cuentas accounts = new Cuentas();
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            if(getIdentificador() == 1){
                Response<JsonElement> response = api.getApCiA(accounts.Body(Codigos),Fi,Ff).execute();
                response2=response;
            }
            if(getIdentificador() == 2){
                Response<JsonElement> response = api.getEAA(accounts.Body(Codigos),Fi,Ff).execute();
                response2=response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response2.isSuccessful()){
            JSON =response2.body().toString();
        }
        if(response2.code()==404){
                JSON="Error 404 Not Fount";
        }
        result= JSON;
        return result;

    }

}
