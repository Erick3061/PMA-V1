package com.pemsa.pemsamonitoreoapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Iterator;

public class mostrardatos {

    public void borrarTabla(TableLayout tableLayout){
        int count = tableLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = tableLayout.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }
    public String[] ConvertListToArray(ArrayList<String> lista){
        Iterator it= lista.iterator();
        String ARRAY = null;
        String[] A=new String[lista.size()];
        int ctn=0;
        while(it.hasNext()){
            ARRAY= (String) it.next();
            A[ctn]=ARRAY.replace(" ",".");
            ctn++;
        }
        return A;
    }
    public String[] separar(String Nombre){
        String []  bar = Nombre. split(" ");
        return bar;
    }

    public float porcentaje(int d1, int total){
        float p=0;
        p = (d1*100)/total;
        return p;
    }
}
