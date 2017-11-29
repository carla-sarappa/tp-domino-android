package ar.edu.unq.uis.domino.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Carla on 27/11/2017.
 */

public class Plato implements Serializable {
    Pizza pizza;
    List<IngredienteDistribuido> extras;
    Double monto;

    public Pizza getPizza() {
        return pizza;
    }

    public double getMonto() {
        return monto;
    }

    public List<IngredienteDistribuido> getExtras() {
        return extras;
    }
}
