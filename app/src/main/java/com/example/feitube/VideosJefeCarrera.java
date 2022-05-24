package com.example.feitube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.feitube.elements.ElementoLista;
import com.example.feitube.elements.ListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VideosJefeCarrera extends AppCompatActivity {
    private List<ElementoLista> lista;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText campoBusqueda;
    private RecyclerView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosjefecarrera);
        this.campoBusqueda = findViewById(R.id.campoBusqueda);
        this.content = findViewById(R.id.listaVideosJefe);
        this.lista = new ArrayList<>();
        this.obtenerVideos();
        ImageView btnDesplegarRegistrarVideo = findViewById(R.id.btnDesplegarRegistrarVideo);
        btnDesplegarRegistrarVideo.setOnClickListener(view -> {
            this.desplegarRegistrarVideo();
        });

        ImageView btnDesplegarInicioSesion = findViewById(R.id.btnDesplegarInicioSesion);
        btnDesplegarInicioSesion.setOnClickListener( view -> {
            this.desplegarInicioSesion();
        });

        ImageView btnBuscar = findViewById(R.id.btnBuscarVideo);
        btnBuscar.setOnClickListener( view -> {
            this.buscarVideos();
        });
    }

    public  void init(){
        ListAdapter listAdapter = new ListAdapter(lista,this);
        content.setHasFixedSize(true);
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(listAdapter);
    }

    private void desplegarRegistrarVideo(){
        Intent registrar =  new Intent(this, RegistroVideo.class);
        startActivity(registrar);
    }

    private void desplegarInicioSesion(){
        Intent registrar =  new Intent(this, InicioSesion.class);
        startActivity(registrar);
    }

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
                this.content.getAdapter().notifyDataSetChanged();
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