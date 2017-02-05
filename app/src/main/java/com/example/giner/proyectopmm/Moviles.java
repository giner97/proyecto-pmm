package com.example.giner.proyectopmm;


import java.util.ArrayList;

public class Moviles {

    //Variables

        private String nombre;
        private int precio;

    //Constructor

        public Moviles(String nombre, int precio){

            this.nombre=nombre;
            this.precio=precio;

        }

    //Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /*prueba................................................................*/

    private static final String[] arrayNombres =
            {"Alberto", "Sara", "José Emilio", "Pier Paolo", "Alvaro", "Alejandro","Javier",
                    "Marcos","Iván","María", "Vicente Jesús", "Alejandro", "Borja", "Antonio",
                    "Jonatan","Enrique","Alejandro","Jose Agustín","Daniel","Carlos"};

    private static final int[] arrayEdades =
            {25, 22, 34, 21, 20, 19,19,29,19,29,25,20,22,20,20,25,20,19,26,19};

    public static ArrayList<Moviles> generaListaAlumnos (){
        ArrayList<Moviles> arrayAlumno = new ArrayList<Moviles>(20);
        for (int i=0; i<20;i++){
            Moviles movil = new Moviles (arrayNombres[i],arrayEdades[i]);
            arrayAlumno.add(movil);
        }
        return  arrayAlumno;

    }

}
