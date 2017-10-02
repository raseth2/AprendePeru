package com.example.samuel.aprendeperu;

import android.content.Intent;
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


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password,name,apellido;
    private Button btnInicioS,btnRegister;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); // importamos la llamada

       //
        btnRegister = (Button)findViewById(R.id.btn_registrar);
        btnInicioS = (Button)findViewById(R.id.btn_iniciar);
        email = (EditText)findViewById(R.id.txtCorreo);
        password = (EditText)findViewById(R.id.txtContraseña);
        name = (EditText)findViewById(R.id.txtNombre);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){

                    startActivity(new Intent(MainActivity.this,User.class));

                }
                else {
                    // Toast.makeText(MainActivity.this, "Datos Incorrectos", Toast.LENGTH_SHORT).show();

                }
            }
        };
        //Check if User is Already LoggedIn
        /*if(mAuth.getCurrentUser() != null)
        {
            //User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),User.class));
        }
*/
        btnInicioS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getemail = email.getText().toString().trim();
                String getepassword = password.getText().toString().trim();
                IniciarSesion(getemail,getepassword);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewUser.class));

            }
        });




    }
    //Create Account
    /*private void CrearUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Signed up Failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userProfile();
                            Toast.makeText(MainActivity.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                        }
                    }
                });
    }*/
    //Set UserDisplay Name
   /* private void userProfile()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim())
//.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // here you can set image link also.
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
    }*/


    //Now start Sign In Process
//SignIn Process
    private void IniciarSesion(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent i = new Intent(MainActivity.this, User.class);
                            finish();
                            startActivity(i);
                        }
                    }
                });

    }
}
