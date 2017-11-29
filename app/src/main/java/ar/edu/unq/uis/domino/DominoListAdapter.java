package ar.edu.unq.uis.domino;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carla Sarappa on 28/11/2017.
 */

public abstract class DominoListAdapter<T> extends RecyclerView.Adapter<DominoViewHolder<T>>{
    List<T> elementos = new ArrayList<>();

    @Override
    public DominoViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listado, parent, false);

        return createViewHolder(view);
    }

    public abstract DominoViewHolder<T> createViewHolder(View view);

    @Override
    public void onBindViewHolder(DominoViewHolder<T> holder, int position) {
        holder.populate(elementos.get(position));
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }
}

