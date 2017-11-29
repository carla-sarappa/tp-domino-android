package ar.edu.unq.uis.domino;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import ar.edu.unq.uis.domino.model.Pedido;
import ar.edu.unq.uis.domino.model.Plato;

/**
 * Created by Carla Sarappa on 27/11/2017.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String PEDIDO = "PEDIDO";
    private Pedido pedido;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.pedido = (Pedido) getIntent().getSerializableExtra(PEDIDO);
        setTitle(pedido.getNombre());
        RecyclerView recyclerView = findViewById(R.id.platos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlatoAdapter platoAdapter = new PlatoAdapter();
        platoAdapter.elementos = pedido.getPlatos();
        recyclerView.setAdapter(platoAdapter);
        TextView tipoDeEnvio = findViewById(R.id.nombre);
        TextView descripcion = findViewById(R.id.descripcion);
        TextView precio = findViewById(R.id.precio);
        tipoDeEnvio.setText(pedido.getFormaDeEnvio().getNombre());
        descripcion.setText(pedido.getFormaDeEnvio().getDireccion());
        TextUtils.setTextAsCurrency(precio, pedido.getFormaDeEnvio().getCosto());
    }

    public static class PlatoAdapter extends DominoListAdapter<Plato>{
        @Override
        public DominoViewHolder<Plato> createViewHolder(View view) {
            return new PlatoViewHolder(view);
        }
    }

    public static class PlatoViewHolder extends DominoViewHolder<Plato>{
        TextView nombre;
        TextView direccion;
        TextView precio;


        public PlatoViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.nombre);
            this.direccion = itemView.findViewById(R.id.descripcion);
            this.precio = itemView.findViewById(R.id.precio);
        }

        public void populate(final Plato plato){
            nombre.setText(plato.getPizza().getNombre());
            direccion.setText(plato.getExtrasAsString());
            TextUtils.setTextAsCurrency(precio, plato.getPrecio());
        }
    }
}
