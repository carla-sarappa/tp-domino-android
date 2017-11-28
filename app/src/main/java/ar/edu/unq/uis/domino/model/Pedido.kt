package ar.edu.unq.uis.domino.model

import java.io.Serializable

/**
 * Created by Carla on 27/11/2017.
 */


public class Pedido: Serializable {
    var platos: List<Plato>? = null
    var cliente: Int? = null
    var aclaraciones: String? = null
    var formaDeEnvio: FormaDeEnvio? = null
    var monto: Double? = 0.0

    fun getNombre():String {
        return platos?.
                map { it.pizza.nombre }?.
                joinToString(", ")?: ""
    }

    fun getDireccion(): String {
        return formaDeEnvio?.direccion?:"Retira por el local"
    }

}
