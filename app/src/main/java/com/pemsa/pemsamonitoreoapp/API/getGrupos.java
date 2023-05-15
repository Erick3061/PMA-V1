package com.pemsa.pemsamonitoreoapp.API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pemsa.pemsamonitoreoapp.API.interfaces.InterfacesApi;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.ui.consultas.ConsutasFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Response;
import retrofit2.Retrofit;

public class getGrupos extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    Parametros geturl = new Parametros();
    public static String JSON;
    Activity activity;
    public getGrupos (){

    }
    int identificador;

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
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
            regreso = ObtenerCuentas(url,strings[1]);
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
                JSONObject data = new JSONObject(respuesta.getString("data"));
                JSONArray cuentasObj = new JSONArray(data.getString("cuentas"));
                switch(getIdentificador()){
                    case 1:
                        if(respuesta.getBoolean("status")){

                            ConsutasFragment.grupos=null;
                            ConsutasFragment.grupos=cuentasObj;
                            ArrayList rows=new ArrayList();
                            String name="";
                            for (int i=0;i<cuentasObj.length();i++){
                                JSONObject cuenta =new JSONObject(cuentasObj.get(i).toString());
                                name = cuenta.getString("name");
                                rows.add(name.trim());
                            }
                            if(!rows.isEmpty()) {
                                ConsutasFragment.ObtenerGrupos(rows);
                            }else{
                                rows.clear();
                                ConsutasFragment.ObtenerGrupos(rows);
                            }
                        }
                    break;
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

    public static String ObtenerCuentas(String cadena, String param) {

        String result = "",token="",Uid="";
        String[] separado = param.split("___ESP___");
        token=separado[1];
        Uid=separado[0];
        Parametros parametros = new Parametros();
        Retrofit retrofit = parametros.Connection(token);
        InterfacesApi api = retrofit.create(InterfacesApi.class);
        Response<JsonElement> response2 = null;
        try {
            Response<JsonElement> response = api.getGrupos(Uid).execute();
            response2=response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        result= response2.body().toString();
        return result;

    }

}
