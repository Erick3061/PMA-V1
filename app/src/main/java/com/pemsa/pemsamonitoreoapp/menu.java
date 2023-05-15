package com.pemsa.pemsamonitoreoapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class menu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView nombre,correo, Uid,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ocultaTeclado();
        /////////////////////////////////////////////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_consultas,R.id.nav_cerrarsesion,R.id.nav_cambiarcontra,R.id.nav_acercade)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //obtener el valor mandado desde el inicio de sesion___________________________________________________________________________________
        String Nombre = getIntent().getStringExtra("Nombre");
        String Token = getIntent().getStringExtra("Token");
        String Correo = getIntent().getStringExtra("Correo");
        String UID = getIntent().getStringExtra("Uid");
        //modificar los datos del menuheader___________________________________________________________________________________________________
        NavigationView navigationheader;
        navigationheader=(NavigationView)findViewById(R.id.nav_view);
        View view=navigationheader.getHeaderView(0);
        nombre = (TextView)view.findViewById( R.id.menuNombre );
        correo = (TextView)view.findViewById( R.id.menuCorreo );
        Uid = (TextView)view.findViewById( R.id.menuIdUsuario );
        token = (TextView)view.findViewById( R.id.menuToken );

        nombre.setText(Nombre);
        correo.setText(Correo);
        Uid.setText(UID);
        token.setText(Token);
        //______________________________________________________________________________________________________________________________________
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void ocultaTeclado(){
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}