package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.AperturaCierreGrupo;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCiGoD;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaPB;
import com.pemsa.pemsamonitoreoapp.ProblemaBateria;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.TableDynamic;

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
    Parametros geturl = new Parametros();
    public static String JSON;
    public static Activity activity;
    public static Response<JsonElement> response2 = null;
    public getProblemaBateria (){

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

        if(strings[0].equals("1")){
            regreso = ObtenerProblemaBateria(url,strings[1]);
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
                if(respuesta.getBoolean("status")){
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

                }else{
                    JSONObject data = new JSONObject( respuesta.getString( "data" ) );
                    ArrayList <String> tp = new ArrayList<>();
                    tp.add( "CUENTAS_SIN_RESTAURE_DE_BATERIA_BAJA" + " " + 0 + " " + 0);
                    tp.add( "CUENTAS_CON_RESTAURE_DE_BATERIA_BAJA" + " " + 0 + " " + 0);
                    tp.add( "CUENTAS_SIN_EVENTOS_DE_BATERIA_BAJA" + " " + 0 + " " + 0);

                    ProblemaBateria.CSR = new JSONArray();
                    ProblemaBateria.CCR = new JSONArray();
                    ProblemaBateria.CSE = new JSONArray();

                    ProblemaBateria.porcentajes = new JSONObject();
                    ProblemaBateria.porcentajes.put("CSR",0);
                    ProblemaBateria.porcentajes.put("CCR",0);
                    ProblemaBateria.porcentajes.put("CSE",0);

                    ProblemaBateria.totalCuentas = "0";
                    ProblemaBateria.NombreGrupo.setText( "Nombre de grupo: "+data.getString("Nombre" ) );
                    ProblemaBateria.TotalCu.setText( "Total de cuentas: "+ 0);

                    ProblemaBateria.tp=tp;

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tablePorcentajes,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.addTablePorcentajes(tp,1);

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tableCSR,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.createTablesPB(ProblemaBateria.CSR,1);

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tableCCR,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.createTablesPB(ProblemaBateria.CCR,2);

                    ProblemaBateria.tableDynamic = new TableDynamic(ProblemaBateria.tableCSE,activity.getApplicationContext());
                    ProblemaBateria.tableDynamic.createTablesPB(ProblemaBateria.CSE,3);

                    GraficaPB grafica = new GraficaPB();
                    grafica.setActivity(activity);
                    ProblemaBateria.grafica.removeAllViews();
                    grafica.borrar();
                    ProblemaBateria.grafica = grafica.Grafica(1,ProblemaBateria.grafica,ProblemaBateria.porcentajes,"0");
                    ProblemaBateria.g1=true;
                    ProblemaBateria.g2=false;
                    ProblemaBateria.g3=false;
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

    @SuppressLint("SimpleDateFormat")
    public static String ObtenerProblemaBateria(String cadena, String param) {
        String Anio="",Mes="",Dia="",Fecha="";
        Anio=new SimpleDateFormat("yyyy").format(new Date());
        Dia=new SimpleDateFormat("dd").format(new Date());
        Mes=new  SimpleDateFormat("MM").format(new Date());
        Fecha=Anio+"-"+Mes+"-"+Dia;

        String result = "",token="",CG="",Type="";

        String[] separado = param.split("___ESP___");
        token=separado[0];
        CG=separado[1];
        Type=separado[2];
        Parametros parametros= new Parametros();
        Retrofit retrofit = parametros.Connection(token);
        InterfacesApi api = retrofit.create(InterfacesApi.class);

        try {
            Response<JsonElement> response = api.getPB(Fecha,CG,Type).execute();
            response2=response;
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(),"Error: \n" + e.getMessage(),Toast.LENGTH_LONG).show();
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
