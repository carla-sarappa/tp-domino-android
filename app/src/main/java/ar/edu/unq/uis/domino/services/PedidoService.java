package ar.edu.unq.uis.domino.services;

import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Carla Sarappa on 27/11/2017.
 */

public interface PedidoService {
    @GET("/pedidos")
    Call<List<Pedido>> getPedidos(@Query("userId") Integer userId);


}
