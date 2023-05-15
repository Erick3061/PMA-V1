package com.pemsa.pemsamonitoreoapp.GRAFICAS;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.pemsa.pemsamonitoreoapp.EventoAlarma;
import com.pemsa.pemsamonitoreoapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraficaEA {
    public static ArrayList<PieEntry> dataPie = new ArrayList<>();
    public static List<BarEntry> dataBars = new ArrayList<>();
    Activity activity;
    ConstraintLayout constraintLayout;
    JSONObject porcentajes;
    String total;
    private PieChart pieChart;
    private BarChart barChart;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public void borrar(){
        this.porcentajes=null;
    }
    public ConstraintLayout Grafica(int tipo, ConstraintLayout graficas,JSONObject porcentajes,String total){
        dataPie.clear();
        dataBars.clear();
        this.porcentajes=porcentajes;
        this.total=total;
        switch (tipo){
            case 1:
                this.constraintLayout=graficas;
                pieChart = new PieChart(this.activity);
                pieChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupPieChart(false);
                loadPieChartDataEA();
                EventoAlarma.setPieChart(pieChart);
                this.constraintLayout.addView(pieChart);
            break;
            case 2:
                this.constraintLayout=graficas;
                pieChart = new PieChart(this.activity);
                pieChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupPieChart(true);
                loadPieChartDataEA();
                EventoAlarma.setPieChart(pieChart);
                this.constraintLayout.addView(pieChart);
            break;
            case 3:
                this.constraintLayout=graficas;
                barChart = new BarChart(this.activity);
                barChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupBarChart();
                loadBarChartDataEA();
                EventoAlarma.setBarChart(barChart);
                this.constraintLayout.addView(barChart);
            break;
        }
        return this.constraintLayout;
    }

    private void setupPieChart(boolean tipo) {
        if(tipo){
            pieChart.setDrawHoleEnabled(true);
        }else {
            pieChart.setDrawHoleEnabled(false);
        }
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(20);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextSize(15f);
        l.setEnabled(true);
    }
    private void setupBarChart() {

        barChart.getDescription().setEnabled(false);
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        l.setDrawInside( true );
        barChart.setDrawValueAboveBar( true );

    }

    private void loadPieChartDataEA() {
        float apci, alarmas, pruebas, baterias, otros;
        try {
            apci = Float.parseFloat(this.porcentajes.getString("apci"));
            alarmas = Float.parseFloat(this.porcentajes.getString("alarmas"));
            pruebas = Float.parseFloat(this.porcentajes.getString("pruebas"));
            baterias = Float.parseFloat(this.porcentajes.getString("baterias"));
            otros = Float.parseFloat(this.porcentajes.getString("otros"));

            dataPie.add(new PieEntry(Math.round((apci*100)/Integer.parseInt(this.total)), "aperturas y cierres: "+Math.round((apci*100)/Integer.parseInt(this.total))+"%"));
            dataPie.add(new PieEntry(Math.round((alarmas*100)/Integer.parseInt(this.total)), "alarmas: "+Math.round((alarmas*100)/Integer.parseInt(this.total))+"%"));
            dataPie.add(new PieEntry(Math.round((pruebas*100)/Integer.parseInt(this.total)), "pruebas: "+Math.round((pruebas*100)/Integer.parseInt(this.total))+"%"));
            dataPie.add(new PieEntry(Math.round((baterias*100)/Integer.parseInt(this.total)), "baterias: "+Math.round((baterias*100)/Integer.parseInt(this.total))+"%"));
            dataPie.add(new PieEntry(Math.round((otros*100)/Integer.parseInt(this.total)), "otros: "+Math.round((otros*100)/Integer.parseInt(this.total))+"%"));
            PieDataSet dataSet = new PieDataSet(dataPie, "");
            dataSet.setColors(
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gamarillo)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gverde)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gazul)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gmorado)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.ggris)).substring(2)))
            );
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);

            pieChart.setData(data);
            pieChart.invalidate();

            pieChart.animateY(1400, Easing.EaseInOutQuad);
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),"Error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void loadBarChartDataEA() {
        float apci, alarmas, pruebas, baterias, otros;
        try {
            apci = Float.parseFloat(this.porcentajes.getString("apci"));
            alarmas = Float.parseFloat(this.porcentajes.getString("alarmas"));
            pruebas = Float.parseFloat(this.porcentajes.getString("pruebas"));
            baterias = Float.parseFloat(this.porcentajes.getString("baterias"));
            otros = Float.parseFloat(this.porcentajes.getString("otros"));

            dataBars.add(new BarEntry(1,Math.round((apci*100)/Integer.parseInt(this.total))));
            dataBars.add(new BarEntry(2,Math.round((alarmas*100)/Integer.parseInt(this.total))));
            dataBars.add(new BarEntry(3,Math.round((pruebas*100)/Integer.parseInt(this.total))));
            dataBars.add(new BarEntry(4,Math.round((baterias*100)/Integer.parseInt(this.total))));
            dataBars.add(new BarEntry(5,Math.round((otros*100)/Integer.parseInt(this.total))));

            String[] labels = new String[]{"",
                    "aperturas y cierres: "+Math.round((apci*100)/Integer.parseInt(this.total))+"%",
                    "alarmas: "+Math.round((alarmas*100)/Integer.parseInt(this.total))+"%",
                    "pruebas: "+Math.round((pruebas*100)/Integer.parseInt(this.total))+"%",
                    "baterias: "+Math.round((baterias*100)/Integer.parseInt(this.total))+"%",
                    "otros: "+Math.round((otros*100)/Integer.parseInt(this.total))+"%"
            };
            BarDataSet barDataSet = new BarDataSet(dataBars,"");
            barDataSet.setColors(
                    Color.parseColor( "#" + String.valueOf( Integer.toHexString( activity.getColor( R.color.gamarillo ) ).substring( 2 ) ) ),
                    Color.parseColor( "#" + String.valueOf( Integer.toHexString( activity.getColor( R.color.gverde ) ).substring( 2 ) ) ),
                    Color.parseColor( "#" + String.valueOf( Integer.toHexString( activity.getColor( R.color.gazul ) ).substring( 2 ) ) ),
                    Color.parseColor( "#" + String.valueOf( Integer.toHexString( activity.getColor( R.color.gmorado ) ).substring( 2 ) ) ),
                    Color.parseColor( "#" + String.valueOf( Integer.toHexString( activity.getColor( R.color.ggris ) ).substring( 2 ) )  )
            );
            barDataSet.setValueTextColor( Color.BLACK );
            barDataSet.setValueTextSize( 20f );
            barDataSet.setStackLabels( labels );
            barDataSet.setDrawValues( false );


            BarData barData = new BarData( barDataSet );
            barChart.setData( barData );
            barChart.animateY(1000 );
            barChart.setFitBars( true );
            barChart.getXAxis().setAxisMinimum( 0 );
            barChart.getXAxis().setAxisMaximum( 0+barChart.getBarData().getGroupWidth(0.08f,0.44f)*labels.length-1 );


            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter( new IndexAxisValueFormatter(labels) );
            xAxis.setPosition( XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity( 1 );
            xAxis.setLabelRotationAngle( -80 );
            xAxis.setTextSize( 15f );
            xAxis.setGranularityEnabled( true );

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),"Error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


}
