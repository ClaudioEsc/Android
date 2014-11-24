package com.claudio.ejemplofragments;

import android.app.Activity;
import android.os.Bundle;

public class DetalleActivity extends Activity {
    public static final String EXTRA_TEXTO =
            "com.claudio.ejemplofragments.EXTRA_TEXTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        FragmentDetalle detalle =
                (FragmentDetalle)getFragmentManager()
                        .findFragmentById(R.id.frgDetalle);

        detalle.mostrarDetalle(getIntent().getStringExtra(EXTRA_TEXTO));
    }
}