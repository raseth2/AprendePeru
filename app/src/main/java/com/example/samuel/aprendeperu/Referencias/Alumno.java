package com.example.samuel.aprendeperu.Referencias;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Samuel on 24/09/2017.
 */

public class Alumno {
    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference mensajeref = ref.child("usuarios").child("dchullunquia").child("nombre");
}
