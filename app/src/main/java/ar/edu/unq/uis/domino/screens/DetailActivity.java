package ar.edu.unq.uis.domino.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ar.edu.unq.uis.domino.R;
import ar.edu.unq.uis.domino.model.Pedido;
import ar.edu.unq.uis.domino.model.Plato;
import ar.edu.unq.uis.domino.utils.TextUtils;
import ar.edu.unq.uis.domino.views.DominoListAdapter;
import ar.edu.unq.uis.domino.views.DominoViewHolder;

/**
 * Created by Carla Sarappa on 27/11/2017.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String PEDIDO = "PEDIDO";
    private Pedido pedido;
    private TextView tipoDeEnvio;
    private TextView descripcion;
    private TextView aclaraciones;
    private TextView precio;
    private TextView total;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.pedido = (Pedido) getIntent().getSerializableExtra(PEDIDO);
        setTitle(pedido.getNombre());
        fetchViews();
        populateViews();
    }

    private void fetchViews() {
        recyclerView = findViewById(R.id.platos);
        tipoDeEnvio = findViewById(R.id.nombre);
        descripcion = findViewById(R.id.descripcion);
        aclaraciones = findViewById(R.id.aclaraciones);
        precio = findViewById(R.id.precio);
        total = findViewById(R.id.total);
    }

    private void populateViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlatoAdapter platoAdapter = new PlatoAdapter();
        platoAdapter.elementos = pedido.getPlatos();
        recyclerView.setAdapter(platoAdapter);

        tipoDeEnvio.setText(pedido.getFormaDeEnvio().getNombre().equals("delivery") ?
                getString(R.string.delivery) : getString(R.string.retira));
        descripcion.setText(pedido.getFormaDeEnvio().getDireccion());
        aclaraciones.setText(pedido.getAclaraciones());
        if (pedido.getAclaraciones() == null || pedido.getAclaraciones().isEmpty()){
            findViewById(R.id.aclaracionesContainer).setVisibility(View.GONE);
        }
        TextUtils.setTextAsCurrency(precio, pedido.getFormaDeEnvio().getCosto());
        TextUtils.setTextAsCurrency(total, pedido.getMonto());
    }

    public static class PlatoAdapter extends DominoListAdapter<Plato> {
        @Override
        public DominoViewHolder<Plato> createViewHolder(View view) {
            return new PlatoViewHolder(view);
        }
    }

    public static class PlatoViewHolder extends DominoViewHolder<Plato>{
        TextView tamanio;

        public PlatoViewHolder(View itemView) {
            super(itemView);
            this.tamanio = itemView.findViewById(R.id.tamanio);
        }

        // Tengo que setear a mano los valores porque no tengo binding
        public void populate(final Plato plato){
            nombre.setText(plato.getPizza().getNombre());
            descripcion.setText(TextUtils.joinList(
                    plato.getExtras(),
                    (extra)-> extra.nombre,
                    ", ",
                    " y "));
            TextUtils.setTextAsCurrency(precio, plato.getMonto());
            tamanio.setVisibility(View.VISIBLE);
            tamanio.setText(plato.getTamanio());
        }
    }
}
