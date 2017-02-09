package com.example.giner.proyectopmm;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_main extends AppCompatActivity implements FragmentoActivityMain.FragmentoMainListener, TareaRest.TareaRestListener, Movil_Dialog.OnListener{

    //URL servidor

    private final static String URL_BASE_SERVIDOR = "http://alvaro-ricardo-pmm.hol.es/";

    //Constantes con el codigo personalizado

    private final static int CONSULTA_MOVILES = 0;

    private  final static int REQUEST_CODE=0;

    //Creamos las variables

    private FragmentoActivityMain fragment;
    private Movil_Dialog movil_dialog;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos el fragmento

        fragment=(FragmentoActivityMain)getFragmentManager().findFragmentById(R.id.fragment);
        fragment.setFragmentoMainListener(this);

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

        TareaRest tareaMovilRest = new TareaRest(this,CONSULTA_MOVILES,"GET",URL_BASE_SERVIDOR+"/movil",null,this);
        tareaMovilRest.execute();

    }

    @Override
    public void onTareaRestFinalizada(int codigoOperacion, int codigoRespuestaHttp, String respuestaJson) {

        if (codigoRespuestaHttp == 200 || respuestaJson != null && !respuestaJson.isEmpty()) {

            if (codigoOperacion == 0) {

                ArrayList<Moviles> moviles = procesarListaMoviles(respuestaJson);
                fragment.pasaMoviles(moviles);

            }

        }

    }

    //Convierte un objeto JSON en un arrayList de Moviles
    private ArrayList<Moviles> procesarListaMoviles (String objetoJSON){

        Gson gson = new Gson();

        try {

            Type tipoLista = new TypeToken<ArrayList<Moviles>>(){}.getType();
            ArrayList<Moviles> moviles = gson.fromJson(objetoJSON,tipoLista);
            return moviles;

        }

        catch (Exception e){

            //Publico un Toast en la activity que nos llamó
            Toast.makeText(Activity_main.this, e.getMessage(), Toast.LENGTH_LONG).show();

            return null;

        }

    }

    @Override
    public void onConsultar() {


        Intent intencion = new Intent(this,Activity_consultar.class);
        startActivity(intencion);


    }

    @Override
    public void onMuestraMovil(Moviles movil) {

        //Creamos e instanciamos el Custom Dialog
        transaction = getFragmentManager().beginTransaction();
        movil_dialog = new Movil_Dialog(movil);
        movil_dialog.show(transaction,null);
        movil_dialog.setCancelable(false);
        movil_dialog.setListener(this);

    }

    @Override
    public void onIniciasActividad() {


        Intent intent = new Intent(this,Activity_compra.class);

        startActivityForResult(intent, REQUEST_CODE);


    }

    private void startActivityForResult(Intent intent) {




    }
}
