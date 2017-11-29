package ar.edu.unq.uis.domino;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Carla Sarappa on 28/11/2017.
 */

public abstract class DominoViewHolder<T> extends RecyclerView.ViewHolder{

    TextView nombre;
    TextView descripcion;
    TextView precio;

    public DominoViewHolder(View itemView) {
        super(itemView);
        this.nombre = itemView.findViewById(R.id.nombre);
        this.descripcion = itemView.findViewById(R.id.descripcion);
        this.precio = itemView.findViewById(R.id.precio);
    }
    public abstract void populate(T t);
}