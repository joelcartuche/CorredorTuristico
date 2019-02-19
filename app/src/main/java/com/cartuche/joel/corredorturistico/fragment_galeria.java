package com.cartuche.joel.corredorturistico;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cartuche.joel.corredorturistico.adapter.adapterImagenes;
import com.cartuche.joel.corredorturistico.modelo.Imagenes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_galeria.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_galeria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_galeria extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ArrayList<Imagenes> listaImagenes;
    private GridView gridView;

    private OnFragmentInteractionListener mListener;
    private RequestQueue request;
    private ProgressDialog progreso;
    private String nombreRecibido;
    private adapterImagenes adaptador;

    public fragment_galeria() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_galeria.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_galeria newInstance(String param1, String param2) {
        fragment_galeria fragment = new fragment_galeria();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_fragment_galeria, container, false);
        gridView = (GridView) vista.findViewById(R.id.grid_data);

        cargarTodo();







        return vista;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //inicio


    private void atractivosCargar(){
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();


        request = Volley.newRequestQueue(getActivity());

        String url = "https://sitiosversion1.herokuapp.com/sw/BusquedaAtractivoNombre/?format=json&nombre="+nombreRecibido;


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              serverResp.setText("String Response : "+ response.toString());
                        progreso.hide();
                        JSONArray array = null;
                        try {
                            array = new JSONArray(response);
                            for (int i=0;i<array.length();i++){
                                JSONObject objecti = array.getJSONObject(i);

                                String imagen = objecti.getString("imagenSitio");
                                String nombre = objecti.getString("nombre");
                                JSONObject datozona = objecti.getJSONObject("zona");
                                String nombreZona = datozona.getString("nombre");

                                Imagenes img = new Imagenes(imagen,nombre,nombreZona);
                                listaImagenes.add(img);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progreso.hide();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        });


        request.add(jsonObjectRequest);

    }


    private void cargarTodo() {

        String[] rutas = new String[5];
        rutas[0] = "Parroquia Taquil";
        rutas[1] = "Parroquia Gualel";
        rutas[2] = "Parroquia Chantaco";
        rutas[3] = "Parroquia Chuquiribamba";
        rutas[4] = "Parroquia el Cisne";


        request = Volley.newRequestQueue(getActivity());

        for (int i = 0; i < rutas.length; i++) {
            nombreRecibido = rutas[i];
            atractivosCargar();


        }
        adaptador = new adapterImagenes(getActivity(),listaImagenes);
        gridView.setAdapter(adaptador);


    }

//fin
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
