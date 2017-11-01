package com.example.samuel.aprendeperu.Fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuel.aprendeperu.Main2Activity;
import com.example.samuel.aprendeperu.PerfilUser;
import com.example.samuel.aprendeperu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.value;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPerfilFragment extends Fragment {
    private ValueEventListener valueEventListener;
    private FirebaseAuth mAuth;
    TextView tNombres,tApellidos,tDireccion,tEmail,tFechaNacimiento,tTelefono,tEstudios;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public void CargarDatos(){
        final FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference refnombrePersona = ref.child("Persona").child(user.getUid());


        //tabla PErsona
        refnombrePersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {
                   // if (snapshot.getValue() != null){
                        //String value = snapshot.getValue(String.class);
                    tNombres.setText("NOMBRES : " + snapshot.child("NombrePersona").getValue().toString());
                    tApellidos.setText("APELLIDOS : "+snapshot.child("ApellidoPersona").getValue().toString());
                   // tNombres.setText(snapshot.child("Telefono").getValue().toString());
                    tTelefono.setText("TELEFONO : "+snapshot.child("Telefono").getValue().toString());
                    tEmail.setText("EMAIL : "+snapshot.child("Email").getValue().toString());
                    tFechaNacimiento.setText("FECHA DE NACIMIENTO : "+snapshot.child("FechaNacimientoPersona").getValue().toString());
                    tDireccion.setText("DIRECCION : "+snapshot.child("DireccionPersona").getValue().toString());

                  // }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            //fin tablapersona
        });
        DatabaseReference refCurriculum = ref.child("Curriculum").child(user.getUid());
        refCurriculum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tEstudios.setText("ESTIDIOS : "+dataSnapshot.child("Estudios").getValue().toString());
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onStart() {
        super.onStart();
        CargarDatos();
    }
    public ViewPerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();

        LinearLayout inflate = (LinearLayout)inflater.inflate(R.layout.fragment_view_perfil, container, false);
        tNombres=(TextView) inflate.findViewById(R.id.tViewNombre);
        tApellidos=(TextView) inflate.findViewById(R.id.tViewApellidos);
        tEstudios=(TextView) inflate.findViewById(R.id.tViewEstudios);
        tTelefono=(TextView) inflate.findViewById(R.id.tViewTelefono);
        tEmail=(TextView) inflate.findViewById(R.id.tViewEmailPer);
        tFechaNacimiento=(TextView) inflate.findViewById(R.id.tViewFechaNac);
        tDireccion=(TextView) inflate.findViewById(R.id.tViewDireccion);



        return inflate;


    }

}
