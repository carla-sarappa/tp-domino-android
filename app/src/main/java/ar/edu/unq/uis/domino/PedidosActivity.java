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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
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
                pedidosAdapter.pedidos = response.body();
                mensajeVacio.setVisibility(pedidosAdapter.pedidos.size() == 0 ? View.VISIBLE : View.GONE);
                Log.d("PedidosActivity", "Cargados " + pedidosAdapter.pedidos.size() + " pedidos");
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

    public static class PedidosAdapter extends RecyclerView.Adapter<PedidoViewHolder>{
        List<Pedido> pedidos = new ArrayList<>();

        public PedidosAdapter() {
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
            this.pedidos.add(new Pedido());
        }

        @Override
        public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);

            return new PedidoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PedidoViewHolder holder, int position) {
            holder.populate(pedidos.get(position));
        }

        @Override
        public int getItemCount() {
            return pedidos.size();
        }
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView direccion;
        TextView precio;


        public PedidoViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.nombre);
            this.direccion = itemView.findViewById(R.id.direccion);
            this.precio = itemView.findViewById(R.id.precio);
        }

        public void populate(Pedido pedido){
            nombre.setText(pedido.getNombre());
            direccion.setText(pedido.getDireccion());
            precio.setText(NumberFormat.getCurrencyInstance().format(pedido.getMonto()));
        }
    }
}

