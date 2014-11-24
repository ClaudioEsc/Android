package com.claudio.ejemplofragments;

import com.claudio.ejemplofragments.FragmentListado.CorreosListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements CorreosListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentListado frgListado
                =(FragmentListado)getFragmentManager()
                .findFragmentById(R.id.frgListado);
        frgListado.setCorreosListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCorreoSeleccionado(Correo c) {
        boolean hayDetalle =
                (getFragmentManager().findFragmentById(R.id.frgDetalle) != null);
        if(hayDetalle) {
            ((FragmentDetalle)getFragmentManager()
                    .findFragmentById(R.id.frgDetalle)).mostrarDetalle(c.getTexto());
        }
        else {
            Intent i = new Intent(this, DetalleActivity.class);
            i.putExtra(DetalleActivity.EXTRA_TEXTO, c.getTexto());
            startActivity(i);
        }
    }
}
