package ar.edu.unq.uis.domino;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Carla Sarappa on 28/11/2017.
 */

public abstract class DominoViewHolder<T> extends RecyclerView.ViewHolder{

    public DominoViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void populate(T t);
}