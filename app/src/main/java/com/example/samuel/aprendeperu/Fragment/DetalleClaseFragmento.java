package com.example.samuel.aprendeperu.Fragment;




import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuel.aprendeperu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleClaseFragmento extends Fragment {

TextView textView;
    public DetalleClaseFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_detalle_clase_fragmento, container, false);
        textView = (TextView)inflate.findViewById(R.id.iduser);

        Bundle bundle = getArguments();
       // String respuesta =
       //String id = getActivity().getIntent().getStringExtra("id");*/
        //String re = "Inicio de sesion";
        textView.setText(String.valueOf(bundle.getString("id")));
        return inflate;
    }

}
