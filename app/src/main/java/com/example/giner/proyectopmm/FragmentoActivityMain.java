package com.example.giner.proyectopmm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class FragmentoActivityMain extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, PopupMenu.OnMenuItemClickListener,AdapterView.OnItemLongClickListener {

    //Widgets del fragmento

        private FragmentoMainListener escuchador;
        private ImageButton boton_Consultar;
        private ImageButton boton_Insertar;
        private ListView listaMoviles;
        private ArrayList<Moviles>arrayMoviles;
        private Moviles movil;
        private ArrayAdapter<Moviles> arrayAdapter;


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
            boton_Insertar = (ImageButton)v.findViewById(R.id.botonInsertarMoviles);
            listaMoviles = (ListView)v.findViewById(R.id.listViewMoviles);

        //ActionListener del botonConsultar

            boton_Consultar.setOnClickListener(this);
            boton_Insertar.setOnClickListener(this);

        //Escuchador del listViewMoviles

            listaMoviles.setOnItemClickListener(this);
            listaMoviles.setOnItemLongClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.botonConsultar){

            escuchador.onConsultar();

        }

        else if(view.getId()==R.id.botonInsertarMoviles){

            escuchador.onInsertarMovil();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        movil=(Moviles)adapterView.getItemAtPosition(i);
        escuchador.onMuestraMovil(movil);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.modificar:

                escuchador.onModificaMovil(movil);
                return true;

            case R.id.eliminar:

                escuchador.onEliminaMovil(movil);
                return true;

            default:

                return false;

        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Recupero el objeto Movil seleccionado

            movil=(Moviles)adapterView.getItemAtPosition(i);

        //Popup  menu

        PopupMenu popup = new PopupMenu(getActivity(), listaMoviles);

        popup.getMenuInflater().inflate(R.menu.popup_menu_moviles, popup.getMenu());

        popup.setOnMenuItemClickListener(this);

        popup.show();

        return true;

    }


    public interface FragmentoMainListener{

        void onConsultar();
        void onMuestraMovil(Moviles movil);
        void onModificaMovil(Moviles movil);
        void onEliminaMovil(Moviles movil);
        void onInsertarMovil();

    }

    public void setFragmentoMainListener(FragmentoMainListener escuchador){

        this.escuchador=escuchador;

    }

    public void pasaMoviles(ArrayList<Moviles>moviles){

        this.arrayMoviles=moviles;

        if(moviles!=null) {

            //Creamos un arrayAdapter y le pasamos el arrayList

             arrayAdapter= new ArrayAdapterMoviles(getActivity(), arrayMoviles);

            //pasamos el arrayList al listView

            listaMoviles.setAdapter(arrayAdapter);

        }

    }

    public void actualizaFragmentoArrayAdapterMoviles(){

        arrayAdapter.notifyDataSetChanged();

    }

    public void actualizaListaMovilesBorrar(Moviles movil){

        arrayMoviles.remove(movil);
        arrayAdapter.notifyDataSetChanged();

    }

}
