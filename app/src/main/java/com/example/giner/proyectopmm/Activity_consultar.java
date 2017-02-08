package com.example.giner.proyectopmm;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_consultar extends AppCompatActivity implements Fragmento_consultar.FragmentoConsultarListener,TareaRest.TareaRestListener{

    private Cliente_Dialog clienteDialogo;
    private FragmentTransaction transaction;

    //URL servidor

        private final static String URL_BASE_SERVIDOR = "http://alvaro-ricardo-pmm.hol.es/";

    //Constantes con el codigo personalizado

        private final static int CONSULTA_CLIENTES = 0;
        private final static int ELIMINA_CLIENTE = 1;

    //Creamos las variables

        private Fragmento_consultar fragmento;
        private Cliente clienteSeleccionadoEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        //Instancio el frgamento

            fragmento=(Fragmento_consultar)getFragmentManager().findFragmentById(R.id.fragment2);
            fragmento.setFragmentoConsultarListener(this);

        //Se usa la clase ConnectivityManager para obtener las características actuales de la conexión.
            ConnectivityManager gestorConexion = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        //Esta información la guardamos en un elemento del tipo NetworkInfo con el método getActiveNetworkInfo().
            NetworkInfo infoRed = gestorConexion.getActiveNetworkInfo();

        //Comprobaremos que existe conexión de red
        if (infoRed != null && infoRed.isConnected()) {


        }

        else {

            // Mostrar errores
            Toast.makeText(this, "No hay conexión de red.", Toast.LENGTH_SHORT).show();

        }

        TareaRest tarea = new TareaRest(this,CONSULTA_CLIENTES,"GET",URL_BASE_SERVIDOR+"/cliente",null,this);
        tarea.execute();

    }

    @Override
    public void onMuestraCLiente(Cliente cliente) {

        //Creamos e instanciamos el Custom Dialog
        transaction = getFragmentManager().beginTransaction();
        clienteDialogo = new Cliente_Dialog(cliente);
        clienteDialogo.show(transaction,null);
        clienteDialogo.setCancelable(false);

    }

    @Override
    public void onEliminaCliente(Cliente cliente) {

        clienteSeleccionadoEliminar=cliente;
        String clienteID = Integer.toString(cliente.getId_cliente());
        TareaRest tareaElimina = new TareaRest(this,ELIMINA_CLIENTE,"DELETE",URL_BASE_SERVIDOR+"/cliente/"+clienteID,null,this);
        tareaElimina.execute();

    }

    @Override
    public void onTareaRestFinalizada(int codigoOperacion, int codigoRespuestaHttp, String respuestaJson) {

        if (codigoRespuestaHttp == 200 || respuestaJson != null && !respuestaJson.isEmpty()) {

            if (codigoOperacion == 0) {

                ArrayList<Cliente> clientes = procesarListaClientes(respuestaJson);
                fragmento.pasaArrayList(clientes);

            }

            else if(codigoOperacion==1){

                fragmento.actualizaLista(clienteSeleccionadoEliminar);

            }

        }

    }

    //Convierte un objeto JSON en un arrayList de Moviles
    private ArrayList<Cliente> procesarListaClientes (String objetoJSON){

        Gson gson = new Gson();

        try {

            Type tipoLista = new TypeToken<ArrayList<Cliente>>(){}.getType();
            ArrayList<Cliente> clientesParseados = gson.fromJson(objetoJSON,tipoLista);
            return clientesParseados;

        }

        catch (Exception e){

            //Publico un Toast en la activity que nos llamó
            Toast.makeText(Activity_consultar.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return null;

        }

    }

}
