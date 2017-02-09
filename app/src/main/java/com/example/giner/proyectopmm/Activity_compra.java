package com.example.giner.proyectopmm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_compra extends AppCompatActivity implements  AdapterView.OnItemClickListener, View.OnClickListener,TareaRest.TareaRestListener{

    //URL servidor

    private final static String URL_BASE_SERVIDOR = "http://alvaro-ricardo-pmm.hol.es/";

    private FloatingActionButton fab;
    private ArrayAdapter<Cliente> arrayAdapterCliente;
    private ArrayList<Cliente> listaClientes;
    private ListView clientes;
    private Cliente clienteSeleccionado;

    //Constantes con el codigo personalizado

    private final static int CONSULTA_CLIENTE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        clientes = (ListView)findViewById(R.id.listViewClientesCompra1);

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
            Toast.makeText(Activity_compra.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

                Toast.makeText(this,"Error aqui",Toast.LENGTH_LONG).show();

            }

        }

    }


}