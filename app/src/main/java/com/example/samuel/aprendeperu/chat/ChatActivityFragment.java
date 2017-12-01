package com.example.samuel.aprendeperu.chat;


import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuel.aprendeperu.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatActivityFragment extends Fragment implements View.OnClickListener, MessageDataSource.MessagesCallbacks  {
    public static final String USER_EXTRA = "USER";
    public FirebaseAuth mAuth;
    public static final String TAG = "ChatActivity";
    private DatabaseReference mDatabaseReference;
    private EditText newMessageView;
    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private ListView mListView;
    private TextView tviewIdMensaje;
    private Date mLastMessageDate = new Date();
    private String mConvoId, IdMensaje;
    private MessageDataSource.MessagesListener mListener;

    public ChatActivityFragment() {
        // Required empty public constructor
    }

private void CrearIdMensaje(){
    String n = tviewIdMensaje.getText().toString();
    Toast.makeText(getContext(),n+" Funciona",Toast.LENGTH_SHORT).show();


    Firebase.setAndroidContext(getContext());
    mConvoId = n;
    //mConvoId="CEsar";
    mListener = MessageDataSource.addMessagesListener(mConvoId, this);

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate =inflater.inflate(R.layout.fragment_chat_activity, container, false);
        mRecipient = "Ajay";

        newMessageView = (EditText)inflate.findViewById(R.id.new_message);
        tviewIdMensaje=(TextView)inflate.findViewById(R.id.TextViewIdMensaje);
        mListView = (ListView)inflate.findViewById(R.id.messages_list);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getArguments();

        //setTitle(mRecipient);
/*
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
*/
        ImageView sendMessage = (ImageView)inflate.findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);
        FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference refnombrePersona = ref.child("Persona").child(user.getUid()).child("Messajes");
        final DatabaseReference refPersona2 = ref.child("Persona").child(String.valueOf(bundle.getString("id"))).child("Messajes");

        refnombrePersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {

                    if (snapshot.getValue() == null){
                        //CrearIdMensaje();
                    //String value = snapshot.getValue(String.class);
                   /* tNombres.setText("NOMBRES : " + snapshot.child("NombrePersona").getValue().toString());
                    tApellidos.setText("APELLIDOS : "+snapshot.child("ApellidoPersona").getValue().toString());
                    // tNombres.setText(snapshot.child("Telefono").getValue().toString());
                    tTelefono.setText("TELEFONO : "+snapshot.child("Telefono").getValue().toString());
                    tEmail.setText("EMAIL : "+snapshot.child("Email").getValue().toString());
                    tFechaNacimiento.setText("FECHA DE NACIMIENTO : "+snapshot.child("FechaNacimientoPersona").getValue().toString());
                    tDireccion.setText("DIRECCION : "+snapshot.child("DireccionPersona").getValue().toString());
*/

                        FirebaseUser user = mAuth.getCurrentUser();
                        Bundle bundle = getArguments();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference refnombrePersona = ref.child("Persona").child(user.getUid()).child("Messajes");
                        final DatabaseReference refPersona2 = ref.child("Persona").child(bundle.getString("id")).child("Messajes");
                        Toast.makeText(getContext(), "Usuario 2 "+bundle.getString("id"), Toast.LENGTH_SHORT).show();
                        String push = mDatabaseReference.push().getKey();

                        refnombrePersona.child("IdChat").setValue(push);
                        refPersona2.child("IdChat").setValue(push);
                        Toast.makeText(getContext(), "No hay chat", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getContext(), "este s el push"+push, Toast.LENGTH_SHORT).show();
                        //tviewIdMensaje.setText(String.valueOf(push).toString());
                        CrearIdMensaje();
                        Toast.makeText(getContext(), "PRobandi id Psuh "+ push+" este es", Toast.LENGTH_SHORT).show();

                     }else{

                        //tviewIdMensaje.setText(String.valueOf(snapshot.child("IdChat").getValue()).toString());
                        String n = tviewIdMensaje.getText().toString();
                        Toast.makeText(getContext(), "else  "+n, Toast.LENGTH_SHORT).show();
                        CrearIdMensaje();
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

        /*String UserFirts=String.valueOf(user.getUid()).toString();
        String UserSecond=String.valueOf(bundle.getString("id")).toString();

        String[] ids = {UserFirts,"-", UserSecond};
        Arrays.sort(ids);*/


        // Inflate the layout for this fragment
        return inflate;
    }

    @Override
    public void onClick(View v) {
        FirebaseUser user = mAuth.getCurrentUser();
        String UserFirts=String.valueOf(user.getUid()).toString();

        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(UserFirts);

        MessageDataSource.saveMessage(msg, mConvoId);

    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }
    private class MessagesAdapter extends ArrayAdapter<Message> {
        MessagesAdapter(ArrayList<Message> messages){
            super(getActivity().getApplicationContext(), R.layout.message, R.id.message, messages);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FirebaseUser user = mAuth.getCurrentUser();
            String UserFirts=user.getUid().toString();

            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.message);
            nameView.setText(message.getText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getSender().equals(UserFirts)){
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getContext().getDrawable(R.drawable.bubble_right_green));
                } else{
                    nameView.setBackgroundDrawable(getContext().getDrawable(R.drawable.bubble_right_green));
                }
                layoutParams.gravity = Gravity.RIGHT;
            }else{
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getContext().getDrawable(R.drawable.bubble_left_gray));
                } else{
                    nameView.setBackgroundDrawable(getContext().getDrawable(R.drawable.bubble_left_gray));
                }
                layoutParams.gravity = Gravity.LEFT;
            }

            nameView.setLayoutParams(layoutParams);


            return convertView;
        }
    }
}
