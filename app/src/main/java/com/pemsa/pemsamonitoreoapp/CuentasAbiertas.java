package com.pemsa.pemsamonitoreoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pemsa.pemsamonitoreoapp.API.getCuentasAbiertas;

import java.util.ArrayList;
import java.util.Iterator;

public class CuentasAbiertas extends AppCompatActivity {
    public static ArrayList<formato> LisDatos = new ArrayList<>();
    public static ArrayList<formato> LisAbierto = new ArrayList<>();
    public static ArrayList<formato> LisCerrado = new ArrayList<>();
    public static ArrayList<formato> LisDesconocido = new ArrayList<>();
    public static ArrayList<formato> LisTodas = new ArrayList<>();

    public static Activity activity;
    public static RecyclerView recycler;
    public static RecyclerView recyclerFormato;
    public static TextView nombreGrupo,textoextra,totalA,totalC,totalD,totalT;
    public static FloatingActionButton recargar;
    public static Button btnAbiertas, btnCerradas, btnDesconocido, btnTodas;

    String Correo,token,Uid,NG,CG,Type;
    getCuentasAbiertas cuentasAbiertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas_abiertas);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //para colocar la accion hacia atras------------------------------------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //se coloca el icono de atras o flecha
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_atras));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activity=this;
        //--------------------------------------------------------------------------------
        //instancias
        nombreGrupo=(TextView)findViewById(R.id.nombreGrupo);
        textoextra=(TextView)findViewById(R.id.texto2);
        recargar=(FloatingActionButton)findViewById(R.id.RecargarCuentasAbiertas);

        btnAbiertas = ( Button )findViewById( R.id.CaBtnAbiertas );
        btnCerradas = ( Button )findViewById( R.id.CaBtnCerradas );
        btnDesconocido = ( Button )findViewById( R.id.CaBtnDesconocido );
        btnTodas = ( Button )findViewById( R.id.CaBtnTodas );

        totalA = ( TextView )findViewById( R.id.CaTextAbiertas );
        totalC = ( TextView )findViewById( R.id.CaTextCerradas );
        totalD = ( TextView )findViewById( R.id.CaTextDesconocido );
        totalT = ( TextView )findViewById( R.id.CaTextTodas );

        //---------------------------------------------------------------------------------
        //OBTENEMOS LOS DATOS QUE SE MANDAROIN DEL CONSULTASFRAGMENT-------------------------------------------------------------------------------------------------------------------------
        Correo=getIntent().getStringExtra("Correo");
        token=getIntent().getStringExtra("Token");
        Uid=getIntent().getStringExtra("Uid");
        NG=getIntent().getStringExtra("NG");
        CG=getIntent().getStringExtra("CG");
        Type=getIntent().getStringExtra("Type");


        //------------------------------------------------------------------------------------------------------------------------------------
        //se hacen la consultas
        recycler=(RecyclerView)findViewById(R.id.idRecicler2);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        LisDatos= new ArrayList<>();
        recyclerFormato=(RecyclerView)findViewById(R.id.idRecicler2);
        recyclerFormato.setLayoutManager(new LinearLayoutManager(this));

        cuentasAbiertas = new getCuentasAbiertas();
        cuentasAbiertas.setActivity(activity);
        cuentasAbiertas.execute("1",token+"___ESP___"+CG+"___ESP___"+Type);
        //Toast.makeText(activity.getApplicationContext(),"entro",Toast.LENGTH_LONG).show();

        //------------------------------------------------------------------------------------------------------------------------------------
        recargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        //------------------------------------------------------------------------------------------------------------------------------------
        btnAbiertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterFormato adapterFormato= new AdapterFormato(CuentasAbiertas.LisAbierto);
                recyclerFormato.setAdapter(adapterFormato);
            }
        });

        btnCerradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterFormato adapterFormato= new AdapterFormato(CuentasAbiertas.LisCerrado);
                recyclerFormato.setAdapter(adapterFormato);
            }
        });

        btnDesconocido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterFormato adapterFormato= new AdapterFormato(CuentasAbiertas.LisDesconocido);
                recyclerFormato.setAdapter(adapterFormato);
            }
        });

        btnTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdapterFormato adapterFormato= new AdapterFormato(CuentasAbiertas.LisTodas);
                recyclerFormato.setAdapter(adapterFormato);
            }
        });

    }
    @SuppressLint("SetTextI18n")
    public static void  llenarFormato(ArrayList lista){
        com.pemsa.pemsamonitoreoapp.mostrardatos mostrardatos=new mostrardatos();
        LisDatos.clear();
        LisAbierto.clear();
        LisCerrado.clear();
        LisDesconocido.clear();
        LisTodas.clear();
        String ARRAY;
        String[] SEPARADO;
        int tipoImg=0;
        Iterator it= lista.iterator();
        while (it.hasNext()){
            ARRAY= (String) it.next();
            SEPARADO=mostrardatos.separar(ARRAY);
            switch (SEPARADO[1].replace("."," ")){
                case "Abierto":
                    tipoImg=1;
                    LisAbierto.add(new formato("PARTICIÓN: "+SEPARADO[0],SEPARADO[1],""+SEPARADO[2],tipoImg));
                break;
                case "Cerrado":
                    tipoImg=2;
                    LisCerrado.add(new formato("PARTICIÓN: "+SEPARADO[0],SEPARADO[1],""+SEPARADO[2],tipoImg));
                    break;
                case "Sin actividad":
                    tipoImg=3;
                    LisDesconocido.add(new formato("PARTICIÓN: "+SEPARADO[0],SEPARADO[1].replace("."," "),""+SEPARADO[2],tipoImg));
                    break;
            }
            LisDatos.add(new formato("PARTICIÓN: "+SEPARADO[0],SEPARADO[1],""+SEPARADO[2],tipoImg));
            LisTodas.add(new formato("PARTICIÓN: "+SEPARADO[0],SEPARADO[1],""+SEPARADO[2],tipoImg));

            totalA.setText("T: "+ LisAbierto.size());
            totalC.setText("T: "+ LisCerrado.size());
            totalD.setText("T: "+ LisDesconocido.size());
            totalT.setText("T: "+ LisTodas.size());

        }
    }

}