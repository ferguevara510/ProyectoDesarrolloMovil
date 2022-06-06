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

public class RegistroJefeCarrera extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText nombre;
    private Spinner programaEducativo;
    private EditText matricula;
    private EditText contraseña;
    private EditText confirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrojefecarrera);

        this.nombre = findViewById(R.id.nombre);
        this.programaEducativo = findViewById(R.id.programaEducativo);
        this.matricula = findViewById(R.id.matriculaNueva);
        this.contraseña = findViewById(R.id.contraseñaNueva);
        this.confirmacion = findViewById(R.id.confirmacion);
        Button registrar = findViewById(R.id.btnRegistrarJefeCarrera);
        registrar.setOnClickListener(view -> {
            this.registrarEstudiante();
            finish();
        });

        ImageView btnCambiarRegistroEstudiante = findViewById(R.id.btnCambiarRegistroEstudiante);
        btnCambiarRegistroEstudiante.setOnClickListener(v -> {
            this.desplegarRegistrarEstudiante();
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
                estudianteNuevo.put("tipo","jefe");

                this.db.collection("usuarios").document(this.matricula.getText().toString()).set(estudianteNuevo)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(RegistroJefeCarrera.this,"Jefe de carrera registrado", Toast.LENGTH_SHORT).show();
                                this.desplegarInicioSesion();
                                finish();
                            }else{
                                Toast.makeText(RegistroJefeCarrera.this,"No se pudo registrar el jefe de carrera", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(RegistroJefeCarrera.this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegistroJefeCarrera.this,"Hay campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    private void desplegarInicioSesion(){
        Intent registrar =  new Intent(this, InicioSesion.class);
        startActivity(registrar);
    }

    private void desplegarRegistrarEstudiante(){
        Intent registrar =  new Intent(this, RegistroEstudiante.class);
        startActivity(registrar);
    }
}