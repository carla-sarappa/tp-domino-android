package ar.edu.unq.uis.domino;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import ar.edu.unq.uis.domino.services.PedidoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public abstract class PedidosFragment extends Fragment {
    RecyclerView pedidosRecyclerView;
    PedidosAdapter pedidosAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View mensajeVacio;
    protected PedidoService service;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pedidos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpPedidoService();
        this.pedidosRecyclerView = view.findViewById(R.id.pedidos);
        this.pedidosAdapter = new PedidosAdapter();
        this.pedidosRecyclerView.setAdapter(pedidosAdapter);
        this.pedidosRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.mensajeVacio = view.findViewById(R.id.mensaje_vacio);
        populateAdapter();
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateAdapter();
            }
        });
    }

    public String getServerUrl(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return prefs.getString("server_url", "http://192.168.0.5:9000");
    }


    public void setUpPedidoService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(PedidoService.class);
    }

    public void populateAdapter(){

        getPedidos().enqueue(new Callback<List<Pedido>>() {
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

    protected abstract Call<List<Pedido>> getPedidos();

    public static class PedidosAdapter extends DominoListAdapter<Pedido>{
        @Override
        public DominoViewHolder<Pedido> createViewHolder(View view) {
            return new PedidoViewHolder(view);
        }
    }

    public static class PedidoViewHolder extends DominoViewHolder<Pedido>{

        public PedidoViewHolder(View itemView) {
            super(itemView);
        }

        public void populate(final Pedido pedido){
            nombre.setText(pedido.getNombre());
            descripcion.setText(pedido.getDireccion());
            TextUtils.setTextAsCurrency(precio, pedido.getMonto());
            this.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(DetailActivity.PEDIDO, pedido);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            });
        }
    }
}
