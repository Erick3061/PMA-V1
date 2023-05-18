package com.pemsa.pemsamonitoreoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pemsa.pemsamonitoreoapp.TCAP.TCAP;

public class  registrarse extends AppCompatActivity {

    public static TextView txtIC,Direc,pruebas;
    public static EditText Nombre,Apellidop,Apellidom,Correo,NombreCuentaoCadena,DirecCuenta;
    public static CheckBox TerminosAndcondiciones;
    public static Button Registrar;
    public static FloatingActionButton ayuda1;
    public static  ValidacionEditText  objValidar;
    public static mostrardatos mostrardatos=new mostrardatos();
    public static Activity activity;
    public static  String name="",Direccion="",email="",CuentaNombre="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        //asociamos los objetos
        txtIC=(TextView)findViewById(R.id.txtIC);
        Direc=(TextView)findViewById(R.id.tex6);
        pruebas = (TextView)findViewById(R.id.pruebasRegistro);
        Registrar=(Button)findViewById(R.id.btnCregistrar);
        TerminosAndcondiciones=(CheckBox)findViewById(R.id.cbTermAndCond);
        Nombre=(EditText)findViewById(R.id.edtAUnombre);
        Apellidop=(EditText)findViewById(R.id.edtCNombrePemsa);
        Apellidom=(EditText)findViewById(R.id.edtRApellidoM);
        Correo=(EditText)findViewById(R.id.edtRCorreo);
        DirecCuenta=(EditText)findViewById(R.id.edtRextra2);
        NombreCuentaoCadena=(EditText)findViewById(R.id.edtRextra);
        ayuda1=(FloatingActionButton)findViewById(R.id.registroayuda);

        activity=this;

        ayuda1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogayuda1();
            }
        });
        TerminosAndcondiciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TCAP tcap = new TCAP();
                tcap.terminosycondiciones(activity);
            }

        });
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ban=0,filtro1=0,filtro2=0;
                if(Nombre.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(v.getContext(),"DEBES INGRESAR UN NOMBRE",Toast.LENGTH_SHORT).show();
                }else {
                    if(Apellidop.getText().toString().trim().equalsIgnoreCase("")){
                        Toast.makeText(v.getContext(),"DEBES INGRESAR UN APELLIDO PATERNO",Toast.LENGTH_SHORT).show();
                    }else{
                        if(Apellidom.getText().toString().trim().equalsIgnoreCase("")){
                            Toast.makeText(v.getContext(),"DEBES INGRESAR UN APELLIDO MATERNO",Toast.LENGTH_SHORT).show();
                        }else {
                            if(Correo.getText().toString().trim().equalsIgnoreCase("")){
                                Toast.makeText(v.getContext(),"DEBES INGRESAR UN CORREO",Toast.LENGTH_SHORT).show();
                            }else{
                                    if(NombreCuentaoCadena.getText().toString().trim().equalsIgnoreCase("")){
                                        Toast.makeText(v.getContext(),"DEBES INGRESAR UN NOMBRE DE CUENTA",Toast.LENGTH_SHORT).show();
                                    }else{
                                        //todos los datos llenados
                                        name = Nombre.getText().toString().trim()+ " " +Apellidop.getText().toString().trim()+ " " +Apellidom.getText().toString().trim();
                                        email = Correo.getText().toString().trim();
                                        CuentaNombre = NombreCuentaoCadena.getText().toString().trim();
                                        ban=1;
                                    }
                            }
                        }
                    }
                }
                if(ban==1){
                    objValidar=new ValidacionEditText();
                    if (objValidar.isEmail(Correo.getText().toString().trim())) {
                        filtro1=1;
                    } else {
                        Toast.makeText(getApplicationContext(), "Correo NO Valido", Toast.LENGTH_SHORT).show();
                    }
                }
                if(ban==1&&filtro1==1){
                    if(TerminosAndcondiciones.isChecked()){
                            InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(Registrar.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                        //-------------------------------------
                        if(!DirecCuenta.getText().toString().trim().equalsIgnoreCase("")){
                            Direccion=DirecCuenta.getText().toString();
                        }else{
                            Direccion="Sin direccion";
                        }


                        //Toast.makeText(activity.getApplicationContext(),"Datos Enviados",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(v.getContext(),"DEBES ACEPTAR LOS TERMINOS Y CONDICIONES", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        txtIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), InicioSesion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent,0);
            }
        });
    }
    public static void dialogProceso(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater =activity.getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_despues_de_registro,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_despues_de_registro,null))
                .setCancelable(false)
                .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent (activity.getApplicationContext(), InicioSesion.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivityForResult(intent,0);
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }
    private void dialogayuda1(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_ayuda_registro,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_ayuda_registro,null))
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }

}