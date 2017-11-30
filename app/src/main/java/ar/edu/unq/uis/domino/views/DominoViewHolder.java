package ar.edu.unq.uis.domino.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ar.edu.unq.uis.domino.R;

/**
 * Created by Carla Sarappa on 28/11/2017.
 */

public abstract class DominoViewHolder<T> extends RecyclerView.ViewHolder{

    // Como no tengo binding automatico, tengo que buscar a mano cada view
    // y despues las subclases se encargan de rellenar los textos
    // implementando populate

    protected TextView nombre;
    protected TextView descripcion;
    protected TextView precio;

    public DominoViewHolder(View itemView) {
        super(itemView);
        this.nombre = itemView.findViewById(R.id.nombre);
        this.descripcion = itemView.findViewById(R.id.descripcion);
        this.precio = itemView.findViewById(R.id.precio);
    }

    public abstract void populate(T t);
}