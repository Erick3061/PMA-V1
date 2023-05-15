package com.pemsa.pemsamonitoreoapp;

public class formato {
    private String Fecha;
    private String DEvento;
    private String NCA;
    private int tipoImagen;

    public  formato(){

    }

    public formato(String fecha, String DEvento,String NombreCA,int tipoImagen) {
        this.Fecha = fecha;
        this.DEvento = DEvento;
        this.NCA=NombreCA;
        this.tipoImagen=tipoImagen;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getDEvento() {
        return DEvento;
    }

    public void setDEvento(String DEvento) {
        this.DEvento = DEvento;
    }

    public String getNCA() { return NCA;}

    public void setNCA(String NCA) { this.NCA = NCA;}

    public int getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(int tipoImagen) {
        this.tipoImagen = tipoImagen;
    }
}
