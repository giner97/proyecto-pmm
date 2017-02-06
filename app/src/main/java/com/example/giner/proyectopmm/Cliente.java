package com.example.giner.proyectopmm;


import java.util.ArrayList;
import java.util.Date;

public class Cliente {

    //Variables

        private int id_cliente,cp;
        private String nombre,apellidos,dni,pais,comunidadAutonoma,provincia,domicilio;
        private Date fechaNacimiento;

    //Constructor

        public Cliente(int id_cliente,String nombre,String apellidos,String dni, Date fechaNacimiento, int cp, String pais, String comunidadAutonoma, String provincia, String domicilio){

            this.id_cliente=id_cliente;
            this.nombre=nombre;
            this.apellidos=apellidos;
            this.dni=dni;
            this.fechaNacimiento=fechaNacimiento;
            this.cp=cp;
            this.pais=pais;
            this.comunidadAutonoma=comunidadAutonoma;
            this.provincia=provincia;
            this.domicilio=domicilio;

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

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
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

        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public String getComunidadAutonoma() {
            return comunidadAutonoma;
        }

        public void setComunidadAutonoma(String comunidadAutonoma) {
            this.comunidadAutonoma = comunidadAutonoma;
        }

        public String getProvincia() {
            return provincia;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getDomicilio() {
            return domicilio;
        }

        public void setDomicilio(String domicilio) {
            this.domicilio = domicilio;
        }

        public Date getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

    @Override
    public String toString() {

        String resultado = this.nombre;
        return resultado;

    }

}
