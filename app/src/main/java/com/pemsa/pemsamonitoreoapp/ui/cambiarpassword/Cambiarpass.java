package com.pemsa.pemsamonitoreoapp.ui.cambiarpassword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pemsa.pemsamonitoreoapp.InicioSesion;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.ValidacionEditText;
import com.google.android.material.navigation.NavigationView;

public class Cambiarpass extends Fragment {

    NavigationView navigationheader;
    public static TextView name,Uid,Token,pruebas;
    public static String N,token,uid,Obtenido;
    com.pemsa.pemsamonitoreoapp.ValidacionEditText validacionEditText=new ValidacionEditText();
    EditText pass,passn1,passn2;
    Button cambiar;
    public static Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ocultaTeclado();
        View root = inflater.inflate(R.layout.fragment_cambiarpass, container, false);
        //Obtener los datos del navigation--------------------------------------------------------------------------------------
        navigationheader=(NavigationView)getActivity().findViewById(R.id.nav_view);
        View view=navigationheader.getHeaderView(0);
        name=(TextView)view.findViewById(R.id.menuNombre);
        Token=(TextView)view.findViewById(R.id.menuToken);
        Uid=(TextView)view.findViewById(R.id.menuIdUsuario);
        //----------------------------------------------------------
        N=name.getText().toString().trim();
        token=Token.getText().toString().trim();
        uid=Uid.getText().toString().trim();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        pass = (EditText)view.findViewById(R.id.edtPasswordAnterior);
        passn1 = (EditText)view.findViewById(R.id.edtPasswordNueva);
        passn2 = (EditText)view.findViewById(R.id.edtPasswordNueva2);
        cambiar = (Button)view.findViewById(R.id.btnCambiarPass);
        pruebas = (TextView)view.findViewById(R.id.textww);
        activity=getActivity();

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog
                        .Builder(view.getContext()).setTitle("Aviso")
                        .setMessage("Estas funciones están disponibles en nuestra nueve versión.\n\nActualiza ahora")
                        .setNeutralButton("Descargar",(dialog, which) -> {
                        })
                        .show();
            }
        });


    }
    public static void dialogo(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater =activity.getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_cambio_de_pass,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_cambio_de_pass,null))
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
    public void ocultaTeclado(){
        View view=this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
