package com.pemsa.pemsamonitoreoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.pemsa.pemsamonitoreoapp.API.getEventos;
import com.pemsa.pemsamonitoreoapp.API.getRepA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class reporteAvanzado extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static TextView dateFi,dateFf,nombre,pruebas;
    public static Button btnFi,btnFf,CONSULTAR;
    public static String Cuentas,Nombre,ObtenSpinner,JSON,token;
    public static int ban=0,diaLimite=0,mesLimite=0,anioLimite=0;
    public static mostrardatos mostrardatos=new mostrardatos();
    public static TableLayout tableLayout;
    public static ArrayList rows=new ArrayList();
    public static ArrayList<String> reportes=new ArrayList<>();
    public static String Fecha_inicio,Fecha_final;
    public static Activity activity;
    public static boolean isActivo=false,creapdf=false,creado=false;
    public static Spinner Lreportes;
    public static String a="";//Web Services
    public static ArrayList<String> CodigoCuentas= new ArrayList<>();//codigos de cuenta
    public static ArrayList<String> ResultadoConsulta= new ArrayList<>();
    public static String[] separado;
    public static RecyclerView imprime;
    public static ProgressDialog progressDialog,progressDialog2;
    public static TableDynamic tableDynamic;
    String[] CuentasMandadas;
    Integer[] Codigos;
    ArrayList<Integer> CodCtas;
    getRepA hilo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CodigoCuentas.clear();
        isActivo=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_avanzado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //para colocar la accion hacia atras------------------------------------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity=this;
        //se coloca el icono de atras o flecha
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_atras));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //OBTENEMOS LOS DATOS QUE SE MANDAROIN DEL CONSULTASFRAGMENT-------------------------------------------------------------------------------------------------------------------------
        Cuentas = getIntent().getStringExtra("Cuentas");
        token = getIntent().getStringExtra("Token");
        JSON = getIntent().getStringExtra("JSON");
        CuentasMandadas= Cuentas.split(" ");
        CodCtas=new ArrayList<>();
       // Codigos= new Integer[CuentasMandadas.length];
        try {
            JSONArray cuentas = new JSONArray(JSON);
            String Cuentas;

            for(int i=0;i<CuentasMandadas.length;i++){
                for (int j=0;j<cuentas.length();j++){
                    JSONObject cuenta = new JSONObject(cuentas.get(j).toString());
                    if(CuentasMandadas[i].replace(".", " ").equals((cuenta.getString("name")).trim())){
                        CodCtas.add(Integer.parseInt(cuenta.getString("codeMW").trim()));
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        reportes.clear();
        reportes.add("APERTURA/CIERRE");
        reportes.add("EVENTO DE ALARMA");
        Lreportes =(Spinner)findViewById(R.id.CuentasRA);
        pruebas =(TextView)findViewById(R.id.pruebas);
        imprime =(RecyclerView)findViewById(R.id.imprimirRA);
        imprime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        //instancias
        tableLayout=(TableLayout)findViewById(R.id.tableRA);
        tableDynamic=new TableDynamic(tableLayout,activity.getApplicationContext());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, reportes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Lreportes.setAdapter(arrayAdapter);

        Lreportes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isActivo=false;
                creapdf=false;
                creado=false;
                mostrardatos.borrarTabla(tableLayout);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(activity.getApplicationContext(),"presionasdo3",Toast.LENGTH_LONG).show();
            }
        });

        Calendar calendar= Calendar.getInstance();
        Date fecha= new Date();
        calendar.setTime(fecha);


        String date = calendar.get(Calendar.YEAR)+"-"+
                String.format("%2s",calendar.get(Calendar.MONTH)+1).replace(' ','0')+"-"+
                calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DAY_OF_MONTH,-30);

        String date2 = calendar.get(Calendar.YEAR)+"-"+
                String.format("%2s",calendar.get(Calendar.MONTH)+1).replace(' ','0')+"-"+
                calendar.get(Calendar.DAY_OF_MONTH);

        diaLimite = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        mesLimite = Calendar.getInstance().get(Calendar.MONTH);
        anioLimite = Calendar.getInstance().get(Calendar.YEAR);

        dateFi=(TextView)findViewById(R.id.txtFIRA);
        dateFf=(TextView)findViewById(R.id.txtFFRA);
        btnFi=(Button)findViewById(R.id.btnFiRA);
        btnFf=(Button)findViewById(R.id.btnFfRA);
        dateFf.setText(date);
        dateFi.setText(date2);
        //ACTION PARA CAMBIAR INTERVALO DE FECHA INICIO
        btnFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verCalendario(1);
                isActivo=false;
                creapdf=false;
                creado=false;
                mostrardatos.borrarTabla(tableLayout);
                ban=0;
            }
        });
        //ACTION PARA CAMBIAR INTERVALO DE FECHA FINAL
        btnFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verCalendario(2);
                isActivo=false;
                creapdf=false;
                creado=false;
                mostrardatos.borrarTabla(tableLayout);
                ban=1;
            }
        });

        CONSULTAR=(Button)findViewById(R.id.hacerconsultaRA);

        CONSULTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActivo){
                    ResultadoConsulta.clear();
                    ObtenSpinner = Lreportes.getSelectedItem().toString();
                    if(ObtenSpinner.equals("APERTURA/CIERRE")){
                        hilo=new getRepA(CodCtas,dateFi.getText().toString(),dateFf.getText().toString(),1);
                        hilo.setActivity(activity);
                        hilo.execute(token);
                    }else{
                        hilo=new getRepA(CodCtas,dateFi.getText().toString(),dateFf.getText().toString(),2);
                        hilo.setActivity(activity);
                        hilo.execute(token);
                    }
                    //isActivo=true;
                }else {
                    ///pruebas.setText(Cuentas);
                }
            }
        });

        creado=false;

    }

    private void verCalendario(int var){
        if (var==1){
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    this,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH)-1,
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        }
        if(var==2){
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    this,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month=(month+1);
        String mes="",dia="";
        int completa,completa2;
        completa=month;
        if(completa<=9){
            mes="0"+completa;
        }else{
            mes=String.valueOf(month);
        }
        completa2=dayOfMonth;
        if(completa2<=9){
            dia="0"+completa2;
        }else{
            dia=String.valueOf(dayOfMonth);
        }

        if(ban==0){
            String date=year+"-"+mes+"-"+dia;
            dateFi.setText(date);
            if(year<anioLimite){
                dateFi.setText("error");
                //dateFi.setText(date);
            }else{
                if(month<mesLimite){
                    dateFi.setTextColor(super.getColor(R.color.rojo));
                    dateFi.setText("mes error");
                    //dateFi.setText(date);
                }else {
                    dateFi.setTextColor(super.getColor(R.color.azul));
                    dateFi.setText(date);
                }
            }
        }
        if(ban==1){
            String date=year+"-"+mes+"-"+dia;
            dateFf.setText(date);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}