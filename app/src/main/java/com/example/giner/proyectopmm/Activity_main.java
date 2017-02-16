package com.example.giner.proyectopmm;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Activity_main extends AppCompatActivity implements FragmentoActivityMain.FragmentoMainListener, TareaRest.TareaRestListener, Movil_Dialog.OnListener,ModificaMovil_Dialog.OnModificaMovilListener, InsertarMoviles_Dialog.OnInsertaMovil{

    //URL servidor

    private final static String URL_BASE_SERVIDOR = "http://alvaro-ricardo-pmm.hol.es/";
    public final static String KEY_MOVIL="movil";

    //Constantes con el codigo personalizado
    private final static int CONSULTA_MOVILES = 0;
    private final static int MODIFICA_MOVIL =1;
    private final static int ELIMINA_MOVIL =2;
    private final static int INSERTA_MOVIL =3;
    private final static int CONSULTA_COMPRADORES =4;
    private  final static int REQUEST_CODE=0;

    //Creamos las variables

    private FragmentoActivityMain fragment;
    private Moviles movil;
    private Moviles movilCreado;
    private Movil_Dialog movil_dialog;
    private Compradores_Dialog compradoresDialog;
    private FragmentTransaction transaction;
    private ModificaMovil_Dialog modificaDialogo;
    private FragmentTransaction transaction1;
    private InsertarMoviles_Dialog insertaMovil;
    private FragmentTransaction transaction3;
    private FragmentTransaction transaccion2;

    //ArrayList Moviles

        ArrayList<Moviles>moviles;

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
            Toasty.error(this,"No hay conexión de red.",Toast.LENGTH_SHORT).show();

        }

        TareaRest tareaMovilRest = new TareaRest(this,CONSULTA_MOVILES,"GET",URL_BASE_SERVIDOR+"/movil",null,this);
        tareaMovilRest.execute();

    }

    @Override
    public void onTareaRestFinalizada(int codigoOperacion, int codigoRespuestaHttp, String respuestaJson) {

        if (codigoRespuestaHttp == 200 || respuestaJson != null && !respuestaJson.isEmpty()) {

            if (codigoOperacion == 0) {

                moviles = procesarListaMoviles(respuestaJson);
                fragment.pasaMoviles(moviles);

            }

            else if(codigoOperacion == 1){

                fragment.actualizaFragmentoArrayAdapterMoviles();

            }

            else if(codigoOperacion == 2){

                Toasty.success(this,"Móvil eliminado correctamente",Toast.LENGTH_SHORT).show();
                fragment.actualizaListaMovilesBorrar(movil);

            }

            else if(codigoOperacion == 3){

                Toasty.success(this,"Móvil insertado correctamente",Toast.LENGTH_SHORT).show();
                TareaRest tareaMovilRest = new TareaRest(this,CONSULTA_MOVILES,"GET",URL_BASE_SERVIDOR+"/movil",null,this);
                tareaMovilRest.execute();
                fragment.actualizaFragmentoArrayAdapterMoviles();

            }

            else if(codigoOperacion == 4){

                ArrayList<Cliente>clientesCompradores = procesarListaClientes(respuestaJson);

                //Creamos e instanciamos el dialogo

                transaction3 = getFragmentManager().beginTransaction();
                compradoresDialog=new Compradores_Dialog(clientesCompradores);
                compradoresDialog.show(transaction3,null);
                compradoresDialog.setCancelable(false);

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
            Toasty.error(Activity_main.this,e.getMessage(),Toast.LENGTH_SHORT).show();


            return null;

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
            Toasty.info(Activity_main.this,"Este móvil no ha sido comprado",Toast.LENGTH_SHORT).show();
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

        this.movil=movil;

        //Creamos e instanciamos el Custom Dialog
        transaction = getFragmentManager().beginTransaction();
        movil_dialog = new Movil_Dialog(movil);
        movil_dialog.show(transaction,null);
        movil_dialog.setCancelable(false);
        movil_dialog.setListener(this);

    }

    @Override
    public void onModificaMovil(Moviles movil) {

        //Creamos e instanciamos el dialogo

            transaction1 = getFragmentManager().beginTransaction();
            modificaDialogo=new ModificaMovil_Dialog(movil);
            modificaDialogo.show(transaction1,null);
            modificaDialogo.setCancelable(false);
            modificaDialogo.setModificaMovilListener(this);

    }

    @Override
    public void onEliminaMovil(Moviles movil) {

        this.movil=movil;

        String movilId = Integer.toString(movil.getId_movil());
        TareaRest tareaElimina = new TareaRest(this,ELIMINA_MOVIL,"DELETE",URL_BASE_SERVIDOR+"/movil/"+movilId,null,this);
        tareaElimina.execute();

    }

    @Override
    public void onInsertarMovil() {

        transaccion2 = getFragmentManager().beginTransaction();
        insertaMovil = new InsertarMoviles_Dialog();
        insertaMovil.setOnInsertarMovilListener(this);
        insertaMovil.show(transaccion2,null);
        insertaMovil.setCancelable(false);

    }

    @Override
    public void onIniciasActividad() {

        Intent intent = new Intent(this,Activity_compra.class);
        intent.putExtra(KEY_MOVIL, movil.getId_movil());
        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    public void llamaDialogoCompradores(Moviles movil) {

        TareaRest tareaConsultaCompradores = new TareaRest(this,CONSULTA_COMPRADORES,"GET",URL_BASE_SERVIDOR+"/consultacliente/"+movil.getId_movil(),null,this);
        tareaConsultaCompradores.execute();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode== Activity.RESULT_OK) {

            if(requestCode==Activity_main.REQUEST_CODE) {

                //Le restamos 1 unidad al stock

                movil.setStock(movil.getStock() - 1);

                //Creamos un objeto GSON

                Gson gson = new Gson();

                //Convertimos un objeto compra en una cadena JSON

                String parametroJson = gson.toJson(movil);

                String id = Integer.toString(movil.getId_movil());

                //Preparamos y ejecutamos la tarea

                TareaRest tareaModifica = new TareaRest(this, MODIFICA_MOVIL, "PUT", URL_BASE_SERVIDOR + "/movil/" + id, parametroJson, this);
                tareaModifica.execute();

            }

        }

    }

    @Override
    public void modificaMovil(Moviles movil) {

        //Creamos un objeto gson

            Gson gson = new Gson();

        //Convertimos el objeto cliente en una cadena Json

            String parametroJson = gson.toJson(movil);

        String movilID=Integer.toString(movil.getId_movil());

        TareaRest tareaModifica = new TareaRest(this,MODIFICA_MOVIL,"PUT",URL_BASE_SERVIDOR+"/movil/"+movilID,parametroJson,this);
        tareaModifica.execute();
        Toasty.success(this,"Móvil modificado correctamente",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInsertaNuevoMovil(Moviles movil) {

        this.movilCreado=movil;

        //Creamos un objeto GSON

            Gson gson = new Gson();

        //Convertimos un objeto movil en una cadena JSON

            String parametroJson = gson.toJson(movilCreado);

        TareaRest tarea = new TareaRest(this,INSERTA_MOVIL,"POST",URL_BASE_SERVIDOR+"/movil",parametroJson,this);
        tarea.execute();

    }

}
