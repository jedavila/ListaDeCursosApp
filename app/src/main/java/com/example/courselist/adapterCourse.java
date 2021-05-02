package com.example.courselist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapterCourse extends RecyclerView.Adapter<adapterCourse.ViewHolder>{
    private Modelo mapaCursos = Modelo.getInstance();
    private Context context;

    private RecyclerView recyclerView;
    private adapterSection adapterSection;

    public adapterCourse(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public adapterCourse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterCourse.ViewHolder holder, int position) {
        List<Course> courseList = new ArrayList<>(mapaCursos.getCursosMap().keySet());
        Course curse = courseList.get(position);
        holder.txtNRC.setText(curse.getCourse());
        holder.txtTituloCurso.setText(curse.getTitle());
        holder.txtDescripcion.setText(curse.getRestrictions());
        holder.txtCredits.setText("Cr: "+curse.getCredits());
        holder.txtLevel.setText("level: "+curse.getLevel());

    }

    @Override
    public int getItemCount() {
        return mapaCursos.getCursosMap().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNRC;
        TextView txtTituloCurso;
        TextView txtDescripcion;
        TextView txtCredits;
        TextView txtLevel;
        List<Course> courseList = new ArrayList<>(mapaCursos.getCursosMap().keySet());


        public ViewHolder(@NonNull View view) {
            super(view);
            txtNRC = (TextView) view.findViewById(R.id.txtNRC);
            txtTituloCurso = (TextView) view.findViewById(R.id.txtTituloCurso);
            txtDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);
            txtCredits = (TextView) view.findViewById(R.id.txtCredits);
            txtLevel = (TextView) view.findViewById(R.id.txtLevel);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            List<section> secciones = new ArrayList<>(mapaCursos.getCursosMap().get(courseList.get(position)));

            adapterSection = new adapterSection(secciones,context);
            recyclerView.setAdapter(adapterSection);


        }
    }

}


