package com.pemsa.pemsamonitoreoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;

import org.json.JSONObject;

public class Actividad_ApCiI extends AppCompatActivity {
    public static ConstraintLayout graficas;
    public static ImageButton pieChartButton, dountCharButton, barsChartButton;
    public static JSONObject porcentajes;
    public static String total;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        GraficaApCi grafica = new GraficaApCi();
        grafica.setActivity(activity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_eventos);
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
        //_--------------------------------------------------------------------------------------
        graficas = ( ConstraintLayout )findViewById( R.id.Eventos_Grafica );
        Toast.makeText(activity.getApplicationContext(),total,Toast.LENGTH_LONG).show();

        //grafica.Grafica(1,graficas);
    }

}