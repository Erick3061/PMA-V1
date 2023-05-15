package com.pemsa.pemsamonitoreoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public static LinearLayout.LayoutParams lp;
    public static LinearLayout.LayoutParams lp2;

    public SlideAdapter(Context context){
        this.context=context;
    }
    //arrays
    public int[] slide_images ={
            R.mipmap.group12,
            R.mipmap.group12,
            R.mipmap.group12
    };
    public String[] slide_titulos={
            "PEMSA monitoreo APP",
            "¿Para qué sirve la aplicación?",
            "¿Como me registro en la aplicación?"
    };
    public String[] slide_description={
            "Ahora nos acercamos a usted para brindarle el acceso de información de su sistema de alarma.\n" +
                    "",

            "Podrá realizar consultas de los eventos recibidos en la central de monitoreo.\n" +
                            "\n"+
                            "Descargar en formato PDF las consultas realizadas.\n",


                    "1: Seleccionar el botón de registro.\n" +
                    "2: Llenar los campos solicitados.\n" +
                    "3: Aceptar los términos y condiciones y aviso de privacidad.\n" +
                    "4: Seleccionar el botón registrar.\n"+
                    "\nCualquier duda o problema tecnico sobre su sistema de alarma que se presente, podrá comunicarse con nosotros y lo atenderemos con gusto.\n" +
                    "\nCorreo electronico:\n"+
                    "correo@pem-sa.com\n"+
                    "\nNumero telefonico:\n"+
                    "222-141-12-30"
    };


    @Override
    public int getCount() {
        return slide_titulos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView =(ImageView)view.findViewById(R.id.slide_image);
        TextView slidetitulos=(TextView)view.findViewById(R.id.slide_titulo);
        TextView slideDesc=(TextView)view.findViewById(R.id.slide_description);

        slideImageView.setImageResource(slide_images[position]);
        slidetitulos.setText(slide_titulos[position]);
        slideDesc.setText(slide_description[position]);
        container.addView(view);

        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp2 = new LinearLayout.LayoutParams(0, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
