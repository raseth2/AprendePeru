package com.example.samuel.aprendeperu;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuel.aprendeperu.Fragment.ClasesFragment;
import com.example.samuel.aprendeperu.Fragment.DetalleClaseFragmento;
import com.example.samuel.aprendeperu.Fragment.ViewPerfilFragment;
import com.example.samuel.aprendeperu.Referencias.Clases;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    ScaleAnimation shrinkAnim;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private TextView tvNoMovies;
    private CardView cardView;
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public void StartActivity(){
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference refnombrePersona = ref.child("Persona").child(user.getUid());
        //tabla PErsona
        refnombrePersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() == null){
                        startActivity(new Intent(Main2Activity.this,PerfilUser.class));
                        Toast.makeText(Main2Activity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
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
    protected void onStart() {
        super.onStart();
        StartActivity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FirebaseUser user = mAuth.getCurrentUser();

        mRecyclerView = (RecyclerView)
                findViewById(R.id.my_recycler_view);
        tvNoMovies = (TextView) findViewById(R.id.tv_no_movies);
        cardView = (CardView)findViewById(R.id.card_view);
//scale animation to shrink floating actionbar
        /*shrinkAnim = new ScaleAnimation(1.15f, 0f, 1.15f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);*/
        if (mRecyclerView != null) {
//to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
//Say Hello to our new FirebaseUI android Element, i.e., FirebaseRecyclerAdapter
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        FirebaseRecyclerAdapter<Clases,MovieViewHolder> adapter = new
                FirebaseRecyclerAdapter<Clases, MovieViewHolder>(
                        Clases.class,
                        R.layout.card_view_main2,
                        MovieViewHolder.class,

                        mDatabaseReference.child("Clases").child("Artes").getRef()

                ) {
                    @Override

                    protected void populateViewHolder(MovieViewHolder viewHolder, Clases
                            model, int position) {
/*                        if(tvNoMovies.getVisibility()== View.VISIBLE){
                            tvNoMovies.setVisibility(View.GONE);
                        }*/

                        viewHolder.tViewAsignatura.setText(model.getAsignatura());
                        viewHolder.tViewCosto.setText("Precio : "+model.getCosto()+" S/.");
                        viewHolder.tIdUser.setText(model.getUserId());
                        viewHolder.tIdCurso.setText(model.getUserId());
                        


                        //viewHolder.ratingBar.setRating(model.get());
                       // Picasso.with(Main2Activity.this).load(model.get()).into(viewHolder.ivMoviePoster);
                    }
                };
        mRecyclerView.setAdapter(adapter);


        if(mAuth.getCurrentUser() == null)
        {
            //User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        //con esto generamos el usuario en el header del menu-------------------------------
        View hView = navigationView.getHeaderView(0);
        TextView correoTview = (TextView) hView.findViewById(R.id.tViewCorreo);
        TextView userTview = (TextView) hView.findViewById(R.id.tViewUser);

          if (user != null) {
            userTview.setText(user.getDisplayName());
              correoTview.setText(user.getEmail());
            }else{
              userTview.setText("Usuario");
              correoTview.setText("Correo");
          }

        navigationView.setNavigationItemSelectedListener(this);

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new ImportFragment()).commit();*/
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        TextView tViewAsignatura,tViewCosto,tIdUser,tIdCurso;
        RatingBar ratingBar;
        ImageView ivMoviePoster;
        public MovieViewHolder(View v) {
            super(v);
            tViewAsignatura = (TextView) v.findViewById(R.id.tViewAsignatura);
            tViewCosto = (TextView) v.findViewById(R.id.tViewCosto);
            ratingBar = (RatingBar) v.findViewById(R.id.rating_bar);
            ivMoviePoster = (ImageView) v.findViewById(R.id.iv_movie_poster);
            tIdUser=(TextView)v.findViewById(R.id.tViewIdUser);
            tIdCurso=(TextView)v.findViewById(R.id.tViewIdCurso);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity activity = (FragmentActivity)v.getContext();
                    FragmentManager manager = activity.getSupportFragmentManager();

                    String dato = tIdUser.getText().toString();
                    String nombreCurso = tViewAsignatura.getText().toString();
                    String Precio = tViewCosto.getText().toString();

                    Log.i("W4K","Click-" + dato);

                    FragmentTransaction transection =activity.getFragmentManager().beginTransaction();
                    DetalleClaseFragmento mfragment = new DetalleClaseFragmento();
                    //using Bundle to send data
                    Bundle bundle = new Bundle();
                    bundle.putString("id", dato);
                    bundle.putString("Curso",nombreCurso);
                    bundle.putString("Precio",Precio);
                    mfragment.setArguments(bundle); //data being send to SecondFragment
                    transection.replace(R.id.contenedor, mfragment);
                    transection.isAddToBackStackAllowed();
                    transection.addToBackStack(null);
                    transection.commit();

                }
            });

        }



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            // Handle the camera action
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ViewPerfilFragment()).commit();

        } else if (id == R.id.nav_clases) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ClasesFragment()).commit();


        } else if (id == R.id.nav_slideshow) {
            //fragmentManager.beginTransaction().replace(R.id.contenedor, new SlideshowFragment()).commit();
        } else if (id == R.id.nav_manage) {
           // fragmentManager.beginTransaction().replace(R.id.contenedor, new ToolsFragment()).commit();
        } else if (id == R.id.nav_salir) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
