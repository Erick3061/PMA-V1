package com.pemsa.pemsamonitoreoapp.ui.cerrarsesion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.pemsa.pemsamonitoreoapp.InicioSesion;
import com.pemsa.pemsamonitoreoapp.R;

public class cerrarsesionFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cerrarsesion, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ocultaTeclado();
        dialogocerrarsesion(view);
    }

    private void dialogocerrarsesion(Object v){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getLayoutInflater();
        final View view=inflater.inflate(R.layout.dialog_cerrar_sesion,null);

        final NavController navController=Navigation.findNavController((View) v);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogredondo);
        dialog.show();
        //se hacen los instancias a los objetos correspondientes
        Button btnNo,btnSi;
        btnNo=(Button)view.findViewById(R.id.btnNo);
        btnSi=(Button)view.findViewById(R.id.btnSi);
        //se hace las acciones de los botones
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"...",Toast.LENGTH_LONG).show();
                navController.navigate(R.id.nav_home);
                dialog.dismiss();
            }
        });
        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irAIniciosesion = new Intent(v.getContext(), InicioSesion.class);
                irAIniciosesion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(irAIniciosesion,0);
                dialog.dismiss();
                //Toast.makeText(v.getContext(),"saliendo...",Toast.LENGTH_SHORT).show();
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
