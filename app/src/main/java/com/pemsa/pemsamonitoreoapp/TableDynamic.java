package com.pemsa.pemsamonitoreoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private String[] separa;
    private ArrayList<String> data;
    private TableRow tableRow;
    private TextView txtCell,txtCellH,txtCellSmall,headerCell;
    private int indexC;
    private int id;
    public static TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    mostrardatos mostrardatos=new mostrardatos();

    public TableDynamic(TableLayout tableLayout, Context context) {
        this.tableLayout=tableLayout;
        this.context=context;
    }

    public void addHeader(String[] header){
        this.header=header;
        createHeader();
    }
    public void addHeader2(String[] header){
        this.header=header;
        createHeader2();
    }
    public void addSeparador(String[] Separa){
        this.separa=Separa;
        createSeparador();
    }
    public void addData(ArrayList<String> datos,int C){
        this.data=datos;
        this.id=C;
        switch (C){
            case 1:
                createDataTable();
                break;
            case 2:
                createDataTable2();
                break;
            case 3:
                createDataTable3();
                break;
            case 4:
                createDataTable4();
                break;
            case 5:
                createDataTable5();
                break;
        }

    }

    public void addTablePorcentajes(ArrayList<String> datos, int C){
        this.id=C;
        switch (C){
            case 1:
                createDataTablePocentajesBateria(datos);
                break;
            case 2:
                createDataTablePocentajesApCiGoD(datos);
                break;
        }
    }

    public void newRow(){
        tableRow=new TableRow(context);
    }

    public void newCell(){
        txtCell=new TextView(context);
        txtCell.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        txtCell.setTextSize(12);
    }

    public void newCell2(){
        txtCellSmall=new TextView(context);
        txtCellSmall.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        txtCellSmall.setTextSize(12);
    }

    public void newCellJustifyIzq(){
        txtCell=new TextView(context);
        txtCell.setGravity(Gravity.LEFT);
        txtCell.setTextSize(12);
    }

    public void newCellHeader(){
        txtCell=new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(12);
        txtCell.setTypeface(null, Typeface.BOLD);
    }

    public void newCellHeader2(){
        txtCellH=new TextView(context);
        txtCellH.setGravity(Gravity.CENTER);
        txtCellH.setTextSize(12);
        txtCellH.setTypeface(null, Typeface.BOLD);
    }

    public void createHeader(){
        indexC=0;
        newRow();
        while(indexC<header.length){
            newCellHeader();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell,newTableRowParams());
            tableRow.setBackgroundColor(Color.GRAY);
            tableRow.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        }
        tableLayout.addView(tableRow);
    }

    public void createHeader2(){
        indexC=0;
        newRow();
        while(indexC<header.length){
            newCellHeader2();
            txtCellH.setText(header[indexC++]);
            tableRow.addView(txtCellH,newTableRowParams());
            tableRow.setBackgroundColor(Color.GRAY);
            tableRow.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        }
        tableLayout.addView(tableRow);
    }

    public void createSeparador(){
        indexC=0;
        newRow();
        while(indexC<separa.length){
            newCellHeader();
            txtCell.setText(separa[indexC++]);
            tableRow.addView(txtCell,newTableRowParams());
            tableRow.setBackgroundColor(Color.WHITE);
            tableRow.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        }
        tableLayout.addView(tableRow);
    }

    public void CrearTitulo(String texto){
        newRow();
        headerCell=new TextView(context);
        headerCell.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        headerCell.setTextSize(20);
        headerCell.setText(texto);
        params.span=6;
        tableRow.addView(headerCell, newTableRowParams());
        headerCell.setLayoutParams(params);
        tableLayout.addView(tableRow);
    }

    public void createDataTable(){
        String ARRAY;
        String[] SEPARADO;
        Iterator it= data.iterator();
        int contador=1;
        while(it.hasNext()){
            newRow();
                ARRAY= (String) it.next();
                SEPARADO=separar(ARRAY);
                for (int i=0;i<SEPARADO.length;i++){
                    if(i==2){
                        String DE;
                        DE=SEPARADO[i];
                        DE.trim();
                        if("Apertura despues de activacion".equals(DE)){
                            newCellJustifyIzq();
                            txtCell.setText("Apertura despues\nde activacion");
                            tableRow.addView(txtCell, newTableRowParams());
                            tableRow.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
                        }else{
                            newCellJustifyIzq();
                            txtCell.setText(DE);
                            tableRow.addView(txtCell, newTableRowParams());
                        }

                    }else{
                        newCell();
                        txtCell.setText(SEPARADO[i].trim());
                        tableRow.addView(txtCell, newTableRowParams());
                    }
                }
            if(SEPARADO.length==5){
                newCellJustifyIzq();
                txtCell.setText("Sistema/llavero");
                tableRow.addView(txtCell, newTableRowParams());
            }
                contador++;
            if((contador%2)==0){
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
            }else {
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
            }
            tableLayout.addView(tableRow);
        }

    }

    public void createDataTable2(){
        String temp="",ARRAY;
        String[] SEPARADO;
        Iterator it= data.iterator();
        int contador=1;
        while(it.hasNext()){
            newRow();
            ARRAY= (String) it.next();
            SEPARADO=separar(ARRAY);
            boolean entro=false;
            for (int i=0;i<SEPARADO.length;i++){
                int resultado = SEPARADO[3].indexOf("Apertur");
                int resultado2 = SEPARADO[3].indexOf("Cierr");
                int resultado3 = SEPARADO[3].indexOf("zona");
                if(SEPARADO.length==6&&entro==false){
                    if(resultado!=-1||resultado2!=-1){
                        temp=SEPARADO[5];
                        SEPARADO[5]=" ";
                        SEPARADO[4]=temp;
                    }else{
                            SEPARADO[4]=" ";
                    }
                }
                if(SEPARADO.length>6&&entro==false&&resultado3==-1){
                    SEPARADO[5]=" ";
                }
                if(SEPARADO.length>6&&entro==false&&resultado3!=-1){
                    SEPARADO[4]=" ";
                }
                entro=true;
                if(i==3){
                    newCellJustifyIzq();
                    txtCell.setText(SEPARADO[i]);
                    tableRow.addView(txtCell, newTableRowParams());
                    i++;
                }
                if(i==6){
                    String juntar="";
                    int con=6;
                    while(con<SEPARADO.length){
                        juntar=juntar+" "+SEPARADO[con];
                        con++;
                    }
                    newCellJustifyIzq();
                    txtCell.setText(juntar);
                    tableRow.addView(txtCell, newTableRowParams());
                }else{
                    newCell();
                    txtCell.setText(SEPARADO[i]);
                    tableRow.addView(txtCell, newTableRowParams());
                }
            }
            if(SEPARADO.length==6){
                newCellJustifyIzq();
                txtCell.setText("Sistema/llavero");
                tableRow.addView(txtCell, newTableRowParams());
            }
            contador++;
            if((contador%2)==0){
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
            }else {
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
            }
            tableLayout.addView(tableRow);
        }
    }

    public int pinta(int contador){
        int color=0;
        if((contador%2)==0){
            color=1;
        }else {
            color=2;
        }
        return color;
    }

    public void createDataTable3(){
        String [] separado;
        String[] datos = new String[data.size()];
        int contador=0;
        Iterator it= data.iterator();
        while(it.hasNext()){
            datos[contador]=(String) it.next();
            contador++;
        }
        for(int i=0;i<datos.length;i++){
            separado=separar(datos[i]);
            newRow();
            newCellJustifyIzq();
            txtCell.setText("   " + separado[0].replace("**", " ") + "   ");
            tableRow.addView(txtCell);
            txtCell.setBackgroundResource(R.drawable.bordeladoder);
                for(int j=1;j<separado.length;j++){
                    newCell();
                    txtCell.setText(separado[j]);
                    tableRow.addView(txtCell);
                    if((j%2)==0) {
                        txtCell.setBackgroundResource(R.drawable.bordeladoder);
                    }
                }
            if(pinta(i)==1){
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
            }
            tableLayout.addView(tableRow);
        }

    }

    public void createDataTable4(){

        String ARRAY;
        String[] titulos={"FECHA","HORA","DESC.\nEVENTO","PART","N°\nUSUARIO","NOMBRE DE USUARIO"};
        String[] separa={"","","","","",""};
        String[] SEPARADO,SEPARADO2;
        Iterator it= data.iterator();
        int contador=1;
        while(it.hasNext()){
            ARRAY= (String) it.next();
            SEPARADO=separar(ARRAY);
            if(SEPARADO.length==2){
                if(SEPARADO[0].equals("NC")){
                    String[] s;
                    s=mostrardatos.separar(SEPARADO[1].replace("***ER***"," "));
                    CrearTitulo("Nombre: "+s[0].replace("."," "));
                    headerCell.setTypeface(null, Typeface.BOLD);
                    CrearTitulo("Dirección: "+s[1].replace("___"," "));
                    headerCell.setTypeface(null, Typeface.BOLD);
                }
                addHeader2(titulos);
            }else if(SEPARADO.length==1){
                if(SEPARADO[0].equals("no_hay_eventos")){
                    CrearTitulo("No hay eventos");
                    headerCell.setGravity(Gravity.CENTER);
                    headerCell.setTextSize(16);
                    addSeparador(separa);
                }
            }else if(SEPARADO.length>3){
                JSONArray Regreso = null;
                    try {
                        Regreso=new JSONArray(ARRAY);
                        for (int ii=0;ii<Regreso.length();ii++){
                            SEPARADO2=separar((String) Regreso.get(ii));
                            newRow();
                            for(int j=2;j<SEPARADO2.length;j++){
                                newCell2();
                                if(j==7){
                                    txtCellSmall.setText(SEPARADO2[j].replace("."," "));
                                    txtCellSmall.setGravity(Gravity.LEFT);
                                }else{
                                    txtCellSmall.setText(SEPARADO2[j]);
                                }
                                tableRow.addView(txtCellSmall, newTableRowParams());
                                tableRow.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
                            }
                            if(SEPARADO2.length==7){
                                newCell2();
                                txtCellSmall.setText("Sistema/llavero");
                                txtCellSmall.setGravity(Gravity.LEFT);
                                tableRow.addView(txtCellSmall, newTableRowParams());
                            }
                            contador++;
                            if((contador%2)==0){
                                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
                            }else {
                                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
                            }
                            tableLayout.addView(tableRow);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                addSeparador(separa);
            }
        }
    }

    public void createDataTable5(){

        String ARRAY,temp="";
        String[] titulos={"FECHA","HORA","PARTICIÓN","DESCRIPCION EVENTO","USUARIO","ZONA","NOMBRE"};
        String[] separa={"","","","","","",""};
        String[] SEPARADO,SEPARADO2;
        Iterator it= data.iterator();
        int contador=1;
        while(it.hasNext()){
            ARRAY= (String) it.next();
            SEPARADO=separar(ARRAY);
            if(SEPARADO.length==2){
                if(SEPARADO[0].equals("NC")){
                    String[] s;
                    params.span=7;
                    s=mostrardatos.separar(SEPARADO[1].replace("***ER***"," "));
                    CrearTitulo("Nombre: "+s[0].replace("."," "));
                    headerCell.setTypeface(null, Typeface.BOLD);
                    headerCell.setLayoutParams(params);
                    CrearTitulo("Dirección: "+s[1].replace("___"," "));
                    headerCell.setTypeface(null, Typeface.BOLD);
                    headerCell.setLayoutParams(params);
                }
                addHeader2(titulos);
            }else if(SEPARADO.length==1){
                if(SEPARADO[0].equals("no_hay_eventos")){
                    CrearTitulo("No hay eventos");
                    headerCell.setGravity(Gravity.CENTER);
                    headerCell.setTextSize(16);
                    addSeparador(separa);
                }
            }else if(SEPARADO.length>3){
                JSONArray Regreso = null;
                try {
                    Regreso=new JSONArray(ARRAY);
                    for (int ii=0;ii<Regreso.length();ii++){
                        SEPARADO2=separar((String) Regreso.get(ii));
                        newRow();
                            boolean entro=false;
                            for (int i=2;i<SEPARADO2.length;i++){
                                int resultado = SEPARADO2[5].indexOf("Apertur");
                                int resultado2 = SEPARADO2[5].indexOf("Cierr");
                                int resultado3 = SEPARADO2[5].indexOf("zona");
                                if(SEPARADO2.length==8&&entro==false){
                                    if(resultado!=-1||resultado2!=-1){
                                        temp=SEPARADO2[7];
                                        SEPARADO2[7]=" ";
                                        SEPARADO2[6]=temp;
                                    }else{
                                        SEPARADO2[6]=" ";
                                    }
                                }
                                if(SEPARADO2.length>8&&entro==false&&resultado3==-1){
                                    SEPARADO2[7]=" ";
                                }
                                if(SEPARADO2.length>8&entro==false&&resultado3!=-1){
                                    SEPARADO2[6]=" ";
                                }
                                entro=true;
                                if(i==5){
                                    newCell2();
                                    txtCellSmall.setText(SEPARADO2[i]);
                                    tableRow.addView(txtCellSmall, newTableRowParams());
                                    i++;
                                }
                                if(i==8){
                                    String juntar="";
                                    int con=8;
                                    while(con<SEPARADO2.length){
                                        juntar=juntar+" "+SEPARADO2[con];
                                        con++;
                                    }
                                    newCell2();
                                    txtCellSmall.setText(juntar);
                                    tableRow.addView(txtCellSmall, newTableRowParams());
                                }else{
                                    newCell2();
                                    txtCellSmall.setText(SEPARADO2[i]);
                                    tableRow.addView(txtCellSmall, newTableRowParams());
                                }
                            }
                            if(SEPARADO2.length==8){
                                newCell2();
                                txtCellSmall.setText("Sistema/llavero");
                                tableRow.addView(txtCellSmall, newTableRowParams());
                            }
                            contador++;
                            if((contador%2)==0){
                                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
                            }else {
                                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
                            }
                            tableLayout.addView(tableRow);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addSeparador(separa);
            }
        }
    }

    public void createDataTablePocentajesBateria( ArrayList<String> datos ){

        newRow();
            newCell();
            txtCell.setText("ESTADO");
            txtCell.setTextColor(ContextCompat.getColor(context,R.color.blanco));
            txtCell.setTypeface(null, Typeface.BOLD);
            tableRow.addView(txtCell, newTableRowParams());

            newCell();
            txtCell.setText("CANTIDAD");
            txtCell.setTextColor(ContextCompat.getColor(context,R.color.blanco));
            txtCell.setTypeface(null,Typeface.BOLD);
            //txtCell.setMaxWidth(150);
            tableRow.addView(txtCell, newTableRowParams());

            newCell();
            txtCell.setText("PORCENTAJE");
            txtCell.setTextColor(ContextCompat.getColor(context,R.color.blanco));
            txtCell.setTypeface(null,Typeface.BOLD);
            //txtCell.setMaxWidth(150);
            tableRow.addView(txtCell, newTableRowParams());

            tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.backgroud));
        tableLayout.addView(tableRow);

        String[] SEPARADO;
        String ARRAY;
        Iterator it= datos.iterator();
        int contador = 1;
        while(it.hasNext()){
            newRow();
            ARRAY= (String) it.next();
            SEPARADO=separar(ARRAY);
            newCell();
            txtCell.setText(SEPARADO[0].replace("_"," "));
            tableRow.addView(txtCell, newTableRowParams());

            newCell();
            txtCell.setText(SEPARADO[1]);
            tableRow.addView(txtCell, newTableRowParams());

            newCell();
            txtCell.setText(SEPARADO[2]);
            tableRow.addView(txtCell, newTableRowParams());

            contador++;
            if((contador%2)==0){
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
            }else {
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
            }
            tableLayout.addView(tableRow);
        }

    }

    public void createDataTablePocentajesApCiGoD( ArrayList<String> datos ){

        newRow();
        newCell();
        txtCell.setText("EVENTO");
        txtCell.setTextColor(ContextCompat.getColor(context,R.color.blanco));
        txtCell.setTypeface(null, Typeface.BOLD);
        tableRow.addView(txtCell, newTableRowParams());

        newCell();
        txtCell.setText("CANTIDAD");
        txtCell.setTextColor(ContextCompat.getColor(context,R.color.blanco));
        txtCell.setTypeface(null,Typeface.BOLD);
        //txtCell.setMaxWidth(150);
        tableRow.addView(txtCell, newTableRowParams());

        newCell();
        txtCell.setText("PORCENTAJE");
        txtCell.setTextColor(ContextCompat.getColor(context,R.color.blanco));
        txtCell.setTypeface(null,Typeface.BOLD);
        //txtCell.setMaxWidth(150);
        tableRow.addView(txtCell, newTableRowParams());

        tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.backgroud));
        tableLayout.addView(tableRow);

        String[] SEPARADO;
        String ARRAY;
        Iterator it= datos.iterator();
        int contador = 1;
        while(it.hasNext()){
            newRow();
            ARRAY= (String) it.next();
            SEPARADO=separar(ARRAY);
            newCell();
            txtCell.setText(SEPARADO[0].replace("_"," "));
            tableRow.addView(txtCell, newTableRowParams());

            newCell();
            txtCell.setText(SEPARADO[1]);
            tableRow.addView(txtCell, newTableRowParams());

            newCell();
            txtCell.setText(SEPARADO[2]);
            tableRow.addView(txtCell, newTableRowParams());

            contador++;
            if((contador%2)==0){
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
            }else {
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
            }
            tableLayout.addView(tableRow);
        }

    }

    public void createTablesPB(JSONArray datos,int color){
        String[] encabezado={ "#","Nombre cuenta","Eventos recibidos" };
        addHeader(encabezado);
        String linea;
        switch (color){
            case 1:
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.grojo));
                break;
            case 2:
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gamarillo));
                break;
            case 3:
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gverde));
                break;
        }
        int contador = 1;
        for (int i=0;i<datos.length();i++){
            newRow();
            try {
                linea = datos.get(i).toString().trim();
                String[] separado = mostrardatos.separar(linea);
                if( separado.length == 4 ){
                    newCell();
                    txtCell.setText(""+contador);
                    txtCell.setMinWidth(200);
                    tableRow.addView(txtCell, newTableRowParams());

                    newCell();
                    txtCell.setText(separado[2].replace("***"," "));
                    txtCell.setMinWidth(400);
                    tableRow.addView(txtCell, newTableRowParams());

                    newCell();
                    txtCell.setText(separado[3]);
                    txtCell.setMinWidth(200);
                    tableRow.addView(txtCell, newTableRowParams());
                }
                if( separado.length == 3 ){
                    newCell();
                    txtCell.setText(""+contador);
                    txtCell.setMinWidth(200);
                    tableRow.addView(txtCell, newTableRowParams());

                    newCell();
                    txtCell.setText(separado[2].replace("***"," "));
                    txtCell.setMinWidth(400);
                    tableRow.addView(txtCell, newTableRowParams());

                    newCell();
                    txtCell.setText("0");
                    txtCell.setMinWidth(200);
                    tableRow.addView(txtCell, newTableRowParams());
                }
            } catch (JSONException e) {
                Toast.makeText(context.getApplicationContext(),"Error: \n" + e.getMessage(),Toast.LENGTH_LONG).show();
            }
            contador++;
            if((contador%2)==0){
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
            }else {
                tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
            }
            tableLayout.addView(tableRow);
        }


        /*
        for (int i=0;i<datos.length();i++) {
            try {
                data.add(datos.get(i).toString().trim());
            } catch (JSONException e) {
                Toast.makeText(context.getApplicationContext(), "Error: \n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        newRow();
        newCell();
        txtCell.setText(data.toString());
        tableRow.addView(txtCell, newTableRowParams());
        tableLayout.addView(tableRow);
        */

    }

    public TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params=new TableRow.LayoutParams();
        params.setMargins(1,1,30,1);
        params.weight=1;
        return params;
    }
    public String[] separar(String Nombre){
        String []  bar = Nombre. split(" ");
        return bar;
    }



}
