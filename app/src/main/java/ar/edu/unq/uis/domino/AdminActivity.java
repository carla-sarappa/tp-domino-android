package ar.edu.unq.uis.domino;

import android.app.Activity;

import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import retrofit2.Call;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public class AdminActivity extends PedidosActivity {

    @Override
    protected Call<List<Pedido>> getPedidos() {
        return service.getPedidos("EnViaje");
    }

}
