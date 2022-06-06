package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.feitube.model.Usuario;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistroEstudiante extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText nombre;
    private Spinner programaEducativo;
    private EditText matricula;
    private EditText contraseña;
    private EditText confirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroestudiante);

        this.nombre = findViewById(R.id.nombre);
        this.programaEducativo = findViewById(R.id.programaEducativo);
        this.matricula = findViewById(R.id.matriculaNueva);
        this.contraseña = findViewById(R.id.contraseñaNueva);
        this.confirmacion = findViewById(R.id.confirmacion);
        Button registrar = findViewById(R.id.btnRegistrarEstudiante);
        registrar.setOnClickListener(view -> {
            this.registrarEstudiante();
        });

        ImageView btnCambiarRegistroJefe = findViewById(R.id.btnCambiarRegistroJefe);
        btnCambiarRegistroJefe.setOnClickListener(v -> {
            this.desplegarRegistrarJefe();
            finish();
        });
    }

    private void registrarEstudiante(){
        if(!nombre.getText().toString().isEmpty() && !matricula.getText().toString().isEmpty() &&
                !contraseña.getText().toString().isEmpty() && !confirmacion.getText().toString().isEmpty() && programaEducativo.getSelectedItem() != null){
            if(contraseña.getText().toString().equals(this.confirmacion.getText().toString())){
                HashMap<String,Object> estudianteNuevo = new HashMap<>();
                estudianteNuevo.put("nombre",this.nombre.getText().toString());
                estudianteNuevo.put("programaEducativo",this.programaEducativo.getSelectedItem().toString());
                estudianteNuevo.put("matricula",this.matricula.getText().toString());
                estudianteNuevo.put("contraseña", Usuario.md5(this.contraseña.getText().toString()));
                estudianteNuevo.put("tipo","estudiante");

                this.db.collection("usuarios").document(this.matricula.getText().toString()).set(estudianteNuevo)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(RegistroEstudiante.this,"Estudiante registrado", Toast.LENGTH_SHORT).show();
                                this.desplegarInicioSesion();
                                finish();
                            }else{
                                Toast.makeText(RegistroEstudiante.this,"No se pudo registrar el estudiante", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(RegistroEstudiante.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegistroEstudiante.this,"Hay campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    private void desplegarInicioSesion(){
        Intent registrar =  new Intent(this, InicioSesion.class);
        startActivity(registrar);
    }

    private void desplegarRegistrarJefe(){
        Intent registrar =  new Intent(this, RegistroJefeCarrera.class);
        startActivity(registrar);
    }
}