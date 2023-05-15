package com.pemsa.pemsamonitoreoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.pemsa.pemsamonitoreoapp.API.getApCiGoD;
import com.pemsa.pemsamonitoreoapp.API.getFecha;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCiGoD;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AperturaCierreGrupo extends AppCompatActivity {
    public static ArrayList<String> FINAL=new ArrayList<String>();
    public static RecyclerView recycler;
    public static TextView nombreGrupo,f1,f2,f3,f4,f5,f6,f7,nd1,nd2,nd3,nd4,nd5,nd6,nd7;
    public static String Token,NG,CG,Type;
    public static mostrardatos mostrardatos=new mostrardatos();;
    public static TableLayout tableLayout,tablePorcentajes;
    public static TableDynamic tableDynamic;
    public static String dt;
    public static Image image, image2, image3;
    public static Font font,fuenteTE,fontDatos;
    public static boolean creado=false;
    public static String NOMBRE_CARPETA_APP = "PEMSA";
    public static FloatingActionButton PDF;
    public static ArrayList<String> tp;

    public static Activity activity;
    public static R r;

    //Graf
    public static Switch MostrarGraficas;
    public static ConstraintLayout grafica, grafica2;
    public static LinearLayout.LayoutParams lp;
    public static LinearLayout.LayoutParams lp2;
    public static LinearLayout linearLayoutGraf;
    public static ImageButton pieChartButton, barChartButton, dountCharButton;
    public static JSONObject porcentajes;
    public static String totalAperturas;
    public static String totalCierres;
    public static boolean g1=false, g2=false, g3=false;

    public static BarChart barChart;
    public static BarChart barChart2;
    public static PieChart pieChart;
    public static PieChart pieChart2;

    public static BarChart getBarChart() {
        return barChart;
    }

    public static void setBarChart(BarChart barChart) {
        AperturaCierreGrupo.barChart = barChart;
    }

    public static BarChart getBarChart2() {
        return barChart2;
    }

    public static void setBarChart2(BarChart barChart2) {
        AperturaCierreGrupo.barChart2 = barChart2;
    }

    public static PieChart getPieChart() {
        return pieChart;
    }

    public static void setPieChart(PieChart pieChart) {
        AperturaCierreGrupo.pieChart = pieChart;
    }

    public static PieChart getPieChart2() {
        return pieChart2;
    }

    public static void setPieChart2(PieChart pieChart2) {
        AperturaCierreGrupo.pieChart2 = pieChart2;
    }

    getApCiGoD hilo;
    getFecha hilo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apertura_cierre_grupo);
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
        GraficaApCiGoD creaGrafica = new GraficaApCiGoD();
        creaGrafica.setActivity(activity);
        //instancias
        PDF=(FloatingActionButton)findViewById(R.id.DescargarPDFG);
        nombreGrupo=(TextView)findViewById(R.id.nombreGrupo);
        f1=(TextView)findViewById(R.id.fecha1);
        f2=(TextView)findViewById(R.id.fecha2);
        f3=(TextView)findViewById(R.id.fecha3);
        f4=(TextView)findViewById(R.id.fecha4);
        f5=(TextView)findViewById(R.id.fecha5);
        f6=(TextView)findViewById(R.id.fecha6);
        f7=(TextView)findViewById(R.id.fecha7);
        nd1=(TextView)findViewById(R.id.nd1);
        nd2=(TextView)findViewById(R.id.nd2);
        nd3=(TextView)findViewById(R.id.nd3);
        nd4=(TextView)findViewById(R.id.nd4);
        nd5=(TextView)findViewById(R.id.nd5);
        nd6=(TextView)findViewById(R.id.nd6);
        nd7=(TextView)findViewById(R.id.nd7);

        //---------------------------------------------------------------------------------
        //graficas
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp2 = new LinearLayout.LayoutParams(0, 0);
        grafica = ( ConstraintLayout )findViewById( R.id.APCIGoD_Grafica);
        grafica2 = ( ConstraintLayout )findViewById( R.id.APCIGoD_Grafica2);
        linearLayoutGraf = (LinearLayout)findViewById( R.id.APCIGoD_LinearLayoutGraficas );
        pieChartButton = ( ImageButton )findViewById( R.id.APCIGoD_pieButtonApCi);
        dountCharButton = ( ImageButton )findViewById( R.id.APCIGoD_dountButtonApCi);
        barChartButton = ( ImageButton )findViewById( R.id.APCIGoD_barButtonApCi );
        MostrarGraficas = ( Switch )findViewById( R.id.APCIGoD_MostrarGrafica );
        tablePorcentajes = ( TableLayout )findViewById( R.id.APCIGoD_table_Porcentajes );

        //OBTENEMOS LOS DATOS QUE SE MANDAROIN DEL CONSULTASFRAGMENT-------------------------------------------------------------------------------------------------------------------------

        Token=getIntent().getStringExtra("Token");
        NG=getIntent().getStringExtra("NG");
        CG=getIntent().getStringExtra("CG");
        Type=getIntent().getStringExtra("Type");
        //se hacen la consultas
         recycler=(RecyclerView)findViewById(R.id.idReciclerAPCIG);
         recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
         tableLayout=(TableLayout)findViewById(R.id.tableAPCIGRUPO);

         nombreGrupo.setText(NG);

         hilo= new getApCiGoD();
         hilo.setActivity(activity);
         hilo.execute("1",Token+"___ESP___"+CG+"___ESP___"+Type);

        creado=false;
        linearLayoutGraf.setLayoutParams(lp2);
        MostrarGraficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MostrarGraficas.isChecked()){
                    linearLayoutGraf.setLayoutParams(lp);
                    grafica.removeAllViews();
                    grafica2.removeAllViews();
                    creaGrafica.borrar();
                    grafica = creaGrafica.Grafica(1,grafica,porcentajes,totalAperturas);
                    grafica2 = creaGrafica.Grafica(11,grafica2,porcentajes,totalCierres);
                    g1=true;
                    g2=false;
                    g3=false;
                    pieChartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            grafica.removeAllViews();
                            grafica2.removeAllViews();
                            creaGrafica.borrar();
                            grafica = creaGrafica.Grafica(1,grafica,porcentajes,totalAperturas);
                            grafica2 = creaGrafica.Grafica(11,grafica2,porcentajes,totalCierres);
                            g1=true;
                            g2=false;
                            g3=false;
                        }
                    });

                    dountCharButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            grafica.removeAllViews();
                            grafica2.removeAllViews();
                            grafica = creaGrafica.Grafica(2,grafica,porcentajes,totalAperturas);
                            grafica2 = creaGrafica.Grafica(22,grafica2,porcentajes,totalCierres);
                            g1=false;
                            g2=true;
                            g3=false;
                        }
                    });

                    barChartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            grafica.removeAllViews();
                            grafica2.removeAllViews();
                            grafica = creaGrafica.Grafica(3,grafica,porcentajes,totalAperturas);
                            grafica2 = creaGrafica.Grafica(33,grafica2,porcentajes,totalCierres);
                            g1=false;
                            g2=false;
                            g3=true;
                        }
                    });
                }else{
                    grafica.removeAllViews();
                    grafica2.removeAllViews();
                    linearLayoutGraf.setLayoutParams(lp2);
                }
            }
        });


        PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!creado){
                    hilo2=new getFecha();
                    hilo2.setIdentifica(3);
                    hilo2.setActivity(activity);
                    hilo2.execute("11");
                }else{
                    Toast.makeText(v.getContext(),"NO SE HA REALIZADO UNA CONSULTA",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //------------------------------------------------------------------------------------------------------------------------------------
    }
    public static void crearPDF(ArrayList datos,String Nombre,String CA,String NOMBRE_DOCUMENTO_PDF) {
        Document documento = new Document(PageSize.A4, 10, 10, 100, 60);
        font=new Font();
        fuenteTE= new Font();
        fontDatos= new Font();
        BaseColor ET = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.backgroud)).substring(2)));
        BaseColor ColorFilas = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gradient_end_color)).substring(2)));
        BaseColor ColorFilas2 = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.blanco)).substring(2)));

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
                Bitmap graf,graf2;
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                ByteArrayOutputStream stream3 = new ByteArrayOutputStream();

                if (g1 || g2) {
                    graf = getPieChart().getChartBitmap();
                    graf.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    image2 = Image.getInstance(stream2.toByteArray());

                    graf2 = getPieChart2().getChartBitmap();
                    graf2.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                    image3 = Image.getInstance(stream3.toByteArray());
                } else if (g3) {
                    graf = getBarChart().getChartBitmap();
                    graf.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    image2 = Image.getInstance(stream2.toByteArray());

                    graf2 = getBarChart2().getChartBitmap();
                    graf2.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                    image3 = Image.getInstance(stream3.toByteArray());
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
            HeaderFooter event = new HeaderFooter();

            documento.setMarginMirroring(false);
            writer.setPageEvent((PdfPageEvent) event);
            image.setAbsolutePosition(0f,0f);
            image.scaleAbsolute(180f,100f);

            int tam = 180;
            if(MostrarGraficas.isChecked()) {
                image2.scaleToFit(tam, tam);
                image2.setAlignment(Element.ALIGN_LEFT);

                image3.scaleToFit(tam, tam);
                image3.setAlignment(Element.ALIGN_RIGHT);
            }

            documento.open();

            event.setNombre(Nombre);
            event.setNombre2(CA);
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

                cellTitulo = new PdfPCell(new Paragraph("EVENTO",fuenteTE));
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
                // Crear tabla nueva con dos posiciones
                PdfPTable table = new PdfPTable(2);
                float[] longitudes = { 5.0f, 5.0f };
                // Establecer posiciones de celdas
                table.setWidths(longitudes);
                //Nombres de las tablas
                PdfPCell cellGraf = new PdfPCell(new Paragraph("Gráfica aperturas",fuenteTE));
                cellGraf.setVerticalAlignment(Element.ALIGN_CENTER);
                cellGraf.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellGraf.setPadding(5);
                cellGraf.setBackgroundColor(ET);
                table.getDefaultCell().setBorderColor(BaseColor.WHITE);
                table.addCell(cellGraf);

                cellGraf = new PdfPCell(new Paragraph("Gráfica cierres",fuenteTE));
                cellGraf.setVerticalAlignment(Element.ALIGN_CENTER);
                cellGraf.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellGraf.setPadding(5);
                cellGraf.setBackgroundColor(ET);
                table.getDefaultCell().setBorderColor(BaseColor.WHITE);
                table.addCell(cellGraf);

                // Cargar imagenes del producto y agregarlas
                image2.scaleAbsolute(40f, 40f);
                table.getDefaultCell().setBorderColor(BaseColor.WHITE);
                table.addCell(image2);
                image3.scaleAbsolute(40f, 40f);
                table.addCell(image3);

                // Agregar la tabla al documento
                documento.add(table);

                documento.add(new Paragraph(" "));
                documento.add(new Paragraph(" "));
            }
            Paragraph tituloTab = new Paragraph("TABLA DE HORARIOS");
            tituloTab.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloTab);
            documento.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(19);
            table.getAbsoluteWidths();
            table.completeRow();
            table.setHorizontalAlignment(Element.ALIGN_CENTER);


            PdfPCell cell = new PdfPCell(new Paragraph(""));
            cell.setColspan(5);
            cell.setRowspan(1);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd1.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd2.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd3.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd4.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd5.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd6.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.nd7.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            //______
            cell = new PdfPCell(new Paragraph(""));
            cell.setColspan(5);
            cell.setRowspan(1);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f1.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f2.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f3.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f4.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f5.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f6.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(AperturaCierreGrupo.f7.getText().toString(),fuenteTE));
            cell.setColspan(2);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //--------------------
            PdfPCell cell3 = new PdfPCell(new Paragraph("NOMBRE",font));
            cell3.setColspan(5);
            cell3.setRowspan(1);
            cell3.setBackgroundColor(ET);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);
            PdfPCell cell4;
            for(int i=0;i<7;i++) {
                cell4 = new PdfPCell(new Paragraph("Ap",font));
                cell4.setColspan(1);
                cell4.setRowspan(1);
                cell4.setBackgroundColor(ET);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell4);

                cell4 = new PdfPCell(new Paragraph("Ci",font));
                cell4.setColspan(1);
                cell4.setRowspan(1);
                cell4.setBackgroundColor(ET);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell4);
            }
            String [] separado;
            //--SE HACE EL ACOMODO DE LOS DATOS
            String[] ARRAY = new String[AperturaCierreGrupo.FINAL.size()];
            int contador=0;
            Iterator it= AperturaCierreGrupo.FINAL.iterator();
            while(it.hasNext()){
                ARRAY[contador]=(String) it.next();
                contador++;
            }
            for(int i=0;i<ARRAY.length;i++){
                separado=mostrardatos.separar(ARRAY[i]);
                PdfPCell nombre = new PdfPCell(new Paragraph("   " + separado[0].replace("**", " ") + "   ",fontDatos));
                nombre.setColspan(5);
                nombre.setRowspan(1);
                if(pinta(i)==1){
                    nombre.setBackgroundColor(ColorFilas);
                }
                table.addCell(nombre);
                for(int j=1;j<separado.length;j++){
                    font.setSize(8);
                    PdfPCell hora = new PdfPCell(new Paragraph(separado[j],fontDatos));
                    hora.setColspan(1);
                    hora.setRowspan(1);
                    hora.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if(pinta(i)==1){
                        hora.setBackgroundColor(ColorFilas);
                    }
                    table.addCell(hora);

                }

            }

            documento.add(table);

        } catch(DocumentException e) {
        } catch(IOException e) {
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