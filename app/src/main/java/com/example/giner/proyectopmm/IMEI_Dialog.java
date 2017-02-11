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


public class IMEI_Dialog extends DialogFragment implements View.OnClickListener{

    //Widgets

        private AlertDialog dialogo;
        private EditText editIMEI;
        private ImageButton oki;
        private ImageButton rturn;

    //Variable para almacenar el imei

        private long imei;

    //Escuchador

        private OnImeiDialog escuchador;

    //OnCreateDialog


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Construimos el dialogo

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("MOVILINE");
            builder.setIcon(R.drawable.icon_dialog);
            View customDialog = getActivity().getLayoutInflater().inflate(R.layout.imei_dialog,null);

        //Instanciamos los objetos

            editIMEI = (EditText)customDialog.findViewById(R.id.editTextIMEI);
            oki = (ImageButton)customDialog.findViewById(R.id.okiButton);
            rturn=(ImageButton)customDialog.findViewById(R.id.rturnButton);

        //Escuchador del boton

            oki.setOnClickListener(this);
            rturn.setOnClickListener(this);

        //Le paso la vista al dialogo

            builder.setView(customDialog);

        //Dialogo modal

            dialogo = builder.create();
            dialogo.setCanceledOnTouchOutside(false);

        return dialogo;

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.okiButton) {

            if (!editIMEI.getText().toString().isEmpty()) {

                if(editIMEI.length()==15) {

                    imei = Long.parseLong(editIMEI.getText().toString());
                    escuchador.ejecutaTareaCompra(imei);
                    dismiss();

                }

                else{

                    Toasty.warning(getActivity(), "El IMEI introducido no tiene 15 dígitos.", Toast.LENGTH_SHORT).show();

                }

            } else {

                Toasty.warning(getActivity(), "Has dejado el campo vacio", Toast.LENGTH_SHORT).show();

            }

        }

        else{

            dismiss();

        }

    }

    //Interfaz del dialogo

    public interface OnImeiDialog{

        void ejecutaTareaCompra(long imei);

    }

    public void setImeiDialogListener(OnImeiDialog escuchador){

        this.escuchador=escuchador;

    }


}
