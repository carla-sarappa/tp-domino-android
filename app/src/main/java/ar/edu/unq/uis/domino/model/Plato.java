package ar.edu.unq.uis.domino.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Carla on 27/11/2017.
 */

public class Plato implements Serializable {
    Pizza pizza;
    List<IngredienteDistribuido> extras;
    Double precio;

    public Pizza getPizza() {
        return pizza;
    }



    public List<IngredienteDistribuido> getExtras() {
        return extras;
    }

    public String getExtrasAsString(){
        return getExtras().stream().map((extra)-> extra.ingrediente).collect(Collectors.joining(", "));

    }

    public double getPrecio() {
        return precio;
    }
}
