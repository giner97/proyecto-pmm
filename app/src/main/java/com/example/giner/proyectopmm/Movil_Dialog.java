package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ricardo on 05/02/2017.
 */

public class Movil_Dialog extends DialogFragment implements View.OnClickListener{



    private TextView textViewMarca;
    private TextView textViewModelo;
    private TextView textViewColor;
    private TextView textViewProcesador;
    private TextView textViewRAM;
    private TextView textViewPrecio;
    private ImageButton botonVolver;
    private ImageButton botonComprar;


    private int id_movil;
    private String precio;
    private String marca, modelo, caracteristicas,procesador,ram;

    private AlertDialog dialogo;
    private OnListener escuchador;



    public Movil_Dialog(Moviles movil){

        this.id_movil = movil.getId_movil();
        this.marca= movil.getMarca().toString();
        this.modelo= movil.getModelo().toString();
        this.procesador=movil.getProcesador().toString();
        this.ram= movil.getRam().toString();
        this.precio= String.valueOf(movil.getPrecio())+"€";

    }

    public Dialog onCreateDialog (Bundle savedInstanceState){

    //Construyo y devuelvo el diálogo
    //Primero genero un constructor de diálogos de Alerta
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("MOVILINE");
    builder.setIcon(R.drawable.icon_dialog);
    View customDialog = getActivity().getLayoutInflater().inflate(R.layout.movil_dialog, null);

    textViewMarca=(TextView)customDialog.findViewById(R.id.textViewMarca);
    textViewModelo=(TextView)customDialog.findViewById(R.id.textViewModelo);
    textViewProcesador=(TextView)customDialog.findViewById(R.id.textViewProcesador);
    textViewRAM=(TextView)customDialog.findViewById(R.id.textViewRAM);
    textViewPrecio=(TextView)customDialog.findViewById(R.id.textViewPrecio);

    botonVolver=(ImageButton)customDialog.findViewById(R.id.imageButtonVolver);
    botonComprar=(ImageButton)customDialog.findViewById(R.id.imageButtonComprar);

    botonVolver.setOnClickListener(this);
    botonComprar.setOnClickListener(this);


        textViewMarca.setText(marca);
        textViewModelo.setText(modelo);
        textViewRAM.setText(ram);


        textViewProcesador.setText(procesador);
        textViewPrecio.setText(precio);

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


        if (view.getId()==R.id.imageButtonComprar){

            escuchador.onIniciasActividad();

        }

        else {
            //Si se pulsa cancelar el dialogo se cerrará sí o sí sin realizar ninguna acción
            dialogo.dismiss();
        }

    }




    public void setListener(OnListener listener){
        this.escuchador = listener;
    }



    //Construyo una interface que defina la callback onLogin()
    public interface OnListener{

        void onIniciasActividad ();

    }

}
