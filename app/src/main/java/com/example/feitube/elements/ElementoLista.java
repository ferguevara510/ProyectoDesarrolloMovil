package com.example.feitube.elements;

public class ElementoLista {
    public String nombre;
    public String proyecto;
    public String director;
    public String cordinador;
    public String sinodal;
    public String id;

    public ElementoLista(String nombre, String proyecto, String director, String cordinador, String sinodal,String id) {
        this.nombre = nombre;
        this.proyecto = proyecto;
        this.director = director;
        this.cordinador = cordinador;
        this.sinodal = sinodal;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCordinador() {
        return cordinador;
    }

    public void setCordinador(String cordinador) {
        this.cordinador = cordinador;
    }

    public String getSinodal() {
        return sinodal;
    }

    public void setSinodal(String sinodal) {
        this.sinodal = sinodal;
    }
}
