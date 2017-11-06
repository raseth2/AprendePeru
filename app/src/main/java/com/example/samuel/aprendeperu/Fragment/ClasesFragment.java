package com.example.samuel.aprendeperu.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuel.aprendeperu.Main2Activity;
import com.example.samuel.aprendeperu.PerfilUser;
import com.example.samuel.aprendeperu.R;
import com.example.samuel.aprendeperu.Referencias.Clases;
import com.example.samuel.aprendeperu.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.samuel.aprendeperu.R.layout.user;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClasesFragment extends Fragment {
   private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    Spinner categoria_spinner;
    EditText tAsignatura,tLocal,tMaxAlumnos,tCosto;
    TextView fecha_text;
    Button btn_save, btn_Cargar;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    public void Guardar() {
        FirebaseUser user = mAuth.getCurrentUser();

        String categoria = categoria_spinner.getSelectedItem().toString();
        //tabla PErsona
        DatabaseReference refCategoria = ref.child("Clases").child(categoria).child(user.getUid()).child(categoria);
        DatabaseReference refAsignatura = ref.child("Clases").child(categoria).child(user.getUid()).child("Asignatura");
        DatabaseReference refLocal = ref.child("Clases").child(categoria).child(user.getUid()).child("Local");
        DatabaseReference refMaxAlumnos = ref.child("Clases").child(categoria).child(user.getUid()).child("MaxAlumnos");
        DatabaseReference refCosto = ref.child("Clases").child(categoria).child(user.getUid()).child("Costo");

        refCategoria.setValue(categoria);
        refAsignatura.setValue(tAsignatura.getText().toString());
        refLocal.setValue(tLocal.getText().toString());
        refMaxAlumnos.setValue(tMaxAlumnos.getText().toString());
        refCosto.setValue(tCosto.getText().toString());
        Toast.makeText(getActivity(), "Datos Guardados", Toast.LENGTH_SHORT).show();
    }


    private void newClase(String userId, String Asignatura, String
            Local,String MaxAlumnos,String Costos) {
        FirebaseUser user = mAuth.getCurrentUser();
        String categoria = categoria_spinner.getSelectedItem().toString();
//Creating a movie object with user defined variables
        Clases clases = new Clases(userId,Asignatura,Local,MaxAlumnos,Costos);
//referring to movies node and setting the values from movie object to location
        mDatabaseReference.child("Clases").child(categoria).push().setValue(clases);
        Toast.makeText(getActivity(), "Datos Cargados", Toast.LENGTH_SHORT).show();
    }


public void CargarDatos(){
    //final String categoria = "Artes";
    String categoria = categoria_spinner.getSelectedItem().toString();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference refCategoria = ref.child("Clases").child(categoria).child(user.getUid());
    //tabla PErsona
    refCategoria.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {

            try {
               // categoria_spinner.setSelection(categoria);
                tAsignatura.setText(snapshot.child("Asignatura").getValue().toString());
                tLocal.setText(snapshot.child("Local").getValue().toString());
                tMaxAlumnos.setText(snapshot.child("MaxAlumnos").getValue().toString());
                tCosto.setText(snapshot.child("Costo").getValue().toString());
                Toast.makeText(getActivity(), "Datos Cargados", Toast.LENGTH_SHORT).show();

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
}
    public ClasesFragment() {
        // Required empty public constructor
    }
    public void onStart() {
        super.onStart();
        //CargarDatos();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        View inflate = inflater.inflate(R.layout.fragment_clases, container, false);

        categoria_spinner = (Spinner) inflate.findViewById(R.id.spCategoria);
        tAsignatura=(EditText) inflate.findViewById(R.id.txtAsignatura);
        tLocal=(EditText) inflate.findViewById(R.id.txtLocal);
        tMaxAlumnos=(EditText) inflate.findViewById(R.id.txtMaxAlumnos);
        tCosto=(EditText) inflate.findViewById(R.id.txtCosto);
btn_save=(Button)inflate.findViewById(R.id.btnGuardar);
        btn_Cargar=(Button)inflate.findViewById(R.id.btnCargar);
        btn_Cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarDatos();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();

               // Guardar();
                newClase(user.getUid(),tAsignatura.getText().toString().trim(),
                        tLocal.getText().toString(),
                        tMaxAlumnos.getText().toString(),
                        tCosto.getText().toString());

            }
        });

        fecha_text = (TextView) inflate.findViewById(R.id.fecha_ejemplo_text);

        fecha_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );
        // Inflate the layout for this fragment
        return inflate;
    }

}
