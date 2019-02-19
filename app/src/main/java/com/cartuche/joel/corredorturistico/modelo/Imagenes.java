package com.cartuche.joel.corredorturistico.modelo;

public class Imagenes {
    String imagenes;
    String nombre;

    public Imagenes(String imagenes, String nombre) {
        this.imagenes = imagenes;
        this.nombre = nombre;
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
