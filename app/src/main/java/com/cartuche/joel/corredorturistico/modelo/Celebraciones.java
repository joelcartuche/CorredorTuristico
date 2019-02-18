package com.cartuche.joel.corredorturistico.modelo;

public class Celebraciones {

    String codigo;
    String nombre;
    String fecha;
    String zona;

    public Celebraciones(String codigo, String nombre, String fecha, String zona) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.zona = zona;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
