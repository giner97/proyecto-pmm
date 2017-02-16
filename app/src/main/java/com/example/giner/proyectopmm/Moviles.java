package com.example.giner.proyectopmm;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Moviles{

    //Variables

        private String marca;
        private String modelo;
        private String procesador;
        private String ram;
        private int precio;
        private int id_movil;
        private int stock;

    //Constructor

    public Moviles(){


    }

        public Moviles(int id_movil,String marca,String modelo, String procesador, String ram,int precio,int stock){

            this.id_movil=id_movil;
            this.marca=marca;
            this.modelo=modelo;
            this.procesador=procesador;
            this.ram=ram;
            this.precio=precio;
            this.stock=stock;

        }

    //Getters y setters


        public String getMarca() {
            return marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getProcesador() {
            return procesador;
        }

        public void setProcesador(String procesador) {
            this.procesador = procesador;
        }

        public String getRam() {
            return ram;
        }

        public void setRam(String ram) {
            this.ram = ram;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public int getId_movil() {
            return id_movil;
        }

        public void setId_movil(int id_movil) {
            this.id_movil = id_movil;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

    @Override
    public String toString() {

        String resultado = this.marca+" "+this.modelo;
        return resultado;

    }

}
