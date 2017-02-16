package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class Compras_Dialog extends DialogFragment implements View.OnClickListener{

    //Creo los widgets

        private ImageButton botonVolver;
        private ListView listaDeCompras;
        private ArrayAdapter<Moviles> arrayAdapterMovilesComprados;

    //ArrayList moviles comprados

        ArrayList<Moviles>movilesComprados;

    private AlertDialog dialogo;

    //Constructor

        public Compras_Dialog(ArrayList<Moviles>movilesComprados){

            this.movilesComprados=movilesComprados;

        }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Construyo y devuelvo el diálogo
        //Primero genero un constructor de diálogos de Alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("MOVILINE");
        builder.setIcon(R.drawable.icon_dialog);

        View customDialog = getActivity().getLayoutInflater().inflate(R.layout.compras_dialog, null);

        //Instancio los widgets

        listaDeCompras = (ListView)customDialog.findViewById(R.id.listViewCompras);
        botonVolver = (ImageButton)customDialog.findViewById(R.id.imageButtonVolver);
        botonVolver.setOnClickListener(this);

        //Cargo el ListView

            if(listaDeCompras!=null){

                if((this.movilesComprados!=null)&&(arrayAdapterMovilesComprados==null)) {

                    //Instancio el arrayAdapter y le paso el array de movilesComprados

                    arrayAdapterMovilesComprados = new ArrayAdapter<Moviles>(getActivity(), android.R.layout.simple_list_item_1, movilesComprados);

                    //Le paso el arrayAdapter al ListView

                    listaDeCompras.setAdapter(arrayAdapterMovilesComprados);

                }

                else{

                    dismiss();

                }

            }

        //Seteo el layout en el diálogo
        builder.setView(customDialog);

        //Hago que el diálogo no sea cancelable pulsando fuera de él (diálogo modal)
        dialogo = builder.create();
        dialogo.setCanceledOnTouchOutside(false);

        //Devuelvo el AlertDialog ya configurado
        return dialogo;

    }

    @Override
    public void onClick(View view) {

        dismiss();

    }

}
