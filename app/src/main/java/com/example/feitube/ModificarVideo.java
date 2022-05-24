package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ModificarVideo extends AppCompatActivity {
    private String idVideo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText enlace;
    private EditText estudiante;
    private EditText proyecto;
    private Spinner programaEducativo;
    private EditText descripcion;
    private EditText director;
    private EditText codirector;
    private EditText sinodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarvideo);
        Bundle extras = getIntent().getExtras();
        this.enlace = findViewById(R.id.enlaceEditar);
        this.estudiante = findViewById(R.id.estudianteEditar);
        this.proyecto = findViewById(R.id.proyectoEditar);
        this.programaEducativo = findViewById(R.id.programaEducativoEditar);
        this.descripcion = findViewById(R.id.descripcionEditar);
        this.director = findViewById(R.id.directorEditar);
        this.codirector = findViewById(R.id.codirectorEditar);
        this.sinodal = findViewById(R.id.sinodalEditar);
        if(extras != null){
            this.idVideo = extras.getString("idVideo");
            this.obtenerVideoPorId(this.idVideo);
            Button button = (Button) findViewById(R.id.btnEditarVideo);
            button.setOnClickListener(view -> {
                this.editarVideo(this.idVideo);
            });
        }else{
            Toast.makeText(ModificarVideo.this,"No se encontro el videoa editar", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerVideoPorId(String id){
        this.db.collection("videos").document(id).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if( document.exists() ){
                            this.enlace.setText(document.getString("enlace"));
                            this.estudiante.setText(document.getString("estudiante"));
                            this.proyecto.setText(document.getString("proyecto"));
                            ArrayAdapter adapter = (ArrayAdapter) this.programaEducativo.getAdapter();
                            this.programaEducativo.setSelection(adapter.getPosition(document.getString("programaEducativo")));
                            this.descripcion.setText(document.getString("descripcion"));
                            this.director.setText(document.getString("director"));
                            this.codirector.setText(document.getString("codirector"));
                            this.sinodal.setText(document.getString("sinodal"));
                        }
                    }
                });
    }

    private void editarVideo(String id){
        DocumentReference docRef = this.db.collection("videos").document(id);
        HashMap<String,Object> video = new HashMap<>();

        video.put("enlace", enlace.getText().toString());
        video.put("estudiante", estudiante.getText().toString());
        video.put("proyecto", proyecto.getText().toString());
        video.put("programaEducativo", programaEducativo.getSelectedItem().toString());
        video.put("descripcion", descripcion.getText().toString());
        video.put("director", director.getText().toString());
        video.put("cordinador", codirector.getText().toString());
        video.put("sinodal", sinodal.getText().toString());
        docRef.update(video).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ModificarVideo.this,"Video editado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ModificarVideo.this,"No se pudo editado el video", Toast.LENGTH_SHORT).show();
            }
        });
    }
}