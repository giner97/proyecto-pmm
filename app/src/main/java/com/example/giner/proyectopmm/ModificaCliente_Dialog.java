package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ModificaCliente_Dialog extends DialogFragment implements View.OnClickListener {

    private EditText editNombre;
    private EditText editApellidos;
    private EditText editDNI;
    private EditText editProvincia;
    private EditText editTelf;
    private ImageButton botonVolver;
    private ImageButton botonOK;

    private AlertDialog dialogo;
    private OnModificaListener escuchador;

    //Variables

        private String nombre;
        private String apellidos;
        private String dni;
        private String provincia;
        private int telf;
        private Cliente clienteAModificar;

    //Constructor del dialogo para pasarle el objeto

    public ModificaCliente_Dialog(Cliente cliente){

        this.clienteAModificar=cliente;
        this.nombre=cliente.getNombre();
        this.apellidos=cliente.getApellidos();
        this.dni=cliente.getDni();
        this.provincia=cliente.getProvincia();
        this.telf=cliente.getTelefono();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Construimos el dialogo

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("MOVILINE");
        builder.setIcon(R.drawable.user);

        View customDialog = getActivity().getLayoutInflater().inflate(R.layout.modifica_cliente_dialog, null);

        //Instancio los widgets

            editNombre = (EditText)customDialog.findViewById(R.id.editTextNombreModificado);
            editApellidos =(EditText)customDialog.findViewById(R.id.editTextApellidosModificado);
            editDNI=(EditText)customDialog.findViewById(R.id.editTextDNIModificado);
            editProvincia=(EditText)customDialog.findViewById(R.id.editTextProvinciaModificada);
            editTelf=(EditText)customDialog.findViewById(R.id.editTextTelefonoModificado);
            botonVolver = (ImageButton)customDialog.findViewById(R.id.imageButtonVolverModifica);
            botonOK = (ImageButton)customDialog.findViewById(R.id.imageButtonModifica);

        //Escuchadores de los botones

            botonVolver.setOnClickListener(this);
            botonOK.setOnClickListener(this);

        //Recuperamos y mostramos los valores del objeto que queremos modificar

            editNombre.setText(nombre);
            editApellidos.setText(apellidos);
            editDNI.setText(dni);
            editProvincia.setText(provincia);
            editTelf.setText(Integer.toString(telf));

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

            if((!editNombre.getText().toString().isEmpty())&&(!editApellidos.getText().toString().isEmpty())&&(!editDNI.getText().toString().isEmpty())&&(!editProvincia.getText().toString().isEmpty())&&(!editTelf.getText().toString().isEmpty())){

                if((!(editNombre.getText().toString()).equals(clienteAModificar.getNombre()))||(!(editApellidos.getText().toString()).equals(clienteAModificar.getApellidos()))||(!(editProvincia.getText().toString()).equals(clienteAModificar.getProvincia()))||(!(editDNI.getText().toString()).equals(clienteAModificar.getDni()))||(Integer.valueOf(editTelf.getText().toString())!=clienteAModificar.getTelefono())){

                    clienteAModificar.setNombre(editNombre.getText().toString());
                    clienteAModificar.setApellidos(editApellidos.getText().toString());
                    clienteAModificar.setDni(editDNI.getText().toString());
                    clienteAModificar.setProvincia(editProvincia.getText().toString());
                    clienteAModificar.setTelefono(Integer.valueOf(editTelf.getText().toString()));
                    escuchador.modificaCliente(clienteAModificar);
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

        public void setModificaoListener(OnModificaListener listener){

            this.escuchador=listener;

        }

        public interface OnModificaListener{

            void modificaCliente(Cliente cl);

        }

}
