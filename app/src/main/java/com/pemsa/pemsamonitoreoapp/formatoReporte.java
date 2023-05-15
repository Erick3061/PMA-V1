package com.pemsa.pemsamonitoreoapp;

public class formatoReporte {
    private String NombreReporte;
    public  formatoReporte(){

    }

    public formatoReporte(String nombre) {
        this.NombreReporte = nombre;
    }

    public String getNombreReporte() {
        return NombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        NombreReporte = nombreReporte;
    }
}
