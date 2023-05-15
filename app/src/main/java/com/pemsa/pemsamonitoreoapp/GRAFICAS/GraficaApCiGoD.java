package com.pemsa.pemsamonitoreoapp.GRAFICAS;

import android.app.Activity;
import android.app.BackgroundServiceStartNotAllowedException;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pemsa.pemsamonitoreoapp.AperturaCierre;
import com.pemsa.pemsamonitoreoapp.AperturaCierreGrupo;
import com.pemsa.pemsamonitoreoapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraficaApCiGoD {
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
                loadPieChartDataApCiGoD();
                AperturaCierreGrupo.setPieChart(pieChart);
                this.constraintLayout.addView(pieChart);
            break;
            case 11:
                this.constraintLayout=graficas;
                pieChart = new PieChart(this.activity);
                pieChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupPieChart(false);
                loadPieChartDataApCiGoD2();
                AperturaCierreGrupo.setPieChart2(pieChart);
                this.constraintLayout.addView(pieChart);
            break;
            case 2:
                this.constraintLayout=graficas;
                pieChart = new PieChart(this.activity);
                pieChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupPieChart(true);
                loadPieChartDataApCiGoD();
                AperturaCierreGrupo.setPieChart(pieChart);
                this.constraintLayout.addView(pieChart);
            break;
            case 22:
                this.constraintLayout=graficas;
                pieChart = new PieChart(this.activity);
                pieChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupPieChart(true);
                loadPieChartDataApCiGoD2();
                AperturaCierreGrupo.setPieChart2(pieChart);
                this.constraintLayout.addView(pieChart);
            break;
            case 3:
                this.constraintLayout=graficas;
                barChart = new BarChart(this.activity);
                barChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupBarChart();
                loadBarChartDataApCiGoD();
                AperturaCierreGrupo.setBarChart(barChart);
                this.constraintLayout.addView(barChart);
            break;
            case 33:
                this.constraintLayout=graficas;
                barChart = new BarChart(this.activity);
                barChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setupBarChart();
                loadBarChartDataApCiGoD2();
                AperturaCierreGrupo.setBarChart2(barChart);
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
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        l.setDrawInside( true );
        barChart.setDrawValueAboveBar( true );

    }

    private void loadPieChartDataApCiGoD() {
        float aperturasCH,aperturasSH;
        try {
            aperturasCH = Float.parseFloat(this.porcentajes.getString("aperturasConHora"));
            aperturasSH = Float.parseFloat(this.porcentajes.getString("aperturasSinHora"));

            dataPie.add(new PieEntry(Math.round((aperturasCH*100)/Integer.parseInt(this.total)), "aperturas recibidas: "+Math.round((aperturasCH*100)/Integer.parseInt(this.total))+"%"));
            dataPie.add(new PieEntry(Math.round((aperturasSH*100)/Integer.parseInt(this.total)), "aperturas faltantes: "+Math.round((aperturasSH*100)/Integer.parseInt(this.total))+"%"));
            PieDataSet dataSet = new PieDataSet(dataPie, "");
            dataSet.setColors(
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gverde)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.grojo)).substring(2))));
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);

            pieChart.setData(data);
            pieChart.invalidate();
            pieChart.animateY(1400, Easing.EaseInOutQuad);
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),"Error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void loadPieChartDataApCiGoD2() {
        float cierresCH,cierresSH;
        try {
            cierresCH = Float.parseFloat(this.porcentajes.getString("cierresConHora"));
            cierresSH = Float.parseFloat(this.porcentajes.getString("cierresSinHora"));

            dataPie.add(new PieEntry(Math.round((cierresCH*100)/Integer.parseInt(this.total)), "cierres recibidos: "+Math.round((cierresCH*100)/Integer.parseInt(this.total))+"%"));
            dataPie.add(new PieEntry(Math.round((cierresSH*100)/Integer.parseInt(this.total)), "cierres faltantes: "+Math.round((cierresSH*100)/Integer.parseInt(this.total))+"%"));
            PieDataSet dataSet = new PieDataSet(dataPie, "");
            dataSet.setColors(
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gverde)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.grojo)).substring(2))));
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);

            pieChart.setData(data);
            pieChart.invalidate();

            pieChart.animateY(1400, Easing.EaseInOutQuad);
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),"Error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void loadBarChartDataApCiGoD() {
        float aperturasCH,aperturasSH;
        try {
            aperturasCH = Float.parseFloat(this.porcentajes.getString("aperturasConHora"));
            aperturasSH = Float.parseFloat(this.porcentajes.getString("aperturasSinHora"));

            dataBars.add(new BarEntry(1,Math.round((aperturasCH*100)/Integer.parseInt(this.total))));
            dataBars.add(new BarEntry(2,Math.round((aperturasSH*100)/Integer.parseInt(this.total))));

            String[] labels = new String[]{"",
                    "aperturas recibidas: "+Math.round((aperturasCH*100)/Integer.parseInt(this.total))+"%",
                    "aperturas faltantes: "+Math.round((aperturasSH*100)/Integer.parseInt(this.total))+"%"
            };
            BarDataSet barDataSet = new BarDataSet(dataBars,"data");
            barDataSet.setColors(
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gverde)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.grojo)).substring(2))));
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
            xAxis.setValueFormatter( new IndexAxisValueFormatter( labels ) );
            xAxis.setPosition( XAxis.XAxisPosition.BOTTOM );
            xAxis.setGranularity( 1 );
            xAxis.setLabelRotationAngle( -70 );
            xAxis.setTextSize( 15f );
            xAxis.setGranularityEnabled( true );

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),"Error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void loadBarChartDataApCiGoD2() {
        float cierresCH,cierresSH;
        try {
            cierresCH = Float.parseFloat(this.porcentajes.getString("cierresConHora"));
            cierresSH = Float.parseFloat(this.porcentajes.getString("cierresSinHora"));

            dataBars.add(new BarEntry(1,Math.round((cierresCH*100)/Integer.parseInt(this.total))));
            dataBars.add(new BarEntry(2,Math.round((cierresSH*100)/Integer.parseInt(this.total))));

            String[] labels = new String[]{"",
                    "cierres recibidos: "+Math.round((cierresCH*100)/Integer.parseInt(this.total))+"%",
                    "cierres faltantes: "+Math.round((cierresSH*100)/Integer.parseInt(this.total))+"%"
            };
            BarDataSet barDataSet = new BarDataSet(dataBars,"data");
            barDataSet.setColors(
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.gverde)).substring(2))),
                    Color.parseColor("#"+String.valueOf(Integer.toHexString(activity.getColor(R.color.grojo)).substring(2))));
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
            xAxis.setValueFormatter( new IndexAxisValueFormatter( labels ) );
            xAxis.setPosition( XAxis.XAxisPosition.BOTTOM );
            xAxis.setGranularity( 1 );
            xAxis.setLabelRotationAngle( -70 );
            xAxis.setTextSize( 15f );
            xAxis.setGranularityEnabled( true );

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),"Error\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
