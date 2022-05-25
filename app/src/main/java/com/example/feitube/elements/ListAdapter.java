package com.example.feitube.elements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feitube.ModificarVideo;
import com.example.feitube.R;
import com.example.feitube.VisualizarVideo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ElementoLista> lista;
    private LayoutInflater mInflater;
    private Context context;
    private boolean isEstudiante;

    public ListAdapter(List<ElementoLista> lista, Context context, boolean isEstudiante) {
        this.mInflater = LayoutInflater.from(context);
        this.lista = lista;
        this.context = context;
        this.isEstudiante = isEstudiante;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.elemento_lista, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(lista.get(position));
        holder.setList(lista,position);
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setItems(List<ElementoLista> lista){
        this.lista = lista;
    }

    public void actualizarDatos(){
        this.notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button btnEditar, btnEliminar, btnVisualizar;
        TextView nombre, proyecto, director, codirector, sinodal;
        String id;
        Context context;
        List<ElementoLista> lista;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            nombre = itemView.findViewById(R.id.nombreView);
            proyecto = itemView.findViewById(R.id.proyectoView);
            director = itemView.findViewById(R.id.directorView);
            codirector = itemView.findViewById(R.id.codirectorView);
            sinodal = itemView.findViewById(R.id.sinodalView);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnVisualizar = itemView.findViewById(R.id.btnVisualizar);
            if(isEstudiante){
                btnEditar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
            }
        }

        void bindData (final ElementoLista item){
            nombre.setText(item.getNombre());
            proyecto.setText(item.getProyecto());
            codirector.setText(item.getCordinador());
            director.setText(item.getDirector());
            sinodal.setText(item.getSinodal());
            this.id = item.getId();
        }

        void setOnClickListeners(){
            btnEditar.setOnClickListener(this);;
            btnEliminar.setOnClickListener(this);;
            btnVisualizar.setOnClickListener(this);;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnEditar:
                    Intent editarVideo = new Intent(context, ModificarVideo.class);
                    editarVideo.putExtra("idVideo",id);
                    context.startActivity(editarVideo);
                    break;
                case R.id.btnEliminar:
                    new AlertDialog.Builder(context)
                            .setTitle("Confimación")
                            .setMessage("¿Quiere borrar el registro?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("videos").document(this.id).delete().addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){
                                        Toast.makeText(this.context,"Video eliminado", Toast.LENGTH_SHORT).show();
                                        this.deleteItem();
                                        actualizarDatos();
                                    }
                                });
                            }).setNegativeButton(android.R.string.no, (dialog, which) -> {

                            }).show();
                    break;
                case R.id.btnVisualizar:
                    Intent visualizarVideo = new Intent(context, VisualizarVideo.class);
                    visualizarVideo.putExtra("idVideo",id);
                    context.startActivity(visualizarVideo);
                    break;
            }
        }

        public void setList(List<ElementoLista> lista, int position) {
            this.lista = lista;
            this.position = position;
        }

        private void deleteItem(){
            this.lista.remove(position);
        }
    }
}
