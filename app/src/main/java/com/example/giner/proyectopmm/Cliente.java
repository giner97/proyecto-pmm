package com.example.giner.proyectopmm;


import java.util.ArrayList;
import java.sql.Date;

public class Cliente {

    //Variables

        private int id_cliente,telefono;
        private String nombre,apellidos,dni,provincia;

    //Constructor

    public Cliente(){}

        public Cliente(int id_cliente,String nombre,String apellidos,String dni, String provincia,int telefono){

            this.id_cliente=id_cliente;
            this.nombre=nombre;
            this.apellidos=apellidos;
            this.dni=dni;
            this.provincia=provincia;
            this.telefono=telefono;

        }

    //Getters y Setters

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getId_cliente() {
            return id_cliente;
        }

        public void setId_cliente(int id_cliente) {
            this.id_cliente = id_cliente;
        }

        public String getDni() {
            return dni;
        }

        public void setDni(String dni) {
            this.dni = dni;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public int getTelefono() {
            return telefono;
        }

        public void setTelefono(int telefono) {
            this.telefono = telefono;
        }

    @Override
    public String toString() {

        String resultado = this.nombre+" "+this.apellidos;
        return resultado;

    }

}
