package com.claudio.ejemplofragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Claudio on 24/11/2014.
 */
public class FragmentDetalle extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    public void mostrarDetalle(String texto) {
        TextView txtDetalle =
                (TextView)getView().findViewById(R.id.txtDetalle);
        txtDetalle.setText(texto);
    }
}