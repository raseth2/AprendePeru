package com.example.samuel.aprendeperu.Fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuel.aprendeperu.Curriculun;
import com.example.samuel.aprendeperu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private FirebaseAuth mAuth;
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
    private void Spiner(){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,datos);
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
                        Toast.makeText(getActivity().getApplicationContext(), "Debe elegir uno tipo persona",Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_perfil, container, false);

        mAuth = FirebaseAuth.getInstance();

        LinearLayout inflate = (LinearLayout)inflater.inflate(R.layout.fragment_perfil, container, false);

        tNombres=(EditText)inflate.findViewById(R.id.txtInsttitucionEducativa);
        tApellidos=(EditText)inflate.findViewById(R.id.txtProfesion);
        tTelefono=(EditText)inflate.findViewById(R.id.txtLugarTrabajo);
        tEmail=(EditText)inflate.findViewById(R.id.txtEmailPer);
        tFechaNacimiento=(EditText)inflate.findViewById(R.id.txtFechaNac);
        tDireccion=(EditText)inflate.findViewById(R.id.txtDireccion);


        textView=(TextView)inflate.findViewById(R.id.txtViewCurriculum);
        btn_next = (Button)inflate.findViewById(R.id.btn_next);
        spTipoPersona =(Spinner)inflate.findViewById(R.id.spEstudios);
        //spinner lista
        Spiner();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regsitro();
                startActivity(new Intent(getActivity().getApplicationContext(),Curriculun.class));
            }
        });
        return inflate;
    }
    @Override
    public void onStart() {
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
