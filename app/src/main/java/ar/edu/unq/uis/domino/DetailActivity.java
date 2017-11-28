package ar.edu.unq.uis.domino;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ar.edu.unq.uis.domino.model.Pedido;

/**
 * Created by Carla Sarappa on 27/11/2017.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String PEDIDO = "PEDIDO";
    private Pedido pedido;
    private TextView direccion;
    private TextView precio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.precio = findViewById(R.id.precio);
        this.direccion = findViewById(R.id.direccion);

        this.pedido = (Pedido) getIntent().getSerializableExtra(PEDIDO);
        precio.setText(pedido.getMonto().toString());
        direccion.setText(pedido.getDireccion());
        setTitle(pedido.getNombre());
    }
}
