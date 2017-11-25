package com.example.samuel.aprendeperu.Fragment;




import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuel.aprendeperu.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleClaseFragmento extends Fragment {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
TextView textView,tCurso,tCosto,tNombre;


    public void CargarDatos(){
        Bundle bundle = getArguments();
        final String nombreCurso=String.valueOf(bundle.getString("Curso"));
        final String Precio=String.valueOf(bundle.getString("Precio"));

        DatabaseReference refPersona = ref.child("Persona").child(String.valueOf(bundle.getString("id")));



        //tabla PErsona
        refPersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {
                    // if (snapshot.getValue() != null){
                    //String value = snapshot.getValue(String.class);
                    tNombre.setText("Docente : " + snapshot.child("NombrePersona").getValue().toString());
                   // tApellidos.setText("APELLIDOS : "+snapshot.child("ApellidoPersona").getValue().toString());
                    //String newid = snapshot.child("Clases").child(nombreCurso).child("ID").getValue().toString();
                   tCurso.setText(nombreCurso);
                   tCosto.setText(Precio);
                   /* tEmail.setText("EMAIL : "+snapshot.child("Email").getValue().toString());
                    tFechaNacimiento.setText("FECHA DE NACIMIENTO : "+snapshot.child("FechaNacimientoPersona").getValue().toString());
                    tDireccion.setText("DIRECCION : "+snapshot.child("DireccionPersona").getValue().toString());
*/
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
        /*
        DatabaseReference refCurriculum = ref.child("Curriculum").child(user.getUid());
        refCurriculum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tEstudios.setText("ESTIDIOS : "+dataSnapshot.child("Estudios").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
    public DetalleClaseFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_detalle_clase_fragmento, container, false);

        textView = (TextView)inflate.findViewById(R.id.iduser);
        tCurso = (TextView)inflate.findViewById(R.id.tViewCurso);
        tCosto = (TextView)inflate.findViewById(R.id.tViewCosto);
        tNombre = (TextView)inflate.findViewById(R.id.tViewUsuario);


        Bundle bundle = getArguments();
        textView.setText(String.valueOf(bundle.getString("id")));
        CargarDatos();
        return inflate;
    }

}
