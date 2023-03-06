package com.example.juegos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.PuntuacionViewHolder> {

    private List<PuntuacionItem> mDataset;

    public Adaptador(List<PuntuacionItem> lista) {
        mDataset = lista;
    }

    public static class PuntuacionViewHolder extends RecyclerView.ViewHolder{
        private   TextView idPlay;
        private   TextView puntuacion;

        public PuntuacionViewHolder(View itemView) {
            super(itemView);
            idPlay = itemView.findViewById(R.id.id);
            puntuacion = itemView.findViewById(R.id.punt);


        }
    }

    @Override
    public PuntuacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
        return new PuntuacionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PuntuacionViewHolder holder, int position) {
        PuntuacionItem puntuacionItem = mDataset.get(position);
        holder.idPlay.setText(puntuacionItem.getNombre());
        holder.puntuacion.setText(String.valueOf(puntuacionItem.getPuntuacion()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}
