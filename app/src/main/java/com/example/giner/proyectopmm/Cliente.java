package com.example.giner.proyectopmm;


import java.util.ArrayList;

public class Cliente {

    //Variables

        private String nombre;

    //Constructor

        public Cliente(String nombre){

            this.nombre=nombre;

        }

    //Getters y Setters

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    /*prueba................................................................*/

    private static final String[] arrayNombres =
            {"Alberto", "Sara", "José Emilio", "Pier Paolo", "Alvaro", "Alejandro","Javier",
                    "Marcos","Iván","María", "Vicente Jesús", "Alejandro", "Borja", "Antonio",
                    "Jonatan","Enrique","Alejandro","Jose Agustín","Daniel","Carlos"};


    public static ArrayList<Cliente> generaListaAlumnos (){
        ArrayList<Cliente> arrayAlumno = new ArrayList<Cliente>(20);
        for (int i=0; i<20;i++){
            Cliente cliente = new Cliente (arrayNombres[i]);
            arrayAlumno.add(cliente);
        }
        return  arrayAlumno;

    }
}
