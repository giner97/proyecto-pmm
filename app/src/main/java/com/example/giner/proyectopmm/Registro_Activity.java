package com.example.giner.proyectopmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Registro_Activity extends AppCompatActivity {


    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextDNI;
    private EditText editTextTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);

        //Instanciamos los objetos
        editTextNombre =(EditText)findViewById(R.id.editTextNombre);
        editTextApellidos =(EditText)findViewById(R.id.editTextApellidos);
        editTextDNI =(EditText)findViewById(R.id.editTextDNI);
        editTextTelefono =(EditText)findViewById(R.id.editTextTelefono);


    }
}
