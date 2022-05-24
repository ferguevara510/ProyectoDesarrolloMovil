package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.feitube.elements.ElementoLista;
import com.example.feitube.elements.ListAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class VideosJefeCarrera extends AppCompatActivity {
    private List<ElementoLista> lista;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosjefecarrera);
        this.lista = new ArrayList<>();
        this.obtenerVideos();
    }

    public  void init(){
        ListAdapter listAdapter = new ListAdapter(lista,this);
        RecyclerView content = findViewById(R.id.listaVideosJefe);
        content.setHasFixedSize(true);
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(listAdapter);
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
                            snapshot.getString("cordinador"),
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