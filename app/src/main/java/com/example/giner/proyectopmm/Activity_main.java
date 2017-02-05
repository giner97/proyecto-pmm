package com.example.giner.proyectopmm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_main extends AppCompatActivity implements FragmentoActivityMain.FragmentoMainListener{

    //Creamos las variables

        private FragmentoActivityMain fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos el fragmento

            fragment=(FragmentoActivityMain)getFragmentManager().findFragmentById(R.id.fragment);
            fragment.setFragmentoMainListener(this);

    }

    @Override
    public void onConsultar() {

        Intent intencion = new Intent(this,Activity_consultar.class);
        startActivity(intencion);

    }
}
