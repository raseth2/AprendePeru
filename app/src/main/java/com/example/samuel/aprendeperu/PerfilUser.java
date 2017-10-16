package com.example.samuel.aprendeperu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Samuel on 18/09/2017.
 */

public class PerfilUser extends AppCompatActivity {
    private FirebaseAuth mAuth;

    //declaraciones de herramientas
    Button btn_next ;
    EditText txtopcion,tNombres,tApellidos,tDireccion,tEmail,tFechaNacimiento,tTelefono;
    Spinner spTipoPersona;
    TextView textView;
    // declaracion de variables
    String tipoPersona;
    String[] datos ={"Elige una opcion","Alumno","Profesor"};
    //referencia base de datos firebase1
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
private void regsitro(){
    FirebaseUser user = mAuth.getCurrentUser();
    //tabla PErsona
        DatabaseReference refidUsuario = ref.child("Persona").child(user.getUid()).child("idUsuario");
        DatabaseReference refnombrePersona = ref.child("Persona").child(user.getUid()).child("NombrePersona");
        DatabaseReference refapellidoPersona = ref.child("Persona").child(user.getUid()).child("ApellidoPersona");
        DatabaseReference reftelefonoPersona = ref.child("Persona").child(user.getUid()).child("Telefono");
        DatabaseReference refemailPersona = ref.child("Persona").child(user.getUid()).child("Email");
        DatabaseReference reffechaNacimientoPersona = ref.child("Persona").child(user.getUid()).child("FechaNacimientoPersona");
        DatabaseReference refadireccionPersona = ref.child("Persona").child(user.getUid()).child("DireccionPersona");
        DatabaseReference reftipoPerson = ref.child("Persona").child(user.getUid()).child("TipoPersona");
    //fin tablapersona

    String tipoPerson = tipoPersona.toString();

    refidUsuario.setValue(user.getUid());
    refnombrePersona.setValue(tNombres.getText().toString());
    refapellidoPersona.setValue(tApellidos.getText().toString());
    reftelefonoPersona.setValue(tTelefono.getText().toString());
    refemailPersona.setValue(user.getEmail());
    reffechaNacimientoPersona.setValue(tFechaNacimiento.getText().toString());
    refadireccionPersona.setValue(tDireccion.getText().toString());
    //reftipoPerson.push().setValue(tipoPerson);
    reftipoPerson.setValue(tipoPerson);
    //dejamos en blanco el edit text
   // txtopcion.setText("");

}
    //referencia base de datos firebase1
    private void Spiner(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datos);
        spTipoPersona.setAdapter(arrayAdapter);

        spTipoPersona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 1:
                        tipoPersona=datos[i];
                        //Toast.makeText(getApplicationContext(), tipoPersona,Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        tipoPersona=datos[i];
                        //  Toast.makeText(getApplicationContext(), tipoPersona,Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Debe elegir uno tipo persona",Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.perfil_user);

        mAuth = FirebaseAuth.getInstance();

        tNombres=(EditText)findViewById(R.id.txtInsttitucionEducativa);
        tApellidos=(EditText)findViewById(R.id.txtProfesion);
        tTelefono=(EditText)findViewById(R.id.txtLugarTrabajo);
        tEmail=(EditText)findViewById(R.id.txtEmailPer);
        tFechaNacimiento=(EditText)findViewById(R.id.txtFechaNac);
        tDireccion=(EditText)findViewById(R.id.txtDireccion);


        textView=(TextView)findViewById(R.id.txtViewCurriculum);
        btn_next = (Button)findViewById(R.id.btn_next);
        spTipoPersona =(Spinner)findViewById(R.id.spEstudios);
        //spinner lista
        Spiner();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regsitro();
                startActivity(new Intent(getApplicationContext(),Curriculun.class));
            }
        });

    }
    //leemos la base de datos y lo mostramos en una textview
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser users = mAuth.getCurrentUser();
        //ver un dato o actualizar la pantalla
        DatabaseReference refnombrePersona = ref.child("Persona").child(users.getUid()).child("NombrePersona");
        if (users != null) {
            tNombres.setText(users.getDisplayName());
            tEmail.setText(users.getEmail());
        }
        //users.getDisplayName()
        refnombrePersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                tNombres.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
