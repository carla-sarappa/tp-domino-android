package ar.edu.unq.uis.domino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import ar.edu.unq.uis.domino.services.PedidoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Carla Sarappa on 24/11/2017.
 */

public class PedidosActivity extends AppCompatActivity {

    RecyclerView pedidosRecyclerView;
    PedidosAdapter pedidosAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View mensajeVacio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.pedidosRecyclerView = findViewById(R.id.pedidos);
        this.pedidosAdapter = new PedidosAdapter();
        this.pedidosRecyclerView.setAdapter(pedidosAdapter);
        this.pedidosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mensajeVacio = findViewById(R.id.mensaje_vacio);
        populateAdapter();
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateAdapter();
            }
        });
    }

    public void populateAdapter(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String url = prefs.getString("server_url", "http://192.168.0.5:9000");
        Integer userId = Integer.valueOf(prefs.getString("user_id", "4"));
        Log.d("PedidosActivity", "Buscando pedidos de user " + userId + " en " + url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PedidoService service = retrofit.create(PedidoService.class);
        service.getPedidos(userId).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                pedidosAdapter.elementos = response.body();
                mensajeVacio.setVisibility(pedidosAdapter.elementos.size() == 0 ? View.VISIBLE : View.GONE);
                Log.d("PedidosActivity", "Cargados " + pedidosAdapter.elementos.size() + " pedidos");
                pedidosAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e("pedidosActivity", "getPedidos() failed", t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class PedidosAdapter extends DominoListAdapter<Pedido>{
        @Override
        public DominoViewHolder<Pedido> createViewHolder(View view) {
            return new PedidosActivity.PedidoViewHolder(view);
        }
    }

    public static class PedidoViewHolder extends DominoViewHolder<Pedido>{
        TextView nombre;
        TextView direccion;
        TextView precio;


        public PedidoViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.nombre);
            this.direccion = itemView.findViewById(R.id.descripcion);
            this.precio = itemView.findViewById(R.id.precio);
        }

        public void populate(final Pedido pedido){
            nombre.setText(pedido.getNombre());
            direccion.setText(pedido.getDireccion());
            TextUtils.setTextAsCurrency(precio, pedido.getMonto());
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DetailActivity.PEDIDO, pedido);
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

