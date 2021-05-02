package com.example.courselist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapterSection extends RecyclerView.Adapter<adapterSection.ViewHolder>{
    private List<section> seccionesList;
    private Context context;


    public adapterSection(List<section> seccionesList, Context context) {
        this.seccionesList = seccionesList;
        this.context = context;
    }

    @NonNull
    @Override
    public adapterSection.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_element, parent, false);
        return new adapterSection.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSection.ViewHolder holder, int position) {
        section secciones = seccionesList.get(position);
        holder.txtNRC.setText(secciones.getBdl());
        holder.txtSection.setText(secciones.getSection());
        holder.txtHorario.setText("W: " + secciones.getDays()+" "+secciones.getStart());
        holder.txtInstructor.setText(secciones.getInstructor());

    }

    @Override
    public int getItemCount() {
        return seccionesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNRC;
        TextView txtSection;
        TextView txtHorario;
        TextView txtInstructor;



        public ViewHolder(@NonNull View view) {
            super(view);
            txtNRC = (TextView) view.findViewById(R.id.txtNRC);
            txtSection = (TextView) view.findViewById(R.id.txtSection);
            txtHorario = (TextView) view.findViewById(R.id.txtHorario);
            txtInstructor = (TextView) view.findViewById(R.id.txtInstructor);

        }
    }
}
