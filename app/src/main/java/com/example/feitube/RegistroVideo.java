package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistroVideo extends AppCompatActivity {

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
        setContentView(R.layout.activity_registrovideo);

        Button btnRegistrar = (Button) findViewById(R.id.btnRegistrarVideo);
        this.enlace = findViewById(R.id.enlace);
        this.estudiante = findViewById(R.id.estudiante);
        this.proyecto = findViewById(R.id.proyecto);
        this.programaEducativo = findViewById(R.id.programaEducativo);
        this.descripcion = findViewById(R.id.descripcion);
        this.director = findViewById(R.id.director);
        this.codirector = findViewById(R.id.codirector);
        this.sinodal = findViewById(R.id.sinodal);

        btnRegistrar.setOnClickListener(view -> {
            this.registrarVideo();
        });
    }

    private void registrarVideo(){
        if (!enlace.getText().toString().isEmpty() && !estudiante.getText().toString().isEmpty() && !proyecto.getText().toString().isEmpty() &&
                !descripcion.getText().toString().isEmpty() && !director.getText().toString().isEmpty() && !codirector.getText().toString().isEmpty() &&
                !sinodal.getText().toString().isEmpty() && programaEducativo.getSelectedItem() != null){
            HashMap<String,Object> nuevoVideo = new HashMap<>();

            nuevoVideo.put("enlace", enlace.getText().toString());
            nuevoVideo.put("estudiante", estudiante.getText().toString());
            nuevoVideo.put("proyecto", proyecto.getText().toString());
            nuevoVideo.put("programaEducativo", programaEducativo.getSelectedItem().toString());
            nuevoVideo.put("descripcion", descripcion.getText().toString());
            nuevoVideo.put("director", director.getText().toString());
            nuevoVideo.put("coordinador", codirector.getText().toString());
            CollectionReference table = this.db.collection("videos");
            String id = table.document().getId();
            table.document(id).set(nuevoVideo)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistroVideo.this,"Video registrado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistroVideo.this,"No se pudo registrar el video", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(RegistroVideo.this,"Hay campos vacios", Toast.LENGTH_SHORT).show();
        }
    }
}