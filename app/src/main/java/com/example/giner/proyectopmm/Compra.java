package com.example.giner.proyectopmm;


public class Compra {

    //Variables

        private int id_cliente;
        private int id_movil;
        private long imei;

    //Constructores

        public Compra(){


        }

        public Compra(int id_cliente,int id_movil, long imei){

            this.id_cliente=id_cliente;
            this.id_movil=id_movil;
            this.imei=imei;

        }

    //Getters y Setters


        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }

        public int getId_cliente() {
                return id_cliente;
        }

        public void setId_cliente(int id_cliente) {
            this.id_cliente = id_cliente;
        }

        public int getId_movil() {
            return id_movil;
        }

        public void setId_movil(int id_movil) {
            this.id_movil = id_movil;
        }
}
