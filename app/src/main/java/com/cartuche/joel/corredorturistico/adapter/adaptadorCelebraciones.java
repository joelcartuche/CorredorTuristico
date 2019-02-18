package com.cartuche.joel.corredorturistico.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cartuche.joel.corredorturistico.R;
import com.cartuche.joel.corredorturistico.modelo.Celebraciones;

import java.util.ArrayList;

public class adaptadorCelebraciones extends BaseAdapter {
    private Context contexto;
    private ArrayList<Celebraciones> celebraciones;
    private  boolean[]item ;

    public adaptadorCelebraciones (Context contexto, ArrayList<Celebraciones> ZonasTuristicas) {
        this.item= new boolean[ZonasTuristicas.size()];
        this.contexto = contexto;
        this.celebraciones = ZonasTuristicas;

    }


    @Override
    public int getCount() {

        return celebraciones.size();
    }

    @Override
    public Object getItem(int position) {
        return celebraciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(contexto, R.layout.display_celebraciones,null);


        }


        TextView lblNombre = (TextView)convertView.findViewById(R.id.dsp_nombreCelebraciones);
        TextView lblFecha = (TextView)convertView.findViewById(R.id.dsp_fechaCelebraciones);
        TextView lblZona = (TextView)convertView.findViewById(R.id.dsp_zonaCelebraciones);






        Celebraciones celebracionesData = celebraciones.get(position);
        //new DownloadImageTask(imagen)
        //      .execute(zonasTuristicas.getImagen());



        lblNombre.setText(celebracionesData.getNombre());
        lblFecha.setText(celebracionesData.getFecha());
        lblZona.setText(celebracionesData.getZona());




        return convertView;

    }

}
