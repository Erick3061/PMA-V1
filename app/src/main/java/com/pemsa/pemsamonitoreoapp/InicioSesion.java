package com.pemsa.pemsamonitoreoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pemsa.pemsamonitoreoapp.API.getSesion;
import com.pemsa.pemsamonitoreoapp.TCAP.TCAP;

public class InicioSesion extends AppCompatActivity {
    public static TextView TYCYAP,titulo,txtOlvideContra,txtRegistrarse,VersionApp;
    EditText Correo,Passsword;
    Button btn;
    String C;
    String P;
    public static Activity activity;
    getSesion sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ocultaTeclado();
        setTheme(R.style.AppTheme2);
        Darpermisos();
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_inicio_sesion);
        //hacemos la asignacion de los objetos con los del activity
        btn=(Button)findViewById(R.id.btnIniciaSesion);
        titulo=(TextView)findViewById(R.id.titulo);
        TYCYAP=(TextView)findViewById(R.id.TermYCondYAviPriv);

        new android.app.AlertDialog
                .Builder(this).setTitle("Hay una nueva actualización de esta aplicación")
                .setMessage("Todas las funciones están disponibles en nuestra nueva versión.")
                .setPositiveButton("Actualiza ahora",(dialog, which) -> {
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultaTeclado();
                //verificamos si los campos de los edit text estan vacios
                Correo=(EditText)findViewById(R.id.edtRCorreo);
                Passsword=(EditText)findViewById(R.id.edtPassword);
                if(Correo.getText().toString().trim().equalsIgnoreCase("")&&Passsword.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"DEBES INSERTAR UN CORREO Y CONTRASEÑA",Toast.LENGTH_SHORT).show();
                }else{
                    if(Correo.getText().toString().trim().equalsIgnoreCase("")){
                        Toast.makeText(getApplicationContext(),"DEBES INSERTAR UN CORREO",Toast.LENGTH_SHORT).show();
                    }else {
                        if(Passsword.getText().toString().trim().equalsIgnoreCase("")){
                            Toast.makeText(getApplicationContext(),"DEBES INSERTAR UNA CONTRASEÑA",Toast.LENGTH_SHORT).show();
                        }else{
                                sesion = new getSesion(Correo.getText().toString(),Passsword.getText().toString());
                                sesion.setActivity(activity);
                                sesion.execute();
                        }
                    }
                }
            }
        });

        TYCYAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TCAP tcap = new TCAP();
                tcap.terminosycondiciones(activity);
            }
        });
    }
    private void cerrarAplicacion() {
        new AlertDialog.Builder(this)
                .setTitle("¿Realmente desea cerrar la aplicación?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                    }
                }).show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cerrarAplicacion();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void ocultaTeclado(){
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void Darpermisos(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
        }
    }

}