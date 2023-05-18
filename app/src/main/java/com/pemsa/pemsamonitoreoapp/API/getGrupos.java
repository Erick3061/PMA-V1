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
    Parametros parametros = new Parametros();
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

        try {
            Response<JsonElement> response=parametros.Connection(strings[0]).create(InterfacesApi.class).getGrupos().execute();
            if(response.isSuccessful()){
                return response.body().toString();
            }else{
                JsonObject json=new JsonObject();
                json.addProperty("status",false);
                json.addProperty("msg",response.toString());
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
}
