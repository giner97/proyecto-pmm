package com.example.giner.proyectopmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_consultar extends AppCompatActivity implements Fragmento_consultar.FragmentoConsultarListener{

    //Creamos las variables

        private Fragmento_consultar fragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        //Instancio el frgamento

            fragmento=(Fragmento_consultar)getFragmentManager().findFragmentById(R.id.fragment2);
            fragmento.setFragmentoConsultarListener(this);

    }
}
