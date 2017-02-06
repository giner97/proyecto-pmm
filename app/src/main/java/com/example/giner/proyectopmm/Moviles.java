package com.example.giner.proyectopmm;


import java.util.ArrayList;

public class Moviles {

    //Variables

        private String marca,modelo,caracteristicas,procesador,ram,almacenamiento,sistemaoperativo;
        private int precio,id_movil;
        private boolean stock;

    //Constructor

        public Moviles(int id_movil,String marca,String modelo, String caracteristicas, String procesador, String ram, String almacenamiento,boolean stock, String sistemaoperativo, int precio){

            this.id_movil=id_movil;
            this.marca=marca;
            this.modelo=modelo;
            this.caracteristicas=caracteristicas;
            this.procesador=procesador;
            this.ram=ram;
            this.almacenamiento=almacenamiento;
            this.stock=stock;
            this.sistemaoperativo=sistemaoperativo;
            this.precio=precio;

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

        public String getCaracteristicas() {
            return caracteristicas;
        }

        public void setCaracteristicas(String caracteristicas) {
            this.caracteristicas = caracteristicas;
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

        public String getAlmacenamiento() {
            return almacenamiento;
        }

        public void setAlmacenamiento(String almacenamiento) {
            this.almacenamiento = almacenamiento;
        }

        public String getSistemaoperativo() {
            return sistemaoperativo;
        }

        public void setSistemaoperativo(String sistemaoperativo) {
            this.sistemaoperativo = sistemaoperativo;
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

        public boolean isStock() {
            return stock;
        }

        public void setStock(boolean stock) {
            this.stock = stock;
        }


}
