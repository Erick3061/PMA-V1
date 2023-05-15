package com.pemsa.pemsamonitoreoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterFormato extends RecyclerView.Adapter<AdapterFormato.ViewHolderFormato> {

    ArrayList<formato> listaFormato;

    public AdapterFormato(ArrayList<formato> listaFormato) {
        this.listaFormato = listaFormato;
    }

    @NonNull
    @Override
    public ViewHolderFormato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_formato,null,false);
        return new ViewHolderFormato(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFormato holder, int position) {
        holder.FH.setText(listaFormato.get(position).getFecha());
        holder.DE.setText(listaFormato.get(position).getDEvento());
        holder.NCA.setText(listaFormato.get(position).getNCA().replace("_"," ").trim());
        switch (listaFormato.get(position).getTipoImagen()){
            case 1:
                holder.imageView.setImageResource(R.mipmap.candado);
            break;
            case 2:
                holder.imageView.setImageResource(R.mipmap.candadoce);
            break;
            case 3:
                holder.imageView.setImageResource(R.mipmap.alerta);
            break;
        }
    }

    @Override
    public int getItemCount() {
        return listaFormato.size();
    }

    public class ViewHolderFormato extends RecyclerView.ViewHolder {
        TextView FH,DE,NCA;
        ImageView imageView;
        public ViewHolderFormato(@NonNull View itemView) {
            super(itemView);
            FH=(TextView)itemView.findViewById(R.id.idFecha);
            DE=(TextView)itemView.findViewById(R.id.idDesEvento);
            NCA=(TextView)itemView.findViewById(R.id.idNombreCuentaAbierta);
            imageView = (ImageView )itemView.findViewById(R.id.tipo);
        }
    }
}
