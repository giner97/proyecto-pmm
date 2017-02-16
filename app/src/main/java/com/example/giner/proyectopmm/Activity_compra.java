package com.example.giner.proyectopmm;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

import es.dmoral.toasty.Toasty;

public class Activity_compra extends AppCompatActivity implements Registro_Dialog.OnInsertarCliente, AdapterView.OnItemClickListener, View.OnClickListener,TareaRest.TareaRestListener,TextWatcher,IMEI_Dialog.OnImeiDialog{

    //URL servidor
    private final static String URL_BASE_SERVIDOR = "http://alvaro-ricardo-pmm.hol.es/";

    private FloatingActionButton floatingActionButton;
    private Registro_Dialog registro_dialog;
    private IMEI_Dialog imei_dialogo;
    private FragmentTransaction transaction;
    private FragmentTransaction transactionImei;
    private ArrayAdapter<Cliente> arrayAdapterCliente;
    private ArrayList<Cliente>listaClientes;
    private ListView clientes;
    private Cliente clienteCreado;
    private EditText searchClientes;
    private int idMovilseleccionado;
    private Cliente clienteSeleccionado;
    private Compra compraAJSON;

    //Constantes con el codigo personalizado

        private final static int CONSULTA_CLIENTE = 0;
        private final static int INSERCION_CLIENTE = 1;
        private final static int INSERCION_COMPRA= 2;
        private final static int ACTUALIZA_CLIENTES= 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        //Recupero el id del movil seleccionado

            Intent recibeIDMovil=getIntent();
            idMovilseleccionado=recibeIDMovil.getIntExtra(Activity_main.KEY_MOVIL,-1);

        clientes = (ListView)findViewById(R.id.listViewClientesCompra);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.addUser);
        floatingActionButton.setOnClickListener(this);

        searchClientes=(EditText)findViewById(R.id.search);
        searchClientes.addTextChangedListener(this);

        clientes.setOnItemClickListener(this);

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

        clienteSeleccionado=(Cliente)adapterView.getItemAtPosition(i);
        transactionImei = getFragmentManager().beginTransaction();
        imei_dialogo = new IMEI_Dialog();
        imei_dialogo.setImeiDialogListener(this);
        imei_dialogo.show(transactionImei,null);
        imei_dialogo.setCancelable(false);

    }

    @Override
    public void onTareaRestFinalizada(int codigoOperacion, int codigoRespuestaHttp, String respuestaJson) {

        if(codigoOperacion==0){

            ArrayList<Cliente>clientesProcesar = procesarListaClientes(respuestaJson);
            this.listaClientes=clientesProcesar;

            if(arrayAdapterCliente==null){

                generaListView();

            }

        }

        else if(codigoOperacion==1){

            Toasty.success(this, "Usuario insertado correctamente", Toast.LENGTH_SHORT).show();
            actualizaArrayAdapter();

        }

        else if(codigoOperacion==2){

            Toasty.success(this, "Compra realizada con éxito.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();

        }

        else if(codigoOperacion==3){

            listaClientes=procesarListaClientes(respuestaJson);

            arrayAdapterCliente.clear();
            arrayAdapterCliente.addAll(listaClientes);

            arrayAdapterCliente.notifyDataSetChanged();

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

            Toasty.error(Activity_compra.this, e.getMessage(), Toast.LENGTH_LONG).show();

            return null;

        }

    }

    //Convierte un objeto JSON en un Movil
    private Moviles procesarMoviles (String objetoJSON){
        Gson gson = new Gson();
        try {

            Moviles movil = gson.fromJson(objetoJSON,Moviles.class);
            return movil;

        }

        catch (Exception e){

            Toasty.error(Activity_compra.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

                Toasty.error(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show();

            }

        }

    }


    @Override
    public void onNuevoCliente(Cliente cliente) {

        //Guardamos el cliente

            this.clienteCreado=cliente;

        //Creamos un objeto GSON

            Gson gson = new Gson();

        //Convertimos un objeto cliente en una cadena JSON

            String parametroJson = gson.toJson(cliente);

        TareaRest tarea = new TareaRest(this,INSERCION_CLIENTE,"POST",URL_BASE_SERVIDOR+"/cliente",parametroJson,this);
        tarea.execute();

    }

    public void actualizaArrayAdapter(){

        TareaRest tarea = new TareaRest(this,ACTUALIZA_CLIENTES,"GET",URL_BASE_SERVIDOR+"/cliente",null,this);
        tarea.execute();

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
    public void ejecutaTareaCompra(long imei) {

        //Instancio el objeto compra

            compraAJSON = new Compra();

        compraAJSON.setId_cliente(clienteSeleccionado.getId_cliente());
        compraAJSON.setImei(imei);
        compraAJSON.setId_movil(idMovilseleccionado);

        //Creamos un objeto GSON

            Gson gson = new Gson();

        //Convertimos un objeto compra en una cadena JSON

            String parametroJson = gson.toJson(compraAJSON);
            TareaRest tarea = new TareaRest(this,INSERCION_COMPRA,"POST",URL_BASE_SERVIDOR+"/compra",parametroJson,this);
            tarea.execute();

    }
}
