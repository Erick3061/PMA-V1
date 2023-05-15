package com.pemsa.pemsamonitoreoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pemsa.pemsamonitoreoapp.API.olvidePass;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

public class olvide_contra extends AppCompatActivity {
    public static TextView txtIC, txtIC2;
    public static EditText correo;
    public static Button btnSend;
    public static ValidacionEditText  objValidar;
    public static mostrardatos mostrardatos=new mostrardatos();
    public static String[] separado;

    public static Activity activity;
    olvidePass hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ocultaTeclado();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contra);

        activity=this;
        txtIC = (TextView) findViewById(R.id.txtIC);
        txtIC2 = (TextView) findViewById(R.id.txtIC2);
        btnSend=(Button)findViewById(R.id.btnEnviar);
        correo=(EditText)findViewById(R.id.edtOlvideCorreo);
        //creamos las actividades de inicar sesion de recorde mi contraseña y tengo una cuenta
        txtIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultaTeclado();
                Intent intent=new Intent(view.getContext(), InicioSesion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent,0);
            }
        });
        txtIC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultaTeclado();
                Intent intent=new Intent(view.getContext(),InicioSesion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent,0);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ocultaTeclado();
                if(correo.getText().toString().trim().equalsIgnoreCase("")){
                    Toast.makeText(v.getContext(), "DEBE INGREASAR UN CORREO", Toast.LENGTH_SHORT).show();
                }else{
                    //vaidar el correo
                    objValidar=new ValidacionEditText();
                    if (objValidar.isEmail(correo.getText().toString().trim())) {
                        //consumir aqui .....
                        hilo = new olvidePass();
                        hilo.setActivity(activity);
                        hilo.execute("1",correo.getText().toString().trim());

                    } else {
                        Toast.makeText(getApplicationContext(), "Correo NO Valido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public void ocultaTeclado(){
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    public static void dialogo(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater =activity.getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_cambio_de_pass2,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_cambio_de_pass2,null))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent irAIniciosesion = new Intent(activity.getApplicationContext(), InicioSesion.class);
                        irAIniciosesion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivityForResult(irAIniciosesion,0);
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }
}