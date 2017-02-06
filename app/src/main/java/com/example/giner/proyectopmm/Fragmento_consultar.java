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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class Fragmento_consultar extends Fragment implements  AdapterView.OnItemClickListener, TextWatcher, AdapterView.OnItemLongClickListener{

    //Widgets del fragmento

        private EditText search;
        private ListView clientes;
        private ArrayAdapter<Cliente>arrayAdapterCliente;
        private FragmentoConsultarListener escuchador;
        private ArrayList<Cliente>listaClientes;

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
            clientes.setOnItemLongClickListener(this);

        //Instancio el arrayAdapter y le paso el array de Clientes

            arrayAdapterCliente = new ArrayAdapter<Cliente>(getActivity(),android.R.layout.simple_list_item_1,listaClientes);

        //Le paso el arrayAdapter al ListView

            clientes.setAdapter(arrayAdapterCliente);

        //Return

            return v;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Creo un cliente y recupero el cliente seleccionado

            Cliente cliente = (Cliente)adapterView.getItemAtPosition(i);

        escuchador.onMuestraCLiente(cliente);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        /*Popup  prueba Falta poner el escuchador

        PopupMenu popup = new PopupMenu(getActivity(), clientes);

        popup.getMenuInflater().inflate(R.menu.popup_menu_clientes, popup.getMenu());

        popup.show();*/

        return true;
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

            void onMuestraCLiente(Cliente cliente);

        }

        public void setFragmentoConsultarListener(FragmentoConsultarListener escuchador){

            this.escuchador=escuchador;

        }

        public void pasaArrayList(ArrayList<Cliente>clientes){

            this.listaClientes=clientes;

        }
}
