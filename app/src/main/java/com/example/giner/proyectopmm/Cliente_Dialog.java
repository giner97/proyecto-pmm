package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


public class Cliente_Dialog extends DialogFragment implements View.OnClickListener {

    private TextView textViewNombre;
    private TextView textViewApellidos;
    private TextView textViewDNI;
    private TextView textViewProvincia;
    private TextView textViewTelefono;
    private ImageButton botonVolver;
    private ImageButton botonVerCompras;
    private OnClienteDialogListener escuchador;
    private Cliente cliente;

    private AlertDialog dialogo;

    //Variables

        private String nombre;
        private String apellidos;
        private String dni;
        private String provincia;
        private int telf;

    public Cliente_Dialog(Cliente cliente){

        this.cliente=cliente;
        this.nombre=cliente.getNombre();
        this.apellidos=cliente.getApellidos();
        this.dni=cliente.getDni();
        this.provincia=cliente.getProvincia();
        this.telf=cliente.getTelefono();

    }

    public Dialog onCreateDialog (Bundle savedInstanceState) {

        //Construyo y devuelvo el diálogo
        //Primero genero un constructor de diálogos de Alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("MOVILINE");
        builder.setIcon(R.drawable.user);

        View customDialog = getActivity().getLayoutInflater().inflate(R.layout.cliente_dialog, null);

        textViewNombre = (TextView) customDialog.findViewById(R.id.textViewNombre);
        textViewApellidos = (TextView) customDialog.findViewById(R.id.textViewApellidos);
        textViewDNI = (TextView) customDialog.findViewById(R.id.textViewDNI);
        textViewProvincia = (TextView) customDialog.findViewById(R.id.textViewProvincia);
        textViewTelefono = (TextView) customDialog.findViewById(R.id.textViewTelefono);
        botonVerCompras = (ImageButton)customDialog.findViewById(R.id.imageButtonVerCompras);
        botonVolver = (ImageButton) customDialog.findViewById(R.id.imageButtonVolver);
        botonVerCompras.setOnClickListener(this);
        botonVolver.setOnClickListener(this);

        //Mostramos los datos del objeto

            textViewNombre.setText(nombre);
            textViewApellidos.setText(apellidos);
            textViewDNI.setText(dni);
            textViewProvincia.setText(provincia);
            textViewTelefono.setText(Integer.toString(telf));

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

        if(view.getId()==R.id.imageButtonVerCompras){

            escuchador.abrirDialogCompras(cliente);

        }

        else if(view.getId()==R.id.imageButtonVolver) {

            dialogo.dismiss();

        }

    }

    public void setOnClienteDialogListener(Cliente_Dialog.OnClienteDialogListener listener){
        this.escuchador = listener;
    }



    public interface OnClienteDialogListener{

        void abrirDialogCompras (Cliente cliente);

    }


}
