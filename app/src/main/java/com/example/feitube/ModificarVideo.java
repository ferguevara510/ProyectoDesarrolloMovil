package com.example.feitube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

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
    private EditText fecha;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarvideo);
        Bundle extras = getIntent().getExtras();
        this.enlace = findViewById(R.id.enlaceEditar);
        this.fecha = findViewById(R.id.fecha);
        this.estudiante = findViewById(R.id.estudianteEditar);
        this.proyecto = findViewById(R.id.proyectoEditar);
        this.programaEducativo = findViewById(R.id.programaEducativoEditar);
        this.descripcion = findViewById(R.id.descripcionEditar);
        this.director = findViewById(R.id.directorEditar);
        this.codirector = findViewById(R.id.codirectorEditar);
        this.sinodal = findViewById(R.id.sinodalEditar);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };

        fecha.setOnClickListener(view -> new DatePickerDialog(this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());

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

    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        fecha.setText(dateFormat.format(myCalendar.getTime()));
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
                            this.fecha.setText(document.getString("fecha"));
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
        video.put("codirector", codirector.getText().toString());
        video.put("sinodal", sinodal.getText().toString());
        video.put("fecha", fecha.getText().toString());
        docRef.update(video).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ModificarVideo.this,"Video editado", Toast.LENGTH_SHORT).show();
                this.desplegarVideosJefeCarrera();
                finish();
            }else{
                Toast.makeText(ModificarVideo.this,"No se pudo editado el video", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void desplegarVideosJefeCarrera(){
        Intent videos =  new Intent(this, VideosJefeCarrera.class);
        startActivity(videos);
    }
}