package com.cartuche.joel.corredorturistico.modelo;

public class Imagenes {
    String imagenes;
    String nombre;
    String nombreZona;

    public Imagenes(String imagenes, String nombre,String nombreZona) {
        this.imagenes = imagenes;
        this.nombre = nombre;
        this.nombreZona = nombreZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
