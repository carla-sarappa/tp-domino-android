package ar.edu.unq.uis.domino;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import retrofit2.Call;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public class ClienteActivity extends PedidosActivity {

    public Integer getUserId(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return Integer.valueOf(prefs.getString("user_id", "4"));
    }

    @Override
    protected Call<List<Pedido>> getPedidos() {
        return service.getPedidos(getUserId());
    }
}
