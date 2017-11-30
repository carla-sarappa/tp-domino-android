package ar.edu.unq.uis.domino;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import retrofit2.Call;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public class ClienteActivity extends PedidosActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //https://developer.android.com/training/basics/fragments/fragment-ui.html
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PedidosByIdFragment())
                .commit();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    public static class PedidosByIdFragment extends PedidosFragment{

        public Integer getUserId(){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            return Integer.valueOf(prefs.getString("user_id", "4"));
        }

        @Override
        protected Call<List<Pedido>> getPedidos() {
            return service.getPedidos(getUserId());
        }
    }
}
