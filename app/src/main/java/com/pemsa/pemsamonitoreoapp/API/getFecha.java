package com.pemsa.pemsamonitoreoapp.API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.AperturaCierre;
import com.pemsa.pemsamonitoreoapp.AperturaCierreGrupo;
import com.pemsa.pemsamonitoreoapp.EventoAlarma;
import com.pemsa.pemsamonitoreoapp.ProblemaBateria;
import com.pemsa.pemsamonitoreoapp.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class getFecha extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    int identifica;

    public int getIdentifica() {
        return identifica;
    }

    public void setIdentifica(int identifica) {
        this.identifica = identifica;
    }

    Parametros geturl = new Parametros();
    public static String JSON;
    public static  Activity activity;
    public getFecha(){

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

        if(strings[0].equals("11")){
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
        progressDialog.hide();
        progressDialog.dismiss();
        try {
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.has("status")){
                Boolean status = false;
                if(respuesta.getBoolean("status")){
                    JSONObject data = new JSONObject(respuesta.getString("data"));
                    s=data.getString("fecha");
                    if(identifica==1){
                        //Toast.makeText(activity.getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                        AperturaCierre.dt=s;
                        AperturaCierre.dt=AperturaCierre.dt.replace(" ","_");
                        AperturaCierre.dt=AperturaCierre.dt.replace("/",".");
                        AperturaCierre.dt=AperturaCierre.dt.replace(":",".");
                        //String[] dATE;
                        //dATE=mostrardatos.separar(dt.replace(".",""));
                        String[] sep;
                        String NombreDoc="RAC__"+AperturaCierre.CodigoA+"__"+AperturaCierre.dt.replace(" ","_");
                        NombreDoc=NombreDoc.replace("-","");
                        AperturaCierre.crearPDF(AperturaCierre.rows,AperturaCierre.Nombre.replace(" ","____"),AperturaCierre.Direccion,NombreDoc);
                        Toast.makeText(activity.getApplicationContext(),"PDF CREADO\nAlmacenamiento Interno/Descargas/PEMSA",Toast.LENGTH_LONG).show();
                        AperturaCierre.creado=true;
                    }
                    if(identifica==2){
                        EventoAlarma.dt=s;
                        EventoAlarma.dt=EventoAlarma.dt.replace(" ","_");
                        EventoAlarma.dt=EventoAlarma.dt.replace("/",".");
                        EventoAlarma.dt=EventoAlarma.dt.replace(":",".");
                        String NombreDoc="REA__"+EventoAlarma.CodigoA.trim()+"__"+EventoAlarma.dt.replace(" ","_");
                        NombreDoc=NombreDoc.replace("-","");
                        EventoAlarma.crearPDF(EventoAlarma.rows,EventoAlarma.Nombre,EventoAlarma.Direccion,NombreDoc);
                        Toast.makeText(activity.getApplicationContext(),"PDF CREADO\nAlmacenamiento Interno/Descargas/PEMSA",Toast.LENGTH_LONG).show();
                        EventoAlarma.creado=true;
                    }
                    if(identifica==3){
                        AperturaCierreGrupo.dt=s;
                        AperturaCierreGrupo.dt=AperturaCierreGrupo.dt.replace(" ","_");
                        AperturaCierreGrupo.dt=AperturaCierreGrupo.dt.replace("/",".");
                        AperturaCierreGrupo.dt=AperturaCierreGrupo.dt.replace(":",".");
                        String NombreDoc="RPG__"+AperturaCierreGrupo.NG.trim()+"__"+AperturaCierreGrupo.dt.replace(" ","_");
                        NombreDoc=NombreDoc.replace("-","");
                        NombreDoc=NombreDoc.replace("/","");
                        AperturaCierreGrupo.crearPDF(AperturaCierreGrupo.FINAL,AperturaCierreGrupo.NG.replace(" ","*").trim(),"ERICK aNDRADE",NombreDoc);
                        Toast.makeText(activity.getApplicationContext(),"PDF CREADO\nAlmacenamiento Interno/Descargas/PEMSA",Toast.LENGTH_LONG).show();
                        AperturaCierreGrupo.creado=false;
                    }
                    if(identifica==4){
                        ProblemaBateria.dt=s;
                        ProblemaBateria.dt=ProblemaBateria.dt.replace(" ","_");
                        ProblemaBateria.dt=ProblemaBateria.dt.replace("/",".");
                        ProblemaBateria.dt=ProblemaBateria.dt.replace(":",".");
                        String NombreDoc="RPB__"+ProblemaBateria.NG.trim()+"__"+ProblemaBateria.dt.replace(" ","_");
                        NombreDoc=NombreDoc.replace("-","");
                        NombreDoc=NombreDoc.replace("/","");
                        ProblemaBateria.crearPDF(ProblemaBateria.CSR,ProblemaBateria.CCR,ProblemaBateria.CSE,ProblemaBateria.NG.replace(" ","*").trim(),ProblemaBateria.totalCuentas,NombreDoc);
                        Toast.makeText(activity.getApplicationContext(),"PDF CREADO\nAlmacenamiento Interno/Descargas/PEMSA",Toast.LENGTH_LONG).show();
                        ProblemaBateria.creado=false;
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

    public static String ObtenerFecha(String cadena) {
        String result;
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection("Sin token");
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getfecha().execute();
            response2=response;
        } catch (IOException e) {
            Toast.makeText(activity.getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        result= response2.body().toString();

        return result;

    }

}
