package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ModificaMovil_Dialog extends DialogFragment implements View.OnClickListener{

    //Widgets

        private EditText editStock;
        private EditText editPrecio;
        private ImageButton botonVolver;
        private ImageButton botonOk;

        private AlertDialog dialogo;
        private OnModificaMovilListener escuchador;

    //Variables

        private int stock;
        private int precio;
        private Moviles movilAModificar;

    //Constructor del dialogo para pasarle el objeto

        public ModificaMovil_Dialog(Moviles movil){

            this.movilAModificar=movil;
            this.stock=movil.getStock();
            this.precio=movil.getPrecio();

        }

    //OnCreateDialog


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Construimos el dialogo

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("MOVILINE");
        builder.setIcon(R.drawable.user);

        View customDialog = getActivity().getLayoutInflater().inflate(R.layout.modifica_movil_dialog, null);

        //Instancio los widgets

            editStock=(EditText)customDialog.findViewById(R.id.editTextStockModificada);
            editPrecio=(EditText)customDialog.findViewById(R.id.editTextPrecioModificado);
            botonVolver = (ImageButton)customDialog.findViewById(R.id.imageButtonVolverModifica);
            botonOk = (ImageButton)customDialog.findViewById(R.id.imageButtonModifica);

        //Escuchadores de los botones

            botonVolver.setOnClickListener(this);
            botonOk.setOnClickListener(this);

        //Recuperamos y mostramos los valores del objeto que queremos modificar

            editStock.setText(Integer.toString(stock));
            editPrecio.setText(Integer.toString(precio));

        //Seteamos del layout del dialogo

            builder.setView(customDialog);

        //Hacemos que el layout no sea cancelable

            dialogo = builder.create();
            dialogo.setCanceledOnTouchOutside(false);

        //Devolvemos el dialogo

            return dialogo;

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.imageButtonModifica){

            if((!editPrecio.getText().toString().isEmpty())&&(!editStock.getText().toString().isEmpty())){

                if((Integer.valueOf(editStock.getText().toString())!=movilAModificar.getStock())||(Integer.valueOf(editPrecio.getText().toString())!=movilAModificar.getPrecio())){

                    movilAModificar.setStock(Integer.valueOf(editStock.getText().toString()));
                    movilAModificar.setPrecio(Integer.valueOf(editPrecio.getText().toString()));
                    escuchador.modificaMovil(movilAModificar);
                    dismiss();

                }

                else{

                    Toasty.info(getActivity(), "Se han introducido los mismos datos.", Toast.LENGTH_SHORT).show();

                }

            }

            else{

                Toasty.warning(getActivity(), "Te has dejado algun campo vacio.", Toast.LENGTH_SHORT).show();

            }

        }

        else if(view.getId()==R.id.imageButtonVolverModifica){

            dismiss();

        }

    }

    //Callback del dialogo

        public void setModificaMovilListener(OnModificaMovilListener escuchador){

            this.escuchador=escuchador;

        }

    public interface OnModificaMovilListener{

            void modificaMovil(Moviles movil);

        }

}
