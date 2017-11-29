package com.example.samuel.aprendeperu.Fragment;




import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
private TextView textView,tCurso,tCosto,tNombre,tDescripcion;
Button btnTelefono;


    public void CargarDatos(){
        Bundle bundle = getArguments();
        final String nombreCurso=String.valueOf(bundle.getString("Curso"));
        final String Precio=String.valueOf(bundle.getString("Precio"));

        DatabaseReference refCurso = ref.child("Clases").child("Artes").child(String.valueOf(bundle.getString("idCurso")));

        refCurso.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    tDescripcion.setText(snapshot.child("descripcion").getValue().toString());

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            //fin tabla
        });

        DatabaseReference refPersona = ref.child("Persona").child(String.valueOf(bundle.getString("id")));
            //tabla PErsona
        refPersona.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        tNombre.setText("Docente : " + snapshot.child("NombrePersona").getValue().toString());
                        tCurso.setText(nombreCurso);
                        tCosto.setText(Precio);
                        btnTelefono.setText(snapshot.child("Telefono").getValue().toString());
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
        btnTelefono=(Button)inflate.findViewById(R.id.btnNumero);
        tDescripcion=(TextView)inflate.findViewById(R.id.tViewDescripcion);

        Bundle bundle = getArguments();
        //haciendo la prueba bundle
       // textView.setText(String.valueOf(bundle.getString("id")));
        textView.setText(String.valueOf(bundle.getString("idCurso")));
        CargarDatos();

        btnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:"+btnTelefono.getText());
                Intent callIntent = new Intent(Intent.ACTION_DIAL,number);
                startActivity(callIntent);
            }
        });
        return inflate;
    }

}
