package com.pemsa.pemsamonitoreoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class inicio extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private Button btnB,btnN,btnIApp;
    private CheckBox entranoentra;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme_NoActionBar2);
        //saber si ya se ha entrado en esta activity antes
        if (restorePrefData()) {
            Intent irainiciarsesion = new Intent(getApplicationContext(),InicioSesion.class );
            startActivity(irainiciarsesion);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //hacemos los onjetos msSlideView pager y mDotLAyut
        mSlideViewPager=(ViewPager)findViewById(R.id.slideViewPager);
        mDotLayout=(LinearLayout)findViewById(R.id.dotsLayout);
        //mandamos a llamar a Slide Adapter
        SlideAdapter slideAdapter = new SlideAdapter(this);
        mSlideViewPager.setAdapter(slideAdapter);
        btnB=(Button)findViewById(R.id.btnback);
        btnN=(Button)findViewById(R.id.btnnext);
        btnIApp=(Button)findViewById(R.id.btnirAapp);
        entranoentra=(CheckBox)findViewById(R.id.checar);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        //se hacen las acciones de los botones
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
        btnIApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrefsData();
                Intent intent=new Intent(v.getContext(),InicioSesion.class);
                startActivityForResult(intent,0);
                finish();
            }
        });

    }

    public void addDotsIndicator(int pos){
        mDots=new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.azul));

            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[pos].setTextColor(getResources().getColor(R.color.rojo));
        }
    }
    ViewPager.OnPageChangeListener viewListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage=position;
            //hacemos las sentacias de cuando apareceran los botones
            if(position==0){
                btnN.setEnabled(true);
                btnB.setEnabled(false);
                btnB.setVisibility(View.INVISIBLE);
                btnIApp.setEnabled(false);
                btnIApp.setVisibility(View.INVISIBLE);
                entranoentra.setEnabled(false);
                entranoentra.setVisibility(View.INVISIBLE);
                btnN.setText("SIGUIENTE");
                btnB.setText("");
            }else {
                if(position==mDots.length-1){
                    btnN.setEnabled(true);
                    btnB.setEnabled(true);
                    btnIApp.setEnabled(true);
                    entranoentra.setEnabled(true);
                    btnN.setVisibility(View.INVISIBLE);
                    btnB.setVisibility(View.VISIBLE);
                    btnIApp.setVisibility(View.VISIBLE);
                    entranoentra.setVisibility(View.VISIBLE);
                    //btnN.setText("IR A LA APP");
                    btnB.setText("ATRAS");
                }else {
                    btnN.setEnabled(true);
                    btnB.setEnabled(true);
                    btnIApp.setEnabled(false);
                    btnIApp.setVisibility(View.INVISIBLE);
                    entranoentra.setEnabled(false);
                    entranoentra.setVisibility(View.INVISIBLE);
                    btnN.setVisibility(View.VISIBLE);
                    btnB.setVisibility(View.VISIBLE);
                    btnN.setText("SIGUIENTE");
                    btnB.setText("ATRAS");
                }
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        //true para decir que si entro
        //false para decir que no entro
        if(entranoentra.isChecked()){
            editor.putBoolean("isIntroOpnend",true);
        }else{
            editor.putBoolean("isIntroOpnend",false);
        }

        editor.apply();
    }
}