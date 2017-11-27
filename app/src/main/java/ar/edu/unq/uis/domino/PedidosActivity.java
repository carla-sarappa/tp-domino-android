package ar.edu.unq.uis.domino;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
        populateAdapter();
    }

    public void populateAdapter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.5:9000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PedidoService service = retrofit.create(PedidoService.class);
        service.getPedidos(4).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                pedidosAdapter.pedidos = response.body();
                pedidosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e("pedidosActivity", "getPedidos() failed", t);
            }
        });
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

