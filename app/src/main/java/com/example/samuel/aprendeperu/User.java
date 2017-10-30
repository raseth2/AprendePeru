package com.example.samuel.aprendeperu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Samuel on 18/09/2017.
 */

public class User extends AppCompatActivity {

    Button btnCerrarSession,btn_perfil, upload_bttn,showData,notification;
    private FirebaseAuth mAuth;
    TextView username;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        mAuth = FirebaseAuth.getInstance(); // important Call
        btnCerrarSession = (Button)findViewById(R.id.btn_cerrarSesion);
        btn_perfil = (Button)findViewById(R.id.btn_perfil);
        username = (TextView) findViewById(R.id.tvBienvenido);

        //Again check if the user is Already Logged in or Not
        if(mAuth.getCurrentUser() == null)
        {
            //User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        //Fetch the Display name of current User
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            username.setText("Bienvenido, " + user.getDisplayName());
        }
        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btn_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "Abrir nuevo perfil", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),PerfilUser.class));
            }
        });


    }
}
