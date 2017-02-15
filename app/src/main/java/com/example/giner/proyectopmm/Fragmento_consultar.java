package com.example.giner.proyectopmm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import es.dmoral.toasty.Toasty;


public class Fragmento_consultar extends Fragment implements  AdapterView.OnItemClickListener, TextWatcher, AdapterView.OnItemLongClickListener, PopupMenu.OnMenuItemClickListener{

    //Widgets del fragmento

        private EditText search;
        private ListView clientes;
        private FloatingActionButton fab;
        private ArrayAdapter<Cliente>arrayAdapterCliente;
        private FragmentoConsultarListener escuchador;
        private ArrayList<Cliente>listaClientes;
        private Cliente clienteSeleccionado;

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

        fab = (FloatingActionButton) v.findViewById(R.id.addUser);

        search = (EditText)v.findViewById(R.id.buscar);
        clientes = (ListView)v.findViewById(R.id.listViewClientes);

        ////Escuchador para el edittext

            search.addTextChangedListener(this);

        //ActionListener del listView

            clientes.setOnItemClickListener(this);
            clientes.setOnItemLongClickListener(this);

        //Return

            return v;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Recupero el cliente seleccionado

        clienteSeleccionado = (Cliente)adapterView.getItemAtPosition(i);

        escuchador.onMuestraCLiente(clienteSeleccionado);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Recupero el cliente seleccionado

        clienteSeleccionado = (Cliente)adapterView.getItemAtPosition(i);

        //Popup  prueba Falta poner el escuchador

        PopupMenu popup = new PopupMenu(getActivity(), clientes);

        popup.getMenuInflater().inflate(R.menu.popup_menu_clientes, popup.getMenu());

        popup.setOnMenuItemClickListener(this);

        popup.show();

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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.modificar:

                escuchador.onModificaCliente(clienteSeleccionado);
                return true;

            case R.id.eliminar:

                escuchador.onEliminaCliente(clienteSeleccionado);
                return true;

            default:

                return false;

        }

    }

    //Metodos callbacks

        public interface FragmentoConsultarListener{

            void onMuestraCLiente(Cliente cliente);
            void onEliminaCliente(Cliente cliente);
            void onModificaCliente(Cliente cliente);

        }

        public void setFragmentoConsultarListener(FragmentoConsultarListener escuchador){

            this.escuchador=escuchador;

        }

        public void pasaArrayList(ArrayList<Cliente>arrayclientes) {

            this.listaClientes = arrayclientes;

            if (clientes != null) {

                if((arrayAdapterCliente==null)&&(this.listaClientes!=null)) {

                    //Instancio el arrayAdapter y le paso el array de Clientes

                        arrayAdapterCliente = new ArrayAdapter<Cliente>(getActivity(), android.R.layout.simple_list_item_1, listaClientes);

                    //Le paso el arrayAdapter al ListView

                        clientes.setAdapter(arrayAdapterCliente);

                }

               if(this.listaClientes==null){

                    search.setEnabled(false);

               }

            }

        }

        public void actualizaLista(Cliente cliente){

            listaClientes.remove(cliente);
            arrayAdapterCliente.notifyDataSetChanged();
            Toasty.success(getActivity(), "Usuario eliminado correctamente.", Toast.LENGTH_SHORT).show();

        }

        public void actualizaListaModificacion(){

            arrayAdapterCliente.notifyDataSetChanged();
            Toasty.success(getActivity(), "Usuario modificado correctamente.", Toast.LENGTH_SHORT).show();

        }

}
