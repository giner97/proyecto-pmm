package com.example.giner.proyectopmm;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrayAdapterMoviles extends ArrayAdapter<Moviles>{

    private Context contexto;

    public ArrayAdapterMoviles(Context context, ArrayList<Moviles>arrayListMovil) {
        super(context,0,arrayListMovil);
        this.contexto=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //La primera vez que se ejecute, inflara el layout

        if(convertView==null){

            convertView = LayoutInflater.from(contexto).inflate(R.layout.layout_listview_moviles,parent,false);

        }

        //Obtenemos el objeto Movil correspondiente a esa posicion

            Moviles movil=getItem(position);

        //Obtenemos todos los widgets del layout

            TextView nombreMovil = (TextView)convertView.findViewById(R.id.movil);
            TextView precioMovil = (TextView)convertView.findViewById(R.id.precio);

        //Mostramos los datos

            nombreMovil.setText(movil.getMarca()+" "+movil.getModelo());
            precioMovil.setText(String.valueOf(movil.getPrecio())+"€");

        //Devolvemos el convertView

            return convertView;

    }
}
