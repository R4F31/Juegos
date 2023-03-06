package com.example.juegos;

public class PuntuacionItem {
    private String nombre;
    private int puntuacion;


    public PuntuacionItem(String nombre, int puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public PuntuacionItem(){

    }

    public String getNombre() {
        return nombre;
    }


    public int getPuntuacion() {
        return puntuacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
