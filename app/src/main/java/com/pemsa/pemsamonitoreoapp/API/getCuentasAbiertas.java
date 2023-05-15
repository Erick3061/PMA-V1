package com.pemsa.pemsamonitoreoapp.API;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
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
    Parametros geturl = new Parametros();
    public static String JSON;
    public static Activity activity;
    public static Response<JsonElement> response2 = null;
    public getCuentasAbiertas (){

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
        //CuentasAbiertas.nombreGrupo.setText(s);
        try {
            JSONObject respuesta = new JSONObject(s);
            if(respuesta.has("status")){
                Boolean status = false;
                if(respuesta.getBoolean("status")){
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
        Parametros parametros= new Parametros();
        Retrofit retrofit = parametros.Connection(token);
        InterfacesApi api = retrofit.create(InterfacesApi.class);

        try {
            Response<JsonElement> response =api.getCuentasAbiertas(Fecha,CG,Type).execute();
            response2=response;
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
