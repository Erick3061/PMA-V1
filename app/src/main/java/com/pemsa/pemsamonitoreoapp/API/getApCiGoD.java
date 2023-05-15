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

public class getApCiGoD extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    Parametros geturl = new Parametros();
    public static String JSON;
    Activity activity;
    public getApCiGoD (){

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
            regreso = ObtenerCuentasAbiertas(url,strings[1]);
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
                ArrayList <String> tp = new ArrayList<>();
                if(respuesta.getBoolean("status")){
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    JSONArray datos = new JSONArray(data.getString("datos"));
                    JSONObject FechasH = new JSONObject(data.getString("fechasH"));
                    JSONArray dias = new JSONArray(FechasH.getString("dias"));
                    JSONArray fechas = new JSONArray(FechasH.getString("fechas"));
                    JSONObject porcentajes = new JSONObject(data.getString("porcentajes"));
                    String totalAperturas = data.getString("totalAperturas");
                    String totalCierres = data.getString("totalCierres");
                    AperturaCierreGrupo.porcentajes=porcentajes;
                    AperturaCierreGrupo.totalAperturas = totalAperturas;
                    AperturaCierreGrupo.totalCierres = totalCierres;

                    tp.add( "APERTURAS_RECIBIDAS" + " " + porcentajes.getString("aperturasConHora") + " " + 1);
                    tp.add( "APERTURAS_FALTANTES" + " " + porcentajes.getString("aperturasSinHora") + " " + 2);
                    tp.add( "CIERRES_RECIBIDOS" + " " + porcentajes.getString("cierresConHora") + " " + 3);
                    tp.add( "CIERRES_FALTANTES" + " " + porcentajes.getString("cierresSinHora") + " " + 4);

                    AperturaCierreGrupo.tp=tp;
                    AperturaCierreGrupo.tableDynamic = new TableDynamic(AperturaCierreGrupo.tablePorcentajes,activity.getApplicationContext());
                    AperturaCierreGrupo.tableDynamic.addTablePorcentajes(tp,2);

                    GraficaApCiGoD grafica = new GraficaApCiGoD();
                    grafica.setActivity(activity);
                    AperturaCierreGrupo.grafica.removeAllViews();
                    AperturaCierreGrupo.grafica2.removeAllViews();
                    grafica.borrar();
                    AperturaCierreGrupo.grafica = grafica.Grafica(1,AperturaCierreGrupo.grafica,porcentajes,totalAperturas);
                    AperturaCierreGrupo.grafica2 = grafica.Grafica(11,AperturaCierreGrupo.grafica2,porcentajes,totalCierres);
                    AperturaCierreGrupo.g1=true;
                    AperturaCierreGrupo.g2=false;
                    AperturaCierreGrupo.g3=false;

                    if(dias.length()==fechas.length()){
                        for (int j=0;j<dias.length();j++){
                            switch (j){
                                case 0:
                                    AperturaCierreGrupo.f1.setText(fechas.get(0).toString());
                                    AperturaCierreGrupo.nd1.setText(dias.get(0).toString());
                                    break;
                                case 1:
                                    AperturaCierreGrupo.f2.setText(fechas.get(1).toString());
                                    AperturaCierreGrupo.nd2.setText(dias.get(1).toString());
                                    break;
                                case 2:
                                    AperturaCierreGrupo.f3.setText(fechas.get(2).toString());
                                    AperturaCierreGrupo.nd3.setText(dias.get(2).toString());
                                    break;
                                case 3:
                                    AperturaCierreGrupo.f4.setText(fechas.get(3).toString());
                                    AperturaCierreGrupo.nd4.setText(dias.get(3).toString());
                                    break;
                                case 4:
                                    AperturaCierreGrupo.f5.setText(fechas.get(4).toString());
                                    AperturaCierreGrupo.nd5.setText(dias.get(4).toString());
                                    break;
                                case 5:
                                    AperturaCierreGrupo.f6.setText(fechas.get(5).toString());
                                    AperturaCierreGrupo.nd6.setText(dias.get(5).toString());
                                    break;
                                case 6:
                                    AperturaCierreGrupo.f7.setText(fechas.get(6).toString());
                                    AperturaCierreGrupo.nd7.setText(dias.get(6).toString());
                                    break;
                            }
                        }
                    }

                    ArrayList<String> Final= new ArrayList<>();
                    for(int i=0;i<datos.length();i++){
                        Final.add(datos.get(i).toString());
                    }
                    AperturaCierreGrupo.tableDynamic=new TableDynamic(AperturaCierreGrupo.tableLayout,activity.getApplicationContext());
                    AperturaCierreGrupo.FINAL=Final;
                    AperturaCierreGrupo.tableDynamic.addData(Final,3);
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
    public static String ObtenerCuentasAbiertas(String cadena, String param) {
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
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection(token);
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getApCiGoD(Fecha,CG,Type).execute();
            response2=response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        result= response2.body().toString();

        return result;

    }

}
