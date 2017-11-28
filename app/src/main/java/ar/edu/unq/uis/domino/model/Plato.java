package ar.edu.unq.uis.domino.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Carla on 27/11/2017.
 */

public class Plato implements Serializable {
    Pizza pizza;
    List<IngredienteDistribuido> extras;
}
