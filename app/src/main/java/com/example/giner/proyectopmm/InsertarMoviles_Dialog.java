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

public class InsertarMoviles_Dialog extends DialogFragment implements View.OnClickListener {

    //Widgets

        private AlertDialog dialog;
        private EditText editTextMarca;
        private EditText editTextModelo;
        private EditText editTextProcesador;
        private EditText editTextRAM;
        private EditText editTextPrecio;
        private EditText editTextStock;

        private ImageButton botonVolver;
        private ImageButton botonAnyadir;

        private Moviles movilInsertado;
        private OnInsertaMovil escuchador;

    //Variables para almacenar los datos de la inserccion

        private String marca;
        private String modelo;
        private String procesador;
        private String ram;
        private int precio;
        private int stock;

    //OnCreateDialog


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Construyo el dialogo

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("MOVILINE");
            builder.setIcon(R.drawable.icon_dialog);

        //Inflo la vista del dialogo

            View customDialog = getActivity().getLayoutInflater().inflate(R.layout.insertar_movil_dialog,null);

        //Intancio los objetos

            editTextMarca=(EditText)customDialog.findViewById(R.id.editTextMarca);
            editTextModelo=(EditText)customDialog.findViewById(R.id.editTextModelo);
            editTextPrecio=(EditText)customDialog.findViewById(R.id.editTextPrecio);
            editTextProcesador=(EditText)customDialog.findViewById(R.id.editTextProcesador);
            editTextRAM=(EditText)customDialog.findViewById(R.id.editTextRAM);
            editTextStock=(EditText)customDialog.findViewById(R.id.editStock);
            botonAnyadir=(ImageButton)customDialog.findViewById(R.id.imageButtonAnyadirMovil);
            botonVolver=(ImageButton)customDialog.findViewById(R.id.imageButtonVolver);

        //Escuchadores de los botones

            botonAnyadir.setOnClickListener(this);
            botonVolver.setOnClickListener(this);

        //Seteo el layout del dialog

            builder.setView(customDialog);

        //Dialogo modal

            dialog=builder.create();
            dialog.setCanceledOnTouchOutside(false);

        //Devuelvo el alertDialog

        return dialog;

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.imageButtonAnyadirMovil){

            marca=editTextMarca.getText().toString();
            modelo=editTextModelo.getText().toString();
            procesador=editTextProcesador.getText().toString();
            ram=editTextRAM.getText().toString();

            if((!marca.isEmpty())&&(!modelo.isEmpty())&&(!procesador.isEmpty())&&(!ram.isEmpty())&&(!editTextStock.getText().toString().isEmpty())&&(!editTextPrecio.getText().toString().isEmpty())){

                stock=Integer.valueOf(editTextStock.getText().toString());
                precio=Integer.valueOf(editTextPrecio.getText().toString());

                //Creo un nuevo objeto Moviles

                    Moviles movilInsertado = new Moviles();

                //Inserto los datos insertado en el objeto

                    movilInsertado.setMarca(marca);
                    movilInsertado.setModelo(modelo);
                    movilInsertado.setRam(ram);
                    movilInsertado.setStock(stock);
                    movilInsertado.setProcesador(procesador);
                    movilInsertado.setPrecio(precio);

                //Llamo al metodo que realiza la tarea de insercion y le paso el objeto

                    escuchador.onInsertaNuevoMovil(movilInsertado);

                //Cierro el dialogo\

                    dismiss();

            }

            else{

                Toasty.warning(getActivity(), "Te has dejado algun campo vacio", Toast.LENGTH_SHORT).show();

            }

        }

        else{

            //Si se pulsa el boton cancelar se cerrara el dialogo

                dismiss();

        }
















    }

    //Callback del dialogo

        public interface OnInsertaMovil{

            void onInsertaNuevoMovil(Moviles movil);

        }

        public void setOnInsertarMovilListener(OnInsertaMovil escuchador){

            this.escuchador=escuchador;

        }



}
