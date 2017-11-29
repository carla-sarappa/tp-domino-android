package ar.edu.unq.uis.domino.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Carla on 27/11/2017.
 */


class IngredienteDistribuido implements Serializable {
    @SerializedName("ingredienteDistribuido.ingrediente.nombre")
    String ingrediente;
    String distribucion;
    @SerializedName("ingredienteDistribuido.ingrediente.precio")
    Double precio;
}
