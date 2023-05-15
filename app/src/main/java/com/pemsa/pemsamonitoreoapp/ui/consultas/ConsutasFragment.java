package com.pemsa.pemsamonitoreoapp.ui.consultas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pemsa.pemsamonitoreoapp.AperturaCierre;
import com.pemsa.pemsamonitoreoapp.AperturaCierreGrupo;
import com.pemsa.pemsamonitoreoapp.CuentasAbiertas;
import com.pemsa.pemsamonitoreoapp.EventoAlarma;
import com.pemsa.pemsamonitoreoapp.ProblemaBateria;
import com.pemsa.pemsamonitoreoapp.R;
import com.pemsa.pemsamonitoreoapp.mostrardatos;
import com.pemsa.pemsamonitoreoapp.reporteAvanzado;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pemsa.pemsamonitoreoapp.API.getCuentas;
import com.pemsa.pemsamonitoreoapp.API.getGrupos;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsutasFragment extends Fragment {

    public static NavigationView navigationheader;
    public static TextView nombre,correo,Id,tCG,Token,prueba;
    public static Spinner spinner;
    public static Spinner spinnerGrupos;
    public static ArrayList<String> LisCuentas=new ArrayList<String>();
    public static ArrayList<String> LisGrupos=new ArrayList<String>();
    public static ArrayList rows=new ArrayList();
    public static boolean sihay=false;
    public static String[] separado,separado1,separado2,separado3,separado4;
    public static String N,C,token,NombreCuenta,Correo,Uid;
    public static mostrardatos mostrardatos=new mostrardatos();
    public static String IdCuenta, Dat,IdGrupo;
    public static boolean isActivo=false,creapdf=false,creado=false;
    public static String[] listItems;
    public static boolean[] checkedItems;
    public static JSONArray cuentas,grupos;

    public static LinearLayout.LayoutParams lp;
    public static LinearLayout.LayoutParams lp2;
    public static LinearLayout linearLayoutCA;
    public static LinearLayout linearLayoutProblemaBat;
    public static LinearLayout linearLayoutAPCIG;
    public static LinearLayout linearLayoutTG;
    public static LinearLayout linearLayoutTAvanzado;
    public static LinearLayout linearLayoutAvanzada;
    public static String item = "";

    public static Activity activity;

    public static getCuentas hiloCuentas;
    public static getGrupos hilo2Grupos;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isActivo=false;
        ocultaTeclado();
        View root = inflater.inflate(R.layout.fragment_consultas, container, false);
        //Obtener los datos del navigation--------------------------------------------------------------------------------------
        navigationheader=(NavigationView)getActivity().findViewById(R.id.nav_view);
        View view=navigationheader.getHeaderView(0);
        nombre=(TextView)view.findViewById(R.id.menuNombre);
        correo=(TextView)view.findViewById(R.id.menuCorreo);
        Id=(TextView)view.findViewById(R.id.menuIdUsuario);
        Token=(TextView)view.findViewById(R.id.menuToken);
        tCG=(TextView)view.findViewById(R.id.txtC3);
        //---------------------------------------------------------------------------------------------------------------------
        N=nombre.getText().toString();
        C=correo.getText().toString();
        token=Token.getText().toString();
        Uid=Id.getText().toString();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isActivo=false;
        prueba = view.findViewById(R.id.text_consultas);
        final Button btnConsultaApCi=view.findViewById(R.id.btnAP_CI);
        final Button btnConsultaProblemasBateria=view.findViewById(R.id.btnProblemasDeBateria);
        final Button btnConsultaEA=view.findViewById(R.id.btnAlarma);
        final Button btnCuentasAbiertas=view.findViewById(R.id.btnAbiertas);
        final Button btnAPCIGrupo=view.findViewById(R.id.btnAPCIGrupo);
        final Button btnSeleccionarCuentas=view.findViewById(R.id.btnSeleccionarCuentas);

        final FloatingActionButton btnQApCi=view.findViewById(R.id.btnQAP_CI);
        final FloatingActionButton btnQPB=view.findViewById(R.id.btnQProblemasDeBateria);
        final FloatingActionButton btnQAlarma=view.findViewById(R.id.btnQAlarma);
        final FloatingActionButton btnQAbiertas=view.findViewById(R.id.btnQAbiertas);
        final FloatingActionButton btnQApciGrupo=view.findViewById(R.id.btnQAPCIGrupo);

        TextView text=view.findViewById(R.id.text_consultas_avanzadas);

        linearLayoutCA = view.findViewById( R.id.LLCA );
        linearLayoutProblemaBat = view.findViewById( R.id.LLPB );
        linearLayoutAPCIG = view.findViewById( R.id.LLAPCIG );
        linearLayoutTG = view.findViewById( R.id.TG );
        linearLayoutTAvanzado = view.findViewById( R.id.TAvanzada );
        linearLayoutAvanzada = view.findViewById( R.id.LLAvanzada );
        //parametros para mostrar u ocultar
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp2 = new LinearLayout.LayoutParams(0, 0);

        activity=getActivity();

        spinner =(Spinner)view.findViewById(R.id.CuentasM);
        spinnerGrupos=(Spinner)view.findViewById(R.id.Grupo);

        hiloCuentas=new getCuentas();
        hiloCuentas.setActivity(activity);
        hiloCuentas.execute("1",Uid+"___ESP___"+token);
        btnConsultaApCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ObtenSpinner;
                ObtenSpinner = spinner.getSelectedItem().toString();
                NombreCuenta=ObtenSpinner;
                JSONObject datos = null;
                datos = cuenta(NombreCuenta);
                IrAConsulta(datos,1);
            }
        });
        btnConsultaEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ObtenSpinner;
                ObtenSpinner = spinner.getSelectedItem().toString();
                NombreCuenta=ObtenSpinner;
                JSONObject datos = null;
                datos = cuenta(NombreCuenta);
                IrAConsulta(datos,2);
            }
        });

        btnConsultaProblemasBateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LisGrupos.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"No tiene grupos",Toast.LENGTH_SHORT).show();
                }else{
                    String ObtenSpinner;
                    ObtenSpinner = spinnerGrupos.getSelectedItem().toString();
                    JSONObject datos = null;
                    datos = grupo(ObtenSpinner);
                    try {
                        Intent iraPB = new Intent(getContext(), ProblemaBateria.class);
                        iraPB.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        iraPB.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        iraPB.putExtra("Correo", C);
                        iraPB.putExtra("Token", token);
                        iraPB.putExtra("Uid",Uid);
                        iraPB.putExtra("NG",datos.getString("name"));
                        iraPB.putExtra("CG",datos.getString("codeMW"));
                        iraPB.putExtra("Type",datos.getString("type"));
                        activity.startActivityForResult(iraPB, 0);
                    } catch (Exception e) {
                        Toast.makeText(activity.getApplicationContext(),"Error intente de nuevo"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnCuentasAbiertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LisGrupos.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"No tiene grupos",Toast.LENGTH_SHORT).show();
                }else{
                    String ObtenSpinner;
                    ObtenSpinner = spinnerGrupos.getSelectedItem().toString();
                    JSONObject datos = null;
                    datos = grupo(ObtenSpinner);
                    //Toast.makeText(activity.getApplicationContext(),datos.toString(),Toast.LENGTH_SHORT).show();
                    try {
                        Intent iraCA = new Intent(getContext(), CuentasAbiertas.class);
                        iraCA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        iraCA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        iraCA.putExtra("Correo", C);
                        iraCA.putExtra("Token", token);
                        iraCA.putExtra("Uid",Uid);
                        iraCA.putExtra("NG",datos.getString("name"));
                        iraCA.putExtra("CG",datos.getString("codeMW"));
                        iraCA.putExtra("Type",datos.getString("type"));
                        startActivityForResult(iraCA, 0);
                    } catch (JSONException e) {
                        Toast.makeText(activity.getApplicationContext(),"Error intente de nuevo"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnAPCIGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LisGrupos.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"No tiene grupos",Toast.LENGTH_SHORT).show();
                }else{
                    String ObtenSpinner;
                    ObtenSpinner = spinnerGrupos.getSelectedItem().toString();
                    JSONObject datos = null;
                    datos = grupo(ObtenSpinner);
                    //Toast.makeText(activity.getApplicationContext(),datos.toString(),Toast.LENGTH_SHORT).show();
                    try {
                        Intent iraAPCIG = new Intent(getContext(), AperturaCierreGrupo.class);
                        iraAPCIG.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        iraAPCIG.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        iraAPCIG.putExtra("Token", token);
                        iraAPCIG.putExtra("NG",datos.getString("name"));
                        iraAPCIG.putExtra("CG",datos.getString("codeMW"));
                        iraAPCIG.putExtra("Type",datos.getString("type"));
                        startActivityForResult(iraAPCIG, 0);
                    } catch (JSONException e) {
                        Toast.makeText(activity.getApplicationContext(),"Error intente de nuevo"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnSeleccionarCuentas.setOnClickListener(new View.OnClickListener() {
            ArrayList<Integer> mUserItems = new ArrayList<>();
            @Override
            public void onClick(View v) {

                item="";
                listItems=new String[LisCuentas.size()];
                listItems=mostrardatos.ConvertListToArray(LisCuentas);
                checkedItems = new boolean[listItems.length];
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(activity);
                mBuilder.setTitle("Selecionar cuentas");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);

                mBuilder.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item+" ";
                            }
                        }
                        for (int j=0;j<checkedItems.length;j++) {
                            checkedItems[j] = false;
                            mUserItems.clear();
                        }
                        if(!item.equals("")){
                            Intent intent = new Intent(activity.getApplicationContext(), reporteAvanzado.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("Cuentas",item);
                            intent.putExtra("Token",token);
                            intent.putExtra("JSON",cuentas.toString());
                            startActivityForResult(intent,0);
                        }else{
                            Toast.makeText(activity.getApplicationContext(),"Se debe colocar al menos una cuenta",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j=0;j<checkedItems.length;j++) {
                            checkedItems[j] = false;
                            mUserItems.clear();
                        }
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
                //Toast.makeText(activity.getApplicationContext(),String.valueOf(LisCuentas.size()),Toast.LENGTH_SHORT).show();
            }
        });
            //accion ´para ver el comentario
            btnQPB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPB();
                }
            });

            btnQApCi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogApci();
                }
            });
            btnQAlarma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogAlarma();
                }
            });
            btnQAbiertas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCuentasAbiertas();
                }
            });
            btnQApciGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogApciG();
                }
            });
        /*}else {
            Toast.makeText(view.getContext(),"NO HAS REGISTRADO NINGUNA CUENTA",Toast.LENGTH_LONG).show();
        }*/

    }

    private void dialogPB(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_problema_bateria,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_problema_bateria,null))
                .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }

    private void dialogApci(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_apci,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_apci,null))
                .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }
    private void dialogApciG(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_apci_grupo,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_apci_grupo,null))
                .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }
    private void dialogAlarma(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_alarma,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_alarma,null))
                .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }
    private void dialogCuentasAbiertas(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_cuentas_abiertas,null);
        builder.setView(view);
        builder.setView(inflater.inflate(R.layout.dialog_cuentas_abiertas,null))
                .setPositiveButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog=builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
    }
    public void ocultaTeclado(){
        View view=this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public static void ObtenerCuentas(ArrayList<String> List) {
        if(!List.isEmpty()) {
            LisCuentas=List;
            if(LisCuentas.size()==1){
                linearLayoutTAvanzado.setVisibility(View.INVISIBLE);
                linearLayoutTAvanzado.setLayoutParams(lp2);
                //ocultamos el linear layout del boton Aperturas Cierres Grupos
                linearLayoutAvanzada.setVisibility(View.INVISIBLE);
                linearLayoutAvanzada.setLayoutParams(lp2);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, List);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);

            hilo2Grupos=new getGrupos();
            hilo2Grupos.setIdentificador(1);
            hilo2Grupos.setActivity(activity);
            hilo2Grupos.execute("1",Uid+"___ESP___"+token);
            //prueba.setText(token);
        }

    }
    public static void ObtenerGrupos(ArrayList<String> List){

        if(!List.isEmpty()) {
            LisGrupos=List;
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(activity.getApplicationContext(),android.R.layout.simple_list_item_1,List);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGrupos.setAdapter(arrayAdapter2);
        }else{
            //Toast.makeText(activity.getApplicationContext(),"No Tiene grupos",Toast.LENGTH_LONG).show();
            //ocultamos el spinner
            spinnerGrupos.setVisibility(View.INVISIBLE);
            spinnerGrupos.setLayoutParams(lp2);
            //ocultamos el linear layout de boton Problema de bateria
            linearLayoutProblemaBat.setVisibility(View.INVISIBLE);
            linearLayoutProblemaBat.setLayoutParams(lp2);
            //ocultamos el linear layout de boton Cuentas Abiertas
            linearLayoutCA.setVisibility(View.INVISIBLE);
            linearLayoutCA.setLayoutParams(lp2);
            //ocultamos el linear layout del boton Aperturas Cierres Grupos
            linearLayoutAPCIG.setVisibility(View.INVISIBLE);
            linearLayoutAPCIG.setLayoutParams(lp2);
            //ocultamos el texto
            linearLayoutTG.setVisibility(View.INVISIBLE);
            linearLayoutTG.setLayoutParams(lp2);
        }

    }
    public JSONObject cuenta(String seleccioando){
        JSONObject cuenta =null;
        for (int i =0;i<cuentas.length();i++){
            try {
                JSONObject cuentaB =new JSONObject(cuentas.get(i).toString());
                if((cuentaB.getString("name")).trim().equals(seleccioando)){
                    return cuentaB;
                }
            } catch (JSONException e) {
                Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                return cuenta;
            }
        }
        return cuenta;
    }
    public JSONObject grupo(String seleccioando){
        JSONObject group =null;
        for (int i =0;i<grupos.length();i++){
            try {
                JSONObject grupoB =new JSONObject(grupos.get(i).toString());
                if((grupoB.getString("name")).trim().equals(seleccioando)){
                    return grupoB;
                }
            } catch (JSONException e) {
                Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                return group;
            }
        }
        return group;
    }
    public static void IrAConsulta(JSONObject JSON,int IDIR){
        if(IDIR==1){
            try {
                Intent iraApCi = new Intent(activity.getApplicationContext(),AperturaCierre.class);
                iraApCi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                iraApCi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iraApCi.putExtra("IdCuenta", JSON.getString("codeMW"));
                iraApCi.putExtra("Correo", Correo);
                iraApCi.putExtra("IdUsurioApp", Uid);
                iraApCi.putExtra("Nombre", JSON.getString("name"));
                iraApCi.putExtra("CodigoAbonado", JSON.getString("codeA"));
                iraApCi.putExtra("Direccion", JSON.getString("Direccion"));
                iraApCi.putExtra("Token", token);
                activity.startActivityForResult(iraApCi, 0);
            } catch (JSONException e) {
                Toast.makeText(activity.getApplicationContext(),"Error intente de nuevo"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        if(IDIR==2){
            try {
                Intent iraApCi = new Intent(activity.getApplicationContext(), EventoAlarma.class);
                iraApCi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                iraApCi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iraApCi.putExtra("IdCuenta", JSON.getString("codeMW"));
                iraApCi.putExtra("Correo", Correo);
                iraApCi.putExtra("IdUsurioApp", Uid);
                iraApCi.putExtra("Nombre", JSON.getString("name"));
                iraApCi.putExtra("CodigoAbonado", JSON.getString("codeA"));
                iraApCi.putExtra("Direccion", JSON.getString("Direccion"));
                iraApCi.putExtra("Token", token);
                activity.startActivityForResult(iraApCi, 0);
            } catch (JSONException e) {
                Toast.makeText(activity.getApplicationContext(),"Error intente de nuevo"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }


}
