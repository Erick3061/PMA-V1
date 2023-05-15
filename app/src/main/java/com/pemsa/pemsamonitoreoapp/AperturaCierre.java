package com.pemsa.pemsamonitoreoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.github.mikephil.charting.charts.*;
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
import com.pemsa.pemsamonitoreoapp.API.getEventos;
import com.pemsa.pemsamonitoreoapp.API.getFecha;
import com.pemsa.pemsamonitoreoapp.GRAFICAS.GraficaApCi;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AperturaCierre extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static TextView dateFi,dateFf,nombre,CodigoAbonado,CodigoCliente,prueba;
    public static Button btnFi,btnFf,CONSULTAR;
    public static String IdCuenta,CorreoM,IdUsuario,TablaActual,Nombre,CodigoA,Direccion,token;
    public static int ban=0,diaLimite=0,mesLimite=0,anioLimite=0;
    public static mostrardatos mostrardatos=new mostrardatos();
    public static TableLayout tableLayout;
    public static String[] header={"FECHA","HORA","DESCRIPCION\nEVENTO","PARTICIÓN","N° USUARIO","NOMBRE DE USUARIO"};
    public static ArrayList rows = new ArrayList();
    public static String dt;
    public static Image image,image2;
    public static Font font,font2;
    public static String Fecha_inicio,Fecha_final;
    public static Activity activity;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public static Switch Grafica;

    //

    public static boolean isActivo=false,creapdf=false,creado=false;
    public static String NOMBRE_CARPETA_APP = "PEMSA";
    public static FloatingActionButton PDF;
    public static  String a="";

    //Graf
    public static ConstraintLayout graficas;
    public static LinearLayout.LayoutParams lp;
    public static LinearLayout.LayoutParams lp2;
    public static LinearLayout linearLayoutGraf;
    public static ImageButton pieChartButton, barChartButton,dountCharButton;
    public static JSONObject porcentajes;
    public static String total;
    public static boolean g1=false, g2=false, g3=false;
    public static BarChart barChart;
    public static PieChart pieChart;

    public static BarChart getBarChart() {
        return barChart;
    }

    public static void setBarChart(BarChart barChart) {
        AperturaCierre.barChart = barChart;
    }

    public static PieChart getPieChart() {
        return pieChart;
    }

    public static void setPieChart(PieChart pieChart) {
        AperturaCierre.pieChart = pieChart;
    }

    //hilos de ejecucion
    getEventos hilo;
    getFecha hilo2;

    public static TableDynamic tableDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        isActivo=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apertura_cierre);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombre=(TextView)findViewById(R.id.nombre);
        CodigoAbonado=(TextView)findViewById(R.id.CodigoAbonado);
        CodigoCliente=(TextView)findViewById(R.id.CodigoCliente);
        PDF=(FloatingActionButton)findViewById(R.id.DescargarPDF1);
        prueba=(TextView)findViewById(R.id.textView20);
        //instancias
        tableLayout=(TableLayout)findViewById(R.id.table);
        Grafica=(Switch)findViewById(R.id.ApCiGrafica);
        activity=this;
        GraficaApCi creaGrafica = new GraficaApCi();
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
        //--------------------------------------------------------------------------------
       //OBTENEMOS LOS DATOS QUE SE MANDAROIN DEL CONSULTASFRAGMENT-------------------------------------------------------------------------------------------------------------------------

        IdCuenta=getIntent().getStringExtra("IdCuenta");
        CorreoM=getIntent().getStringExtra("Correo");
        IdUsuario=getIntent().getStringExtra("IdUsurioApp");
        Nombre=getIntent().getStringExtra("Nombre");
        CodigoA=getIntent().getStringExtra("CodigoAbonado");
        Direccion=getIntent().getStringExtra("Direccion");
        token=getIntent().getStringExtra("Token");

        nombre.setText("NOMBRE: "+Nombre.trim());
        CodigoAbonado.setText("DIRECCIÓN: "+Direccion.trim());

        //------------------------------------------------------------------------------------------------------------------------------------

        Calendar calendar= Calendar.getInstance();
        Date fecha= new Date();
        calendar.setTime(fecha);


        String date = calendar.get(Calendar.YEAR)+"-"+
                (calendar.get(Calendar.MONTH)+1)+"-"+
               calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DAY_OF_MONTH,-30);

        String date2 = calendar.get(Calendar.YEAR)+"-"+
                (calendar.get(Calendar.MONTH)+1)+"-"+
                calendar.get(Calendar.DAY_OF_MONTH);

        diaLimite = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        mesLimite = Calendar.getInstance().get(Calendar.MONTH);
        anioLimite = Calendar.getInstance().get(Calendar.YEAR);



        dateFi=(TextView)findViewById(R.id.txtFI);
        dateFf=(TextView)findViewById(R.id.txtFF);
        btnFi=(Button)findViewById(R.id.btnFi);
        btnFf=(Button)findViewById(R.id.btnFf);
        dateFf.setText(date);
        dateFi.setText(date2);
        //Toast.makeText(activity.getBaseContext(),date+"\n"+date2,Toast.LENGTH_LONG).show();
        //graficas
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp2 = new LinearLayout.LayoutParams(0, 0);
        graficas = ( ConstraintLayout )findViewById( R.id.ApCi_Grafica);
        linearLayoutGraf = (LinearLayout)findViewById( R.id.APCI_LinearLayoutGraficas );
        pieChartButton = ( ImageButton )findViewById( R.id.ApCi_pieButtonApCi);
        dountCharButton = ( ImageButton )findViewById( R.id.ApCi_dountButtonApCi);
        barChartButton = ( ImageButton )findViewById( R.id.ApCi_barButtonApCi );

        linearLayoutGraf.setLayoutParams(lp2);


        //ACTION PARA CAMBIAR INTERVALO DE FECHA INICIO
        btnFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verCalendario(1);
                isActivo=false;
                creapdf=false;
                creado=false;
                linearLayoutGraf.setLayoutParams(lp2);
                graficas.removeAllViews();
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
                linearLayoutGraf.setLayoutParams(lp2);
                graficas.removeAllViews();
                mostrardatos.borrarTabla(tableLayout);
                ban=1;
            }
        });
        //tabla de eventos del año ------------------------------------------------------------------------------------------------------------
        TablaActual="Evento"+Calendar.getInstance().get(Calendar.YEAR);
        CONSULTAR=(Button)findViewById(R.id.hacerconsultaAP);
        //tse consultan los datos------------------------------------------------------------------------------------------------------------------------------------
        isActivo=false;
        Grafica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActivo=false;
                creapdf=false;
                creado=false;
                mostrardatos.borrarTabla(tableLayout);
                linearLayoutGraf.setLayoutParams(lp2);
            }
        });
        pieChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creado = false;
                graficas.removeAllViews();
                creaGrafica.borrar();
                graficas = creaGrafica.Grafica(1,graficas,porcentajes,total);
                g1=true;
                g2=false;
                g3=false;
            }
        });

        dountCharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creado = false;
                graficas.removeAllViews();
                graficas = creaGrafica.Grafica(2,graficas,porcentajes,total);
                g1=false;
                g2=true;
                g3=false;
            }
        });

        barChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creado = false;
                graficas.removeAllViews();
                graficas = creaGrafica.Grafica(3,graficas,porcentajes,total);
                g1=false;
                g2=false;
                g3=true;
            }
        });




        CONSULTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!AperturaCierre.Grafica.isChecked()){
                    graficas.removeAllViews();
                    linearLayoutGraf.setLayoutParams(lp2);
                }

                if(!isActivo){
                    Fecha_inicio=dateFi.getText().toString().trim();
                    Fecha_final=dateFf.getText().toString().trim();
                    hilo=new getEventos();
                    hilo.setActivity(activity);
                    hilo.setToken(token);
                    hilo.execute("1",IdCuenta+"___ESP___"+Fecha_inicio+"___ESP___"+Fecha_final);
                }
                isActivo=true;
            }
        });
        creado=false;
        PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(creapdf && !creado){
                    hilo2=new getFecha();
                    hilo2.setIdentifica(1);
                    hilo2.setActivity(activity);
                    hilo2.execute("11");
                }else{
                    Toast.makeText(v.getContext(),"NO SE HA REALIZADO UNA CONSULTA",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //---------------------------------------------------------------------------------------------------------------------------------

    }


    //FUNCIONES
    private void verCalendario(int var){
        if (var==1){
                    DatePickerDialog datePickerDialog=new DatePickerDialog(
                    this,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    //Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-30
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
    @Override
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

    public static void crearPDF(ArrayList datos,String Nombre,String CA,String NOMBRE_DOCUMENTO_PDF) {
        Document documento = new Document(PageSize.A4, 10, 10, 100, 60);
        font=new Font();
        font2=new Font();
        try {
            Drawable d = activity.getResources().getDrawable(R.mipmap.docpdf1);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = Image.getInstance(stream.toByteArray());
            if(AperturaCierre.Grafica.isChecked()) {
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
            //NOMBRE_DOCUMENTO_PDF="prueba";
            File file = crearFichero(activity.getApplicationContext(),NOMBRE_DOCUMENTO_PDF);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
            HeaderFooter event = new HeaderFooter();

            documento.setMarginMirroring(false);
            writer.setPageEvent((PdfPageEvent) event);
            image.setAbsolutePosition(0f,0f);
            image.scaleAbsolute(180f,100f);
            if(AperturaCierre.Grafica.isChecked()) {
                image2.scaleToFit(400, 400);
                image2.setAlignment(Element.ALIGN_CENTER);
            }
            documento.open();

            event.setNombre(Nombre.replace("____"," "));
            event.setNombre2(CA.replace("____"," "));
            event.setImage(image);
            if(AperturaCierre.Grafica.isChecked()) {
                // LINE SEPARATOR
                LineSeparator lineSeparator = new LineSeparator();
                lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

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
                //documento.add(new Chunk(lineSeparator));
            }
            Paragraph tituloTab = new Paragraph("Tabla");
            tituloTab.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloTab);

            font.setSize(9);
            font.setColor(BaseColor.WHITE);
            font2.setSize(9);
            font2.setColor(BaseColor.BLACK);

            BaseColor ET = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.backgroud)).substring(2)));

            BaseColor ColorFilas = WebColors.getRGBColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gradient_end_color)).substring(2)));

            // Insertamos una tabla

            PdfPTable tabla = new PdfPTable(6);
            tabla.setSpacingBefore(20);
            tabla.getDefaultCell().setPadding(5);
            tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
            //agregamos celdas
            PdfPCell cell,cell2;

            cell = new PdfPCell(new Paragraph("FECHA",font));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("HORA",font));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("DESCRIPCIÓN\nEVENTO",font));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("PARTICIÓN",font));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("N° USUARIO",font));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("NOMBRE DE USUARIO",font));
            cell.setColspan(1);
            cell.setRowspan(1);
            cell.setBackgroundColor(ET);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);

            String Array="";
            String[] separado;
            int pagina=1;
            for(int i = 0 ; i < datos.size() ; i++) {
                Array= (String) datos.get(i);
                separado=mostrardatos.separar(Array);
                for(int j=0;j<separado.length;j++){
                    cell2 = new PdfPCell(new Paragraph(separado[j],font2));
                    cell2.setColspan(1);
                    cell2.setRowspan(1);
                    cell2.setBackgroundColor(ET);
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if(pinta(i)==1){
                        cell2.setBackgroundColor(ColorFilas);
                    }else{
                        cell2.setBackgroundColor(BaseColor.WHITE);
                    }
                    tabla.addCell(cell2);
                }
                if(separado.length==5){
                    cell2 = new PdfPCell(new Paragraph("Sistema / Llavero",font2));
                    cell2.setColspan(1);
                    cell2.setRowspan(1);
                    cell2.setBackgroundColor(ET);
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if(pinta(i)==1){
                        cell2.setBackgroundColor(ColorFilas);
                    }else{
                        cell2.setBackgroundColor(BaseColor.WHITE);
                    }
                    tabla.addCell(cell2);
                }
            }
            documento.add(tabla);

        } catch(DocumentException e) {
            Toast.makeText(activity.getApplicationContext(),"error APCI Documento\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            Toast.makeText(activity.getApplicationContext(),"error APCI Exepcion\n"+e.getMessage(),Toast.LENGTH_LONG).show();
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

        @SuppressLint("DefaultLocale")
        public void onEndPage (PdfWriter writer, Document document) {

            Rectangle rect= writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase("NOMBRE CUENTA: "+nombre+" "),
                    rect.getLeft()+150, rect.getTop(), 0);
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase("DIRECCIÓN: "+nombre2+" "),
                    rect.getLeft()+150,rect.getTop()-25,0);
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
