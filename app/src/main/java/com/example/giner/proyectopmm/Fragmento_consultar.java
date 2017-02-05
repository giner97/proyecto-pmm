package com.example.giner.proyectopmm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class Fragmento_consultar extends Fragment implements  AdapterView.OnItemClickListener, TextWatcher{

    //Widgets del fragmento

        private EditText search;
        private ListView clientes;
        private ArrayAdapter<Cliente>arrayAdapterCliente;
        private FragmentoConsultarListener escuchador;

    //Constructor

        public Fragmento_consultar(){


        }

    //Metodo onCreate

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

    //Metodo onCreateView


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos el layout

            View v = inflater.inflate(R.layout.fragmento_consultar, container,false);

        //Instanciamos los widgets

            search = (EditText)v.findViewById(R.id.buscar);
            clientes = (ListView)v.findViewById(R.id.listViewClientes);

        ////Escuchador para el edittext

            search.addTextChangedListener(this);

        //ActionListener del listView

            clientes.setOnItemClickListener(this);

        //Creamos un array de Clientes

            ArrayList<Cliente>listaClientes = Cliente.generaListaAlumnos();

        //Instancio el arrayAdapter y le paso el array de Clientes

            arrayAdapterCliente = new ArrayAdapter<Cliente>(getActivity(),android.R.layout.simple_list_item_1,listaClientes);

        //Le paso el arrayAdapter al ListView

            clientes.setAdapter(arrayAdapterCliente);

        //Return

            return v;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        arrayAdapterCliente.getFilter().filter(charSequence);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //Metodos callbacks

        public interface FragmentoConsultarListener{



        }

        public void setFragmentoConsultarListener(FragmentoConsultarListener escuchador){

            this.escuchador=escuchador;

        }


}
