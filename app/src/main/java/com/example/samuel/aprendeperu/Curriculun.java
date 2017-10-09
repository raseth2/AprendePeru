package com.example.samuel.aprendeperu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Samuel on 18/09/2017.
 */

public class Curriculun extends Activity {
    private FirebaseAuth mAuth;

        Button btn_next1;
        EditText tInstitucion,tProfesion,tLugarTrabajo;
        Spinner spStudios, spTrabaja;

        String Estudios, Trabajo;
        String[] datosEstudios ={"Estudios ...","Universitarios","Tecnico","Sin Estudios"};
        String[] datosTrabajo ={"Actualmente Trabajas?","Si","No"};


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private void regsitroCurriculum(){
        FirebaseUser user = mAuth.getCurrentUser();
        //tabla PErsona
        DatabaseReference refidUsuario = ref.child("Curriculum").child(user.getUid()).child("idUsuario");
        DatabaseReference refEstudiosDocente = ref.child("Curriculum").child(user.getUid()).child("Estudios");
        DatabaseReference refInstitucionDocente = ref.child("Curriculum").child(user.getUid()).child("Institucion");
        DatabaseReference refProfesionDocente = ref.child("Curriculum").child(user.getUid()).child("Profesion");
        DatabaseReference refTrabajandoDocente = ref.child("Curriculum").child(user.getUid()).child("Trabajando");
        DatabaseReference refLugarTrabajo = ref.child("Curriculum").child(user.getUid()).child("LugarTrabajo");
        
        //fin tablapersona

        String inserEstudios = Estudios.toString();
        String inserTrabajando = Trabajo.toString();


        refidUsuario.setValue(user.getUid());
        refEstudiosDocente.setValue(inserEstudios);
        refInstitucionDocente.setValue(tInstitucion.getText().toString());
        refProfesionDocente.setValue(tProfesion.getText().toString());
        refTrabajandoDocente.setValue(inserTrabajando);
        refLugarTrabajo.setValue(tLugarTrabajo.getText().toString());


    }

    private void SpinerEstudios(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datosEstudios);
        spStudios.setAdapter(arrayAdapter);

        spStudios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 1:
                        Estudios=datosEstudios[i];
                        //Toast.makeText(getApplicationContext(), tipoPersona,Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Estudios=datosEstudios[i];
                        //  Toast.makeText(getApplicationContext(), tipoPersona,Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SpinerTrabajo(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datosTrabajo);
        spTrabaja.setAdapter(arrayAdapter);

        spTrabaja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 1:
                        Trabajo=datosTrabajo[i];
                        //Toast.makeText(getApplicationContext(), tipoPersona,Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Trabajo=datosTrabajo[i];
                        //  Toast.makeText(getApplicationContext(), tipoPersona,Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.curriculun);


        mAuth = FirebaseAuth.getInstance();

        spStudios=(Spinner) findViewById(R.id.spEstudios);
        tInstitucion=(EditText)findViewById(R.id.txtInsttitucionEducativa);
        tProfesion=(EditText)findViewById(R.id.txtProfesion);
        spTrabaja=(Spinner) findViewById(R.id.spTrabajando);
        tLugarTrabajo=(EditText)findViewById(R.id.txtLugarTrabajo);
        btn_next1 = (Button)findViewById(R.id.btn_next1);

        SpinerEstudios();
        SpinerTrabajo();


        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regsitroCurriculum();

                //startActivity(new Intent(getApplicationContext(),Curriculun.class));
            }
        });


    }
}
