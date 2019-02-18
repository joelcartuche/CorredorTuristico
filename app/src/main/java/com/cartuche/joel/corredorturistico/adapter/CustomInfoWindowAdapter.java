package com.cartuche.joel.corredorturistico.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cartuche.joel.corredorturistico.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String imagenAdvertencia;


    public CustomInfoWindowAdapter(LayoutInflater inflater,String nombre,String descripcion,String imagen,String imagenAdvertencia){
        this.inflater = inflater;
        this.nombre=nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.imagenAdvertencia = imagenAdvertencia;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.info_marker, null);
        ((TextView)v.findViewById(R.id.lbl_nombreMarker)).setText(nombre);

        ((TextView)v.findViewById(R.id.lbl_descripcionMarker)).setText(descripcion);

        ImageView imagenPrimaria= v.findViewById(R.id.img_sitioMarker);
        ImageView imagenAdvertir= v.findViewById(R.id.img_advertenciaMarker);

        Picasso.with(inflater.getContext()).load(imagen).into(imagenPrimaria);
        Picasso.with(inflater.getContext()).load(imagenAdvertencia).into(imagenAdvertir);
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }


}