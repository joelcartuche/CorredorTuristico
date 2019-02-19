package com.cartuche.joel.corredorturistico.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cartuche.joel.corredorturistico.R;
import com.cartuche.joel.corredorturistico.modelo.Imagenes;
import com.cartuche.joel.corredorturistico.modelo.ZonasTuristicas;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterImagenes extends  BaseAdapter{
        private Context contexto;
        private ArrayList<Imagenes> imagenesList;


        public adapterImagenes (Context contexto, ArrayList<Imagenes> imagenes) {

            this.contexto = contexto;
            this.imagenesList = imagenes;

        }


        @Override
        public int getCount() {

            return imagenesList.size();
        }

        @Override
        public Object getItem(int position) {
            return imagenesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){

                convertView = View.inflate(contexto, R.layout.gri_item,null);


            }

            ImageView imagen = convertView.findViewById(R.id.imagen_sitio);
            TextView nombreSitio = convertView.findViewById(R.id.lbl_sitio);
            TextView nombreZona = convertView.findViewById(R.id.lbl_zona);

            Imagenes imagenNuevo = imagenesList .get(position);


            Picasso.with(contexto).load(imagenNuevo.getImagenes()).resize(190,182).into(imagen);

            nombreSitio.setText(imagenNuevo.getNombre());
            nombreZona.setText(imagenNuevo.getNombreZona());

            return convertView;

        }
}