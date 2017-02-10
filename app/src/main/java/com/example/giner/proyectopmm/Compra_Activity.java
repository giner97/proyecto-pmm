package com.example.giner.proyectopmm;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Compra_Activity extends AppCompatActivity implements  Registro_Dialog.OnInsertarCliente, AdapterView.OnItemClickListener, View.OnClickListener,TareaRest.TareaRestListener,TextWatcher{

    //URL servidor
    private final static String URL_BASE_SERVIDOR = "http://alvaro-ricardo-pmm.hol.es/";

    private FloatingActionButton fab;
    private Registro_Dialog registro_dialog;
    private FragmentTransaction transaction;
    private ArrayAdapter<Cliente> arrayAdapterCliente;
    private ArrayList<Cliente>listaClientes;
    private ListView clientes;
    private Cliente clienteCreado;
    private EditText searchClientes;

    //Constantes con el codigo personalizado

        private final static int CONSULTA_CLIENTE = 0;
        private final static int INSERCION_CLIENTE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        clientes = (ListView)findViewById(R.id.listViewClientesCompra);
        fab= (FloatingActionButton)findViewById(R.id.addUser);
        fab.setOnClickListener(this);

        searchClientes=(EditText)findViewById(R.id.search);
        searchClientes.addTextChangedListener(this);

        //Se usa la clase ConnectivityManager para obtener las características actuales de la conexión.
        ConnectivityManager gestorConexion = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        //Esta información la guardamos en un elemento del tipo NetworkInfo con el método getActiveNetworkInfo().
        NetworkInfo infoRed = gestorConexion.getActiveNetworkInfo();

        //Comprobaremos que existe conexión de red
        if (infoRed != null && infoRed.isConnected()) {


        }

        else {

            // Mostrar errores
            Toast.makeText(this,"No hay conexión de red.",Toast.LENGTH_SHORT).show();

        }

        TareaRest tarea = new TareaRest(this,CONSULTA_CLIENTE,"GET",URL_BASE_SERVIDOR+"/cliente",null,this);
        tarea.execute();

    }

    @Override
    public void onClick(View view) {


        if(view.getId()==R.id.addUser){

            //Creamos e instanciamos el Custom Dialog
            transaction = getFragmentManager().beginTransaction();
            registro_dialog = new Registro_Dialog();
            registro_dialog.setOnInsertarClienteListener(this);
            registro_dialog.show(transaction,null);
            registro_dialog.setCancelable(false);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {





    }

    @Override
    public void onTareaRestFinalizada(int codigoOperacion, int codigoRespuestaHttp, String respuestaJson) {

        if(codigoOperacion==0){

            ArrayList<Cliente>clientesProcesar = procesarListaClientes(respuestaJson);
            listaClientes=clientesProcesar;
            generaListView();

        }

        else if(codigoOperacion==1){

            actualizaArrayAdapter(clienteCreado);

        }

    }

    //Convierte un objeto JSON en un arrayList de Moviles
    private ArrayList<Cliente> procesarListaClientes (String objetoJSON){

        Gson gson = new Gson();

        try {

            Type tipoLista = new TypeToken<ArrayList<Cliente>>(){}.getType();
            ArrayList<Cliente> clientes = gson.fromJson(objetoJSON,tipoLista);
            return clientes;

        }

        catch (Exception e){

            //Publico un Toast en la activity que nos llamó
            Toast.makeText(Compra_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            return null;

        }

    }

    public void generaListView(){

        if (clientes != null) {

            if((arrayAdapterCliente==null)&&(listaClientes!=null)) {

                //Instancio el arrayAdapter y le paso el array de Clientes

                arrayAdapterCliente = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, listaClientes);

                //Le paso el arrayAdapter al ListView

                clientes.setAdapter(arrayAdapterCliente);

            }

            else{

                Toast.makeText(this,"Ha ocurrido un error",Toast.LENGTH_LONG).show();

            }

        }

    }


    @Override
    public void onNuevoCliente(Cliente cliente) {

        //Guardamos el cliente
        this.clienteCreado=cliente;
        //Cremaos un objeto GSON
        Gson gson = new Gson();
        //Convertimos un objeto Alumno en una cadena JSON
        String parametroJson = gson.toJson(cliente);
        TareaRest tarea = new TareaRest(this,INSERCION_CLIENTE,"POST",URL_BASE_SERVIDOR+"/cliente",parametroJson,this);
        tarea.execute();

    }

    public void actualizaArrayAdapter(Cliente cliente){

        listaClientes.add(cliente);
        arrayAdapterCliente.notifyDataSetChanged();

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
}
