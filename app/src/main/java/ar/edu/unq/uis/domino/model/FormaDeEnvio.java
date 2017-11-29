package ar.edu.unq.uis.domino.model;

import java.io.Serializable;

/**
 * Created by Carla on 27/11/2017.
 */

public class FormaDeEnvio implements Serializable {
    String nombre;
    String direccion = "";
    Double costo = 0.0;

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Double getCosto() {
        return costo;
    }
}
