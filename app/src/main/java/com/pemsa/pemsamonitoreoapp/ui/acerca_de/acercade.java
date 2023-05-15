package com.pemsa.pemsamonitoreoapp.ui.acerca_de;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.pemsa.pemsamonitoreoapp.BuildConfig;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.TCAP.TCAP;

public class acercade extends Fragment {
    TextView terminos;
    TextView version;
    public static Activity activity;
    public acercade() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_acercade, container, false);
        //Obtener los datos del navigation--------------------------------------------------------------------------------------

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity=getActivity();
        terminos=(TextView)view.findViewById(R.id.TermYCondYAviPriv2);
        version=(TextView)view.findViewById(R.id.version);
        version.setText("Versión: "+BuildConfig.VERSION_NAME);
        terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TCAP tcap = new TCAP();
                tcap.terminosycondiciones(activity);
            }
        });
    }
}