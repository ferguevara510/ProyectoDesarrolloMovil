package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.feitube.elements.ElementoLista;
import com.example.feitube.elements.ListAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VideosEstudiante extends AppCompatActivity {
    private List<ElementoLista> lista;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText campoBusqueda;
    private RecyclerView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosestudiante);
        this.campoBusqueda = findViewById(R.id.campoBusquedaEstudiante);
        this.content = findViewById(R.id.listaVideosEstudiantes);
        this.lista = new ArrayList<>();
        this.obtenerVideos();
        ImageView btnDesplegarInicioSesion = findViewById(R.id.btnSalirSesion);
        btnDesplegarInicioSesion.setOnClickListener( view -> {
            this.desplegarInicioSesion();
        });

        ImageView btnBuscar = findViewById(R.id.btnBuscarVideosEstudiantes);
        btnBuscar.setOnClickListener( view -> {
            this.buscarVideos();
        });
    }

    public  void init(){
        ListAdapter listAdapter = new ListAdapter(lista,this,true);
        content.setHasFixedSize(true);
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(listAdapter);
    }

    private void desplegarInicioSesion(){
        Intent registrar =  new Intent(this, InicioSesion.class);
        startActivity(registrar);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void buscarVideos(){
        String busqueda = this.campoBusqueda.getText().toString();

        db.collection("videos").whereEqualTo("proyecto",busqueda).get().addOnCompleteListener(task -> {
            lista.clear();
            if (task.isSuccessful()) {
                ElementoLista elemento;
                for (DocumentSnapshot snapshot : task.getResult()){
                    elemento = new ElementoLista(
                            snapshot.getString("estudiante"),
                            snapshot.getString("proyecto"),
                            snapshot.getString("director"),
                            snapshot.getString("codirector"),
                            snapshot.getString("sinodal"),
                            snapshot.getId()
                    );
                    this.lista.add(elemento);
                }
                Objects.requireNonNull(this.content.getAdapter()).notifyDataSetChanged();
            }
        });
    }

    private void obtenerVideos(){
        db.collection("videos").get().addOnCompleteListener(task -> {
            lista.clear();
            if (task.isSuccessful()) {
                ElementoLista elemento;
                for (DocumentSnapshot snapshot : task.getResult()){
                    elemento = new ElementoLista(
                            snapshot.getString("estudiante"),
                            snapshot.getString("proyecto"),
                            snapshot.getString("director"),
                            snapshot.getString("codirector"),
                            snapshot.getString("sinodal"),
                            snapshot.getId()
                    );
                    this.lista.add(elemento);
                }
            }
            this.init();
        });
    }
}