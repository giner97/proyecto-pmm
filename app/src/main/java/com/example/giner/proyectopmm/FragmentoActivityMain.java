package com.example.giner.proyectopmm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class FragmentoActivityMain extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    //Widgets del fragmento

        private FragmentoMainListener escuchador;
        private ImageButton boton_Consultar;
        private ListView listaMoviles;

    //Constructor

        public FragmentoActivityMain(){


        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos el layout

            View v = inflater.inflate(R.layout.fragmento_activity_main, container, false);

        //Instanciamos los objetos

            boton_Consultar = (ImageButton)v.findViewById(R.id.botonConsultar);
            listaMoviles = (ListView)v.findViewById(R.id.listViewMoviles);

        //ActionListener del botonConsultar

            boton_Consultar.setOnClickListener(this);

        //Creamos un array de moviles

            ArrayList<Moviles> arrayMoviles=Moviles.generaListaAlumnos();

        //Creamos un arrayAdapter y le pasamos el arrayList

            ArrayAdapterMoviles arrayAdapter = new ArrayAdapterMoviles(getActivity(),arrayMoviles);

        //Escuchador del listViewMoviles

            listaMoviles.setOnItemClickListener(this);

        //pasamos el arrayList al listView

            listaMoviles.setAdapter(arrayAdapter);

        return v;
    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.botonConsultar){

            escuchador.onConsultar();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public interface FragmentoMainListener{

        void onConsultar();

    }

    public void setFragmentoMainListener(FragmentoMainListener escuchador){

        this.escuchador=escuchador;

    }



}
