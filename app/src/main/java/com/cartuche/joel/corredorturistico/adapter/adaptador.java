package com.cartuche.joel.corredorturistico.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cartuche.joel.corredorturistico.DescargarImagen.DownloadImageTask;
import com.cartuche.joel.corredorturistico.R;
import com.cartuche.joel.corredorturistico.modelo.ZonasTuristicas;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adaptador extends BaseAdapter {
    private Context contexto;
    private ArrayList<ZonasTuristicas> ZonasTuristicas;
    private  boolean[]item ;

    public adaptador(Context contexto, ArrayList<ZonasTuristicas> ZonasTuristicas) {
        this.item= new boolean[ZonasTuristicas.size()];
        this.contexto = contexto;
        this.ZonasTuristicas = ZonasTuristicas;

    }


    @Override
    public int getCount() {

        return ZonasTuristicas.size();
    }

    @Override
    public Object getItem(int position) {
        return ZonasTuristicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(contexto, R.layout.display_zonas,null);


        }

        ImageView imagen = (ImageView) convertView.findViewById(R.id.img_zona);
        TextView lblNombre = (TextView)convertView.findViewById(R.id.lbl_nombreZona);
        final CheckBox check =(CheckBox) convertView.findViewById(R.id.seleccion);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item[position]=check.isChecked();
            }
        });
        check.setChecked(item[position]);


        ZonasTuristicas zonasTuristicas = ZonasTuristicas.get(position);
        //new DownloadImageTask(imagen)
          //      .execute(zonasTuristicas.getImagen());

        Picasso.with(contexto).load(zonasTuristicas.getImagen()).into(imagen);

        lblNombre.setText(zonasTuristicas.getNombre());



        return convertView;

    }

}
