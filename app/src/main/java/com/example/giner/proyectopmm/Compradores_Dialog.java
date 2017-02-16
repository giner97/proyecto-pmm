package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Compradores_Dialog extends DialogFragment implements View.OnClickListener{

    //Creo los widgets

        private ImageButton botonVolver;
        private ListView listaDeCompradores;
        private ArrayAdapter<Cliente>arrayAdapterCompradores;

    //ArrayList compradores

        ArrayList<Cliente>compradores;

    //Dialogo

        private AlertDialog dialogo;

    //Constructor

        public Compradores_Dialog(ArrayList<Cliente>compradores){

            this.compradores=compradores;

        }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Construyo y devuelvo el diálogo
        //Primero genero un constructor de diálogos de Alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("MOVILINE");
        builder.setIcon(R.drawable.icon_dialog);

        View customDialog = getActivity().getLayoutInflater().inflate(R.layout.compras_dialog, null);

        //Intancio los widgets

            listaDeCompradores = (ListView)customDialog.findViewById(R.id.listViewCompras);
            botonVolver = (ImageButton)customDialog.findViewById(R.id.imageButtonVolver);
            botonVolver.setOnClickListener(this);

        //Cargo el ListView

            if(listaDeCompradores!=null){

                if((this.compradores!=null)&&(arrayAdapterCompradores==null)){

                    //Instancio el arrayAdapter y le paso el array de compradores

                        arrayAdapterCompradores = new ArrayAdapter<Cliente>(getActivity(),android.R.layout.simple_list_item_1,compradores);

                    //Le paso el arrayAdpater al listView

                        listaDeCompradores.setAdapter(arrayAdapterCompradores);

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
