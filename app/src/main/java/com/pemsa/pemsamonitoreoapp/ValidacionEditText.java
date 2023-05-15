package com.pemsa.pemsamonitoreoapp;

import android.util.Patterns;

public class ValidacionEditText {
    public  boolean isEmail(String cadena) {
        boolean resultado;
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            resultado = true;
        } else {
            resultado = false;
        }
        return resultado;
    }
    public boolean iguales(String pass1,String pass2){
        if(pass1.equals(pass2)){
            return true;
        }else {
            return false;
        }
    }


}
