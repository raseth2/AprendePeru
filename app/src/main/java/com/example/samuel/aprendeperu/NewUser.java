package com.example.samuel.aprendeperu;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Samuel on 18/09/2017.
 */

public class NewUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password,name,apellido;
    private Button btnInicioS,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        mAuth = FirebaseAuth.getInstance(); // importamos la llamada

        btnRegister = (Button)findViewById(R.id.btn_registrar);
        email = (EditText)findViewById(R.id.txtCorreo);
        apellido=(EditText)findViewById(R.id.txtApellido);
        password = (EditText)findViewById(R.id.txtContraseña);
        name = (EditText)findViewById(R.id.txtNombre);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getemail = email.getText().toString().trim();
                String getepassword = password.getText().toString().trim();

                callsignup(getemail,getepassword);

            }
        });


        }
    private void userProfile()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name.getText().toString().trim())
                        .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // here you can set image link also.
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");

                            }
                        }
                    });
        }
    }
        //Create Account


    private void callsignup(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(NewUser.this, "Signed up Failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userProfile();
                            Toast.makeText(NewUser.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                        }
                    }
                });
    }




    }
