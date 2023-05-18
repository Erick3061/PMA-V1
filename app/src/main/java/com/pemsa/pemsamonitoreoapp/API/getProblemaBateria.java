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
import com.pemsa.pemsamonitoreoapp.API.models.User;
import com.pemsa.pemsamonitoreoapp.AperturaCierreGrupo;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCiGoD;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaPB;
import com.pemsa.pemsamonitoreoapp.ProblemaBateria;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.TableDynamic;
import com.pemsa.pemsamonitoreoapp.reporteAvanzado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import retrofit2.Response;
import retrofit2.Retrofit;

public class getProblemaBateria extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    Parametros parametros = new Parametros();
    public static String JSON;
    public Activity activity;
    public static Response<JsonElement> response2 = null;
    Report data;
    public getProblemaBateria (Report data){
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
            Response<JsonElement> response=api.getBattery(this.data.getJson()).execute();
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

    @SuppressLint("SetTextI18n")
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
                    String Total = "";
                    ArrayList <String> tp = new ArrayList<>();
                    JSONObject data = new JSONObject( respuesta.getString( "data" ) );
                    JSONObject porcentajes = new JSONObject(data.getString( "porcentajes" ) );
                    JSONArray CSR = new JSONArray(data.getString("CSR"));
                    JSONArray CCR = new JSONArray(data.getString("CCR"));
                    JSONArray CSE = new JSONArray(data.getString("CSE"));
                    Total = data.getString("totalCuentas");

                    tp.add( "CUENTAS_SIN_RESTAURE_DE_BATERIA_BAJA" + " " + CSR.length() + " " + porcentajes.getString("CSR"));
                    tp.add( "CUENTAS_CON_RESTAURE_DE_BATERIA_BAJA" + " " + CCR.length() + " " + porcentajes.getString("CCR"));
                    tp.add( "CUENTAS_SIN_EVENTOS_DE_BATERIA_BAJA" + " " + CSE.length() + " " + porcentajes.getString("CSE"));

                    ProblemaBateria.CSR=CSR;
                    ProblemaBateria.CCR=CCR;
                    ProblemaBateria.CSE=CSE;

                    ProblemaBateria.porcentajes = porcentajes;
                    ProblemaBateria.totalCuentas = Total;
                    ProblemaBateria.NombreGrupo.setText( "Nombre de grupo: "+data.getString("Nombre" ) );
                    ProblemaBateria.TotalCu.setText( "Total de cuentas: "+ Total);

                    ProblemaBateria.tp=tp;

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tablePorcentajes,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.addTablePorcentajes(tp,1);

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tableCSR,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.createTablesPB(CSR,1);

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tableCCR,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.createTablesPB(CCR,2);

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tableCSE,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.createTablesPB(CSE,3);

                    GraficaPB grafica = new GraficaPB();
                    grafica.setActivity(activity);
                    ProblemaBateria.grafica.removeAllViews();
                    grafica.borrar();
                    ProblemaBateria.grafica = grafica.Grafica(1,ProblemaBateria.grafica,porcentajes,Total);
                    ProblemaBateria.g1=true;
                    ProblemaBateria.g2=false;
                    ProblemaBateria.g3=false;
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
}
