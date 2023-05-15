package com.pemsa.pemsamonitoreoapp.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pemsa.pemsamonitoreoapp.API.getContacto;
import com.pemsa.pemsamonitoreoapp.R;

public class HomeFragment extends Fragment {

    ImageButton F,T,I,W,Web;
    public static  String Mensajeria;
    public static  String FacebookId;
    public static  String FacebookUrl;
    public static  String Instagram;
    public static  String Twiter;

    @SuppressLint("StaticFieldLeak")
    public static TextView correo;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ocultaTeclado();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ImageView imageView = root.findViewById(R.id.imgHome);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.logo3));
        return root;
    }
    public static Activity activity;

    getContacto hilo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //se instancian los objetos
        F=(ImageButton)view.findViewById(R.id.btnFacebook);
        T=(ImageButton)view.findViewById(R.id.btnTwitter);
        I=(ImageButton)view.findViewById(R.id.btnInstagram);
        W=(ImageButton)view.findViewById(R.id.btnWhasapp);
        Web=(ImageButton)view.findViewById(R.id.btnPagWeb);
        correo=(TextView)view.findViewById(R.id.Homecorreo);

        //se hacen las cciones  de la aplicacion
        activity=getActivity();

        hilo=new getContacto();
        hilo.setActivity(activity);
        hilo.execute("1");

        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookId="fb:"+FacebookId;
                String urlPage = "https:"+FacebookUrl;
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
                } catch (Exception e) {
                    Toast.makeText(v.getContext(),"LA APLICACION DE FACEBOOK NO ESTA INSTALADA",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }

            }
        });
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link="https:"+Twiter;
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link="https:"+Instagram;
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        W.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link="https:"+Mensajeria;
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        Web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link="https://www.pem-sa.com/";
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }
    public void ocultaTeclado(){
        View view=this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}