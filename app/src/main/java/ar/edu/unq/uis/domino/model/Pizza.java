package ar.edu.unq.uis.domino.model;

import java.io.Serializable;

public class Pizza implements Serializable {
    String nombre;
    Double precio;

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }
}
