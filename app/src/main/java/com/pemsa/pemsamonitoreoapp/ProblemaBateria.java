package com.pemsa.pemsamonitoreoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.pemsa.pemsamonitoreoapp.API.getCuentasAbiertas;
import com.pemsa.pemsamonitoreoapp.API.getFecha;
import com.pemsa.pemsamonitoreoapp.API.getProblemaBateria;
import com.pemsa.pemsamonitoreoapp.API.models.Report;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaPB;
import com.pemsa.pemsamonitoreoapp.databinding.ActivityProblemaBateriaBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ProblemaBateria extends AppCompatActivity {
    public static JSONArray CSR=new JSONArray();
    public static JSONArray CCR=new JSONArray();
    public static JSONArray CSE=new JSONArray();

    public static TextView NombreGrupo,TotalCu;
    public static TableLayout tablePorcentajes, tableCSR, tableCCR, tableCSE;
    public static String Correo,token,Uid,NG,CG,Type;
    public static Button btnCSR, btnCCR, btnCSE;
    public static FloatingActionButton PDF;
    public static String dt;
    public static Font font,fuenteTE,fontDatos;
    public static Image image, image2;
    public static Activity activity;
    public static String NOMBRE_CARPETA_APP = "PEMSA";

    //Graf
    public static Switch MostrarGraficas;
    public static ConstraintLayout grafica;
    public static LinearLayout.LayoutParams lp;
    public static LinearLayout.LayoutParams lp2;
    public static LinearLayout linearLayoutGraf,llcsr, llccr, llcse;
    public static ImageButton pieChartButton, barChartButton, dountCharButton;
    public static JSONObject porcentajes;
    public static String totalCuentas;
    public static boolean creado=false;
    public static boolean g1=false, g2=false, g3=false;


    public static ArrayList<String> tp;

    public static BarChart barChart;
    public static PieChart pieChart;

    public static BarChart getBarChart() {
        return barChart;
    }

    public static void setBarChart(BarChart barChart) {
        ProblemaBateria.barChart = barChart;
    }

    public static PieChart getPieChart() {
        return pieChart;
    }

    public static void setPieChart(PieChart pieChart) {
        ProblemaBateria.pieChart = pieChart;
    }

    public static TableDynamic tableDynamic;

    getFecha hilo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problema_bateria);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instancias
        NombreGrupo = ( TextView )findViewById( R.id.PB_Nombre );
        TotalCu = ( TextView )findViewById( R.id.PB_Total );
        btnCSR = ( Button )findViewById( R.id.PB_btnCSR );
        btnCCR = ( Button )findViewById( R.id.PB_btnCCR );
        btnCSE = ( Button )findViewById( R.id.PB_btnCSE );

        //instancias
        activity=this;
        GraficaPB creaGrafica = new GraficaPB();
        creaGrafica.setActivity(activity);
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

        //OBTENEMOS LOS DATOS QUE SE MANDAROIN DEL CONSULTASFRAGMENT-------------------------------------------------------------------------------------------------------------------------
        Correo=getIntent().getStringExtra("Correo");
        token=getIntent().getStringExtra("Token");
        Uid=getIntent().getStringExtra("Uid");
        NG=getIntent().getStringExtra("NG");
        CG=getIntent().getStringExtra("CG");
        Type=getIntent().getStringExtra("Type");

        PDF=(FloatingActionButton)findViewById(R.id.PB_DescargarPDF);
        //graficas
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp2 = new LinearLayout.LayoutParams(0, 0);
        grafica = ( ConstraintLayout )findViewById( R.id.PB_Grafica);
        linearLayoutGraf = (LinearLayout)findViewById( R.id.PB_LinearLayoutGraficas );
        pieChartButton = ( ImageButton )findViewById( R.id.PB_pieButtonApCi);
        dountCharButton = ( ImageButton )findViewById( R.id.PB_dountButtonApCi);
        barChartButton = ( ImageButton )findViewById( R.id.PB_barButtonApCi );
        MostrarGraficas = ( Switch )findViewById( R.id.PB_MostrarGrafica );
        tablePorcentajes = ( TableLayout )findViewById(R.id.PB_table_Porcentajes);
        tableCSR = ( TableLayout )findViewById(R.id.PB_table_CSR);
        tableCCR = ( TableLayout )findViewById(R.id.PB_table_CCR);
        tableCSE = ( TableLayout )findViewById(R.id.PB_table_CSE);
        llcsr = ( LinearLayout )findViewById( R.id.PB_llTCSR );
        llccr = ( LinearLayout )findViewById( R.id.PB_llTCCR );
        llcse = ( LinearLayout )findViewById( R.id.PB_llTCSE );

        ArrayList<Integer> accounts= new ArrayList<Integer>();
        accounts.add(Integer.parseInt(CG));
        getProblemaBateria problemaBateria =new getProblemaBateria(new Report(accounts,Integer.parseInt(Type)));
        problemaBateria.setActivity(activity);
        problemaBateria.execute(token);

        linearLayoutGraf.setLayoutParams(lp2);

        btnCSR.setText("ocultar tabla");
        btnCCR.setText("ocultar tabla");
        btnCSE.setText("ocultar tabla");

        btnCSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCSR.getText() == "ocultar tabla"){
                    llcsr.setLayoutParams(lp2);
                    //Toast.makeText(activity.getApplicationContext(),"Se oculta tabla",Toast.LENGTH_LONG).show();
                    btnCSR.setText("mostrar tabla");
                }else{
                    llcsr.setLayoutParams(lp);
                    llcsr.setPadding(15,15,15,5);
                    //Toast.makeText(activity.getApplicationContext(),"Se muestra tabla",Toast.LENGTH_LONG).show();
                    btnCSR.setText("ocultar tabla");
                }
            }
        });
        btnCCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCCR.getText() == "ocultar tabla"){
                    llccr.setLayoutParams(lp2);
                    //Toast.makeText(activity.getApplicationContext(),"Se oculta tabla",Toast.LENGTH_LONG).show();
                    btnCCR.setText("mostrar tabla");
                }else{
                    llccr.setLayoutParams(lp);
                    llccr.setPadding(15,15,15,5);
                    //Toast.makeText(activity.getApplicationContext(),"Se muestra tabla",Toast.LENGTH_LONG).show();
                    btnCCR.setText("ocultar tabla");
                }
            }
        });
        btnCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnCSE.getText() == "ocultar tabla"){
                    llcse.setLayoutParams(lp2);
                    //Toast.makeText(activity.getApplicationContext(),"Se oculta tabla",Toast.LENGTH_LONG).show();
                    btnCSE.setText("mostrar tabla");
                }else{
                    llcse.setLayoutParams(lp);
                    llcse.setPadding(15,15,15,5);
                    //Toast.makeText(activity.getApplicationContext(),"Se muestra tabla",Toast.LENGTH_LONG).show();
                    btnCSE.setText("ocultar tabla");
                }
            }
        });
        creado=false;
        MostrarGraficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MostrarGraficas.isChecked()){
                    linearLayoutGraf.setLayoutParams(lp);
                    grafica.removeAllViews();
                    creaGrafica.borrar();
                    grafica = creaGrafica.Grafica(1,grafica,porcentajes,totalCuentas);
                    g1=true;
                    g2=false;
                    g3=false;

                    pieChartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            grafica.removeAllViews();
                            creaGrafica.borrar();
                            grafica = creaGrafica.Grafica(1,grafica,porcentajes,totalCuentas);
                            g1=true;
                            g2=false;
                            g3=false;
                        }
                    });

                    dountCharButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            grafica.removeAllViews();
                            grafica = creaGrafica.Grafica(2,grafica,porcentajes,totalCuentas);
                            g1=false;
                            g2=true;
                            g3=false;
                        }
                    });

                    barChartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            grafica.removeAllViews();
                            grafica = creaGrafica.Grafica(3,grafica,porcentajes,totalCuentas);
                            g1=false;
                            g2=false;
                            g3=true;
                        }
                    });
                }else{
                    grafica.removeAllViews();
                    linearLayoutGraf.setLayoutParams(lp2);
                }
            }
        });

        PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!creado){
                    hilo2=new getFecha();
                    hilo2.setIdentifica(4);
                    hilo2.setActivity(activity);
                    hilo2.execute("11");
                }else{
                    Toast.makeText(v.getContext(),"NO SE HA REALIZADO UNA CONSULTA",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static void crearPDF(JSONArray csr, JSONArray ccr, JSONArray cse, String Nombre, String Totalcuentas,String NOMBRE_DOCUMENTO_PDF) {
        mostrardatos mostrardatos= new mostrardatos();
        Document documento = new Document(PageSize.A4, 10, 10, 100, 60);
        font=new Font();
        fuenteTE= new Font();
        fontDatos= new Font();
        BaseColor ET = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.backgroud)).substring(2)));
        BaseColor ColorFilas = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gradient_end_color)).substring(2)));
        BaseColor ColorFilas2 = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.blanco)).substring(2)));
        BaseColor Colorcsr = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.grojo)).substring(2)));
        BaseColor Colorccr = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gamarillo)).substring(2)));
        BaseColor Colorcse = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gverde)).substring(2)));

        fuenteTE.setSize(8);
        fuenteTE.setColor(BaseColor.WHITE);
        font.setSize(10);
        font.setColor(BaseColor.WHITE);
        fontDatos.setColor(BaseColor.BLACK);
        fontDatos.setSize(8);

        try {
            Drawable d = activity.getResources().getDrawable(R.mipmap.docpdf1);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = Image.getInstance(stream.toByteArray());
            if(MostrarGraficas.isChecked()) {
                Bitmap graf;
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();

                if (g1 || g2) {
                    graf = getPieChart().getChartBitmap();
                    graf.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    image2 = Image.getInstance(stream2.toByteArray());
                } else if (g3) {
                    graf = getBarChart().getChartBitmap();
                    graf.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    image2 = Image.getInstance(stream2.toByteArray());
                }
            }
        } catch (IOException | BadElementException e) {
            Toast.makeText(activity.getApplicationContext(),"Crea PDF\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        try {
            File file = crearFichero(activity.getApplicationContext(),NOMBRE_DOCUMENTO_PDF);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
            ProblemaBateria.HeaderFooter event = new ProblemaBateria.HeaderFooter();

            documento.setMarginMirroring(false);
            writer.setPageEvent((PdfPageEvent) event);
            image.setAbsolutePosition(0f,0f);
            image.scaleAbsolute(180f,100f);

            int tam = 180;
            if(MostrarGraficas.isChecked()) {
                image2.scaleToFit(tam, tam);
                image2.setAlignment(Element.ALIGN_CENTER);
            }

            documento.open();

            event.setNombre(Nombre);
            event.setNombre2(totalCuentas);
            event.setImage(image);

            if(MostrarGraficas.isChecked()) {
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                Paragraph tituloTabp = new Paragraph("TABLA DE PORCENTAJES");
                tituloTabp.setAlignment(Element.ALIGN_CENTER);
                documento.add(tituloTabp);
                documento.add(new Paragraph(" "));
                //cREAR LA TABLA DE PORCENTAJES
                PdfPTable tablePorcentajes = new PdfPTable(3);
                PdfPCell cellTitulo,cell;

                cellTitulo = new PdfPCell(new Paragraph("ESTADO",fuenteTE));
                cellTitulo.setVerticalAlignment(Element.ALIGN_CENTER);
                cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTitulo.setBackgroundColor(ET);
                cellTitulo.setPadding(5);
                tablePorcentajes.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablePorcentajes.addCell(cellTitulo);

                cellTitulo = new PdfPCell(new Paragraph("CANTIDAD",fuenteTE));
                cellTitulo.setVerticalAlignment(Element.ALIGN_CENTER);
                cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTitulo.setBackgroundColor(ET);
                cellTitulo.setPadding(5);
                tablePorcentajes.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablePorcentajes.addCell(cellTitulo);

                cellTitulo = new PdfPCell(new Paragraph("PORCENTAJE",fuenteTE));
                cellTitulo.setVerticalAlignment(Element.ALIGN_CENTER);
                cellTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTitulo.setBackgroundColor(ET);
                cellTitulo.setPadding(5);
                tablePorcentajes.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablePorcentajes.addCell(cellTitulo);

                String[] SEPARADO;
                String ARRAY;
                Iterator it= tp.iterator();
                int contador = 1;
                while(it.hasNext()){
                    ARRAY= (String) it.next();
                    SEPARADO=mostrardatos.separar(ARRAY);

                    cell = new PdfPCell(new Paragraph(SEPARADO[0].replace("_"," "),fontDatos));
                    if((contador%2)==0){
                        cell.setBackgroundColor(ColorFilas);
                    }else {
                        cell.setBackgroundColor(ColorFilas2);
                    }
                    cell.setPadding(5);
                    tablePorcentajes.getDefaultCell().setBorderColor(BaseColor.WHITE);
                    tablePorcentajes.addCell(cell);

                    cell = new PdfPCell(new Paragraph(SEPARADO[1].replace("_"," "),fontDatos));
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if((contador%2)==0){
                        cell.setBackgroundColor(ColorFilas);
                    }else {
                        cell.setBackgroundColor(ColorFilas2);
                    }
                    cell.setPadding(5);
                    tablePorcentajes.getDefaultCell().setBorderColor(BaseColor.WHITE);
                    tablePorcentajes.addCell(cell);

                    cell = new PdfPCell(new Paragraph(SEPARADO[2].replace("_"," "),fontDatos));
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if((contador%2)==0){
                        cell.setBackgroundColor(ColorFilas);
                    }else {
                        cell.setBackgroundColor(ColorFilas2);
                    }
                    cell.setPadding(5);
                    tablePorcentajes.getDefaultCell().setBorderColor(BaseColor.WHITE);
                    tablePorcentajes.addCell(cell);

                    contador++;
                }

                documento.add(tablePorcentajes);

                //CREAR LAS GRAFICAS
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
                //documento.add(new Chunk(lineSeparator));
                Paragraph tituloGraf = new Paragraph("Gráfica");
                tituloGraf.setAlignment(Element.ALIGN_CENTER);
                documento.add(tituloGraf);
                documento.add(new Paragraph(""));

                documento.add(image2);

                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
            }
            Paragraph tituloTab = new Paragraph("CUENTAS SIN RESTAURE");
            tituloTab.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloTab);
            documento.add(new Paragraph(" "));

            PdfPTable tablecsr = new PdfPTable(3);
                tablecsr.getAbsoluteWidths();
                tablecsr.completeRow();
                tablecsr.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell;
                cell = new PdfPCell(new Paragraph("#",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorcsr);
                cell.setPadding(5);
                tablecsr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecsr.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE CUENTA",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorcsr);
                cell.setPadding(5);
                tablecsr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecsr.addCell(cell);

                cell = new PdfPCell(new Paragraph("EVENTOS RECIBIDOS",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorcsr);
                cell.setPadding(5);
                tablecsr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecsr.addCell(cell);

            String[] SEPARADO;
            String ARRAY;
            for (int i =0;i<csr.length();i++){
                ARRAY= csr.get(i).toString();
                SEPARADO=mostrardatos.separar(ARRAY);
                cell = new PdfPCell(new Paragraph(""+(i+1),fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tablecsr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecsr.addCell(cell);

                cell = new PdfPCell(new Paragraph(SEPARADO[2].replace("***"," "),fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tablecsr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecsr.addCell(cell);

                cell = new PdfPCell(new Paragraph(SEPARADO[3],fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tablecsr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecsr.addCell(cell);
            }
            documento.add(tablecsr);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));

            tituloTab = new Paragraph("CUENTAS CON RESTAURE");
            tituloTab.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloTab);
            documento.add(new Paragraph(" "));

            PdfPTable tableccr = new PdfPTable(3);
                tableccr.getAbsoluteWidths();
                tableccr.completeRow();
                tableccr.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell = new PdfPCell(new Paragraph("#",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorccr);
                cell.setPadding(5);
                tableccr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tableccr.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE CUENTA",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorccr);
                cell.setPadding(5);
                tableccr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tableccr.addCell(cell);

                cell = new PdfPCell(new Paragraph("EVENTOS RECIBIDOS",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorccr);
                cell.setPadding(5);
                tableccr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tableccr.addCell(cell);

            SEPARADO = null;
            ARRAY = null;
            for (int i =0;i<ccr.length();i++){
                ARRAY= ccr.get(i).toString();
                SEPARADO=mostrardatos.separar(ARRAY);
                cell = new PdfPCell(new Paragraph(""+(i+1),fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tableccr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tableccr.addCell(cell);

                cell = new PdfPCell(new Paragraph(SEPARADO[2].replace("***"," "),fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tableccr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tableccr.addCell(cell);

                cell = new PdfPCell(new Paragraph(SEPARADO[3],fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tableccr.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tableccr.addCell(cell);
            }
            documento.add(tableccr);

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));

            tituloTab = new Paragraph("CUENTAS SIN FALLA");
            tituloTab.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloTab);
            documento.add(new Paragraph(" "));

            PdfPTable tablecse = new PdfPTable(3);
                tablecse.getAbsoluteWidths();
                tablecse.completeRow();
                tablecse.setHorizontalAlignment(Element.ALIGN_CENTER);

                cell = new PdfPCell(new Paragraph("#",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorcse);
                cell.setPadding(5);
                tablecse.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecse.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE CUENTA",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorcse);
                cell.setPadding(5);
                tablecse.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecse.addCell(cell);

                cell = new PdfPCell(new Paragraph("EVENTOS RECIBIDOS",fuenteTE));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Colorcse);
                cell.setPadding(5);
                tablecse.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecse.addCell(cell);

            SEPARADO = null;
            ARRAY = null;
            for (int i =0;i<cse.length();i++){
                ARRAY= cse.get(i).toString();
                SEPARADO=mostrardatos.separar(ARRAY);
                cell = new PdfPCell(new Paragraph(""+(i+1),fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tablecse.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecse.addCell(cell);

                cell = new PdfPCell(new Paragraph(SEPARADO[2].replace("***"," "),fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tablecse.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecse.addCell(cell);

                cell = new PdfPCell(new Paragraph("0",fontDatos));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                if(((i+1)%2)==0){
                    cell.setBackgroundColor(ColorFilas);
                }else {
                    cell.setBackgroundColor(ColorFilas2);
                }
                tablecse.getDefaultCell().setBorderColor(BaseColor.WHITE);
                tablecse.addCell(cell);
            }
            documento.add(tablecse);

        } catch(DocumentException | IOException ignored) {
        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(),"error Document endPage\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
            documento.close();
        }
    }

    public static File crearFichero(Context context, String nombreFichero){
        File ruta = getRuta(context);
        File file = new File(ruta, nombreFichero+".pdf");
        if(file.exists()){
            file.mkdir();
        }
        return file;
    }
    public static int pinta(int contador){
        int color=0;
        if((contador%2)==0){
            color=1;
            //tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.gradient_end_color));
        }else {
            color=2;
            //tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.blanco));
        }
        return color;
    }
    public static  File getRuta(Context context) {
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "/"+NOMBRE_CARPETA_APP);
            //Toast.makeText(context,""+ruta,Toast.LENGTH_LONG).show();
            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }

        return ruta;
    }

    //clase
    public static class HeaderFooter extends PdfPageEventHelper {
        private String nombre;
        private String nombre2;
        Image image;
        PdfTemplate total;

        public HeaderFooter(){
            nombre="";
            nombre2="";
        }
        public HeaderFooter(String nombre, String nombre2) {
            this();
            nombre = nombre;
            nombre2 = nombre2;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre2() {
            return nombre2;
        }

        public void setNombre2(String nombre2) {
            this.nombre2 = nombre2;
        }

        public void onEndPage (PdfWriter writer, Document document) {

            Rectangle rect= writer.getBoxSize("art");

            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase("NOMBRE GRUPO: "+nombre+" "),
                    rect.getLeft()+150, rect.getTop(), 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase(""),
                    rect.getLeft()+150,rect.getTop()-30,0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("página %d", writer.getPageNumber())),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 42, 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("PERMISO SSP FEDERAL: DGSP/303-16/3302"),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 14, 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase("PERMISO SSP EDO. DE PUEBLA: SSP/SUBCOP/DGSP/114-15/109"),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 28, 0);
            image.setAbsolutePosition(document.leftMargin(),writer.getPageSize().getTop(document.topMargin())+2);
            try {
                document.add(image);
            } catch (DocumentException e) {
                Toast.makeText(activity.getApplicationContext(),"error Document endPage\n"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
}