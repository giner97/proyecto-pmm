package com.example.giner.proyectopmm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created by ricardo on 09/02/2017.
 */

public class Registro_Dialog extends DialogFragment implements View.OnClickListener{

    private AlertDialog dialogo;
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextDNI;
    private EditText editTextTelefono;
    private EditText editTextProvincia;

    private ImageButton botonVolver;
    private ImageButton botonAñadir;

    private Cliente clienteInsertado;
    private OnInsertarCliente escuchador;

    private String nombre;
    private String apellidos;
    private String dni;
    private int telefono;
    private String provincia;



    public Dialog onCreateDialog (Bundle savedInstanceState){

        //Construyo y devuelvo el diálogo
        //Primero genero un constructor de diálogos de Alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("MOVILINE");
        builder.setIcon(R.drawable.icon_dialog);
        View customDialog = getActivity().getLayoutInflater().inflate(R.layout.registro_dialog, null);




        //Instanciamos los objetos
        editTextNombre =(EditText)customDialog.findViewById(R.id.editTextNombre);
        editTextApellidos =(EditText)customDialog.findViewById(R.id.editTextApellidos);
        editTextDNI =(EditText)customDialog.findViewById(R.id.editTextDNI);
        editTextTelefono =(EditText)customDialog.findViewById(R.id.editTextTelefono);
        editTextProvincia=(EditText)customDialog.findViewById(R.id.editTextProvincia);

        botonVolver=(ImageButton)customDialog.findViewById(R.id.imageButtonVolver);
        botonAñadir=(ImageButton)customDialog.findViewById(R.id.imageButtonAñadirUsuario);


        botonVolver.setOnClickListener(this);
        botonAñadir.setOnClickListener(this);

        //Seteo el layout en el diálogo
        builder.setView(customDialog);

        //Hago que el diálogo no sea cancelable pulsando fuera de él (diálogo modal)
        dialogo = builder.create();
        dialogo.setCanceledOnTouchOutside(false);


        reiniciarWidgets();

        //Devuelvo el AlertDialog ya configurado
        return dialogo;

    }

    //Método privado para reiniciar widgets (nuevo pulsado)
    private void reiniciarWidgets (){
        editTextNombre.setText("");
        editTextApellidos.setText("");
        editTextDNI.setText("");
        editTextTelefono.setText("");
        editTextProvincia.setText("");
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.imageButtonAñadirUsuario){

                nombre=editTextNombre.getText().toString();
                apellidos=editTextApellidos.getText().toString();
                dni=editTextDNI.getText().toString();
                provincia=editTextProvincia.getText().toString();


            if((!nombre.isEmpty())&&(!apellidos.isEmpty())&&(!dni.isEmpty())&&(!provincia.isEmpty())&&(!editTextTelefono.getText().toString().isEmpty())){

                telefono=Integer.valueOf(editTextTelefono.getText().toString());

                    clienteInsertado = new Cliente();

                    clienteInsertado.setNombre(nombre);
                    clienteInsertado.setApellidos(apellidos);
                    clienteInsertado.setDni(dni);
                    clienteInsertado.setProvincia(provincia);
                    clienteInsertado.setTelefono(telefono);
                    escuchador.onNuevoCliente(clienteInsertado);

                    dialogo.dismiss();

            }

            else{

                Toasty.warning(getActivity(), "Te has dejado algun campo vacio", Toast.LENGTH_SHORT).show();

            }

        }

        else  if(view.getId()==R.id.imageButtonVolver){
            //Si se pulsa cancelar el dialogo se cerrará sí o sí sin realizar ninguna acción
            dialogo.dismiss();

        }

    }

    public interface OnInsertarCliente{
        void onNuevoCliente(Cliente cliente);
    }

    public void setOnInsertarClienteListener(OnInsertarCliente escuchador){
        this.escuchador = escuchador;
    }

}
