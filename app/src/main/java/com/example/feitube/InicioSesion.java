package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class InicioSesion extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText contraseña;
    private EditText matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
        this.contraseña = findViewById(R.id.contraseña);
        this.matricula = findViewById(R.id.matricula);
        ImageView registrar = findViewById(R.id.btnRegistrar);
        Button inicarSesion = findViewById(R.id.btnIniciarSesion);

        inicarSesion.setOnClickListener(view -> {
            this.iniciarSesion();
        });

        registrar.setOnClickListener(view -> {
            this.desplegarRegistrar();
        });

    }

    private void iniciarSesion(){
        db.collection("usuarios").document(this.matricula.getText().toString()).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if( document.exists() ){
                            if(Objects.equals(document.getString("contraseña"), this.contraseña.getText().toString())){
                                Toast.makeText(InicioSesion.this,"Sesion iniciada, !Hola "+ document.getString("nombre"), Toast.LENGTH_SHORT).show();
                                if(Objects.equals(document.getString("tipo"), "jefe")){
                                    this.desplegarVideosJefeCarrera();
                                }else{
                                    this.desplegarVideosJefeCarrera();
                                }
                            }else{
                                Toast.makeText(InicioSesion.this,"Matricula o contraseña incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void desplegarVideosJefeCarrera(){
        Intent videos =  new Intent(this, VideosJefeCarrera.class);
        startActivity(videos);
    }

    private void desplegarVideosEstudiantes(){
        Intent videos =  new Intent(this, VideosEstudiante.class);
        startActivity(videos);
    }

    private void desplegarRegistrar(){
        Intent registrar =  new Intent(this, RegistroEstudiante.class);
        startActivity(registrar);
    }
}