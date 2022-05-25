package com.example.feitube;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VisualizarVideo extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView estudiante;
    private TextView proyecto;
    private TextView programaEducativo;
    private TextView descripcion;
    private TextView director;
    private TextView codirector;
    private TextView sinodal;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_video);
        youTubePlayerView= findViewById(R.id.vistaVideo);
        this.estudiante = findViewById(R.id.seccionEstudiante);
        this.proyecto = findViewById(R.id.seccionProyecto);
        this.descripcion = findViewById(R.id.seccionDescripcion);
        this.director = findViewById(R.id.seccionDirector);
        this.codirector = findViewById(R.id.seccionCodirector);
        this.sinodal = findViewById(R.id.seccionSinodal);
        Bundle extras = getIntent().getExtras();
        String idVideo;
        if(extras != null) {
            idVideo = extras.getString("idVideo");
            this.obtenerVideoPorId(idVideo);
        }

    }

    private void obtenerVideoPorId(String id){
        this.db.collection("videos").document(id).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if( document.exists() ){
                            this.link = document.getString("enlace");
                            this.estudiante.setText(document.getString("estudiante"));
                            this.proyecto.setText(document.getString("proyecto"));
//                            this.programaEducativo.setText(document.getString("programaEducativo"));
                            this.descripcion.setText(document.getString("descripcion"));
                            this.director.setText(document.getString("director"));
                            this.codirector.setText(document.getString("codirector"));
                            this.sinodal.setText(document.getString("sinodal"));
                        }
                        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                youTubePlayer.loadVideo(link);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                            }
                        };

                        youTubePlayerView.initialize("AIzaSyDE6fW39-Tw8o-HiahHjTIpyP2dEwOT-II",onInitializedListener);
                    }
                });
    }
}