package com.cartuche.joel.corredorturistico.adapter;

import android.content.Context;
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
        private  boolean[]item ;

        public adapterImagenes (Context contexto, ArrayList<Imagenes> imagenes) {
            this.item= new boolean[imagenes.size()];
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
                convertView = View.inflate(contexto, R.layout.displaynuevozonas,null);


            }

            return convertView;

        }
}