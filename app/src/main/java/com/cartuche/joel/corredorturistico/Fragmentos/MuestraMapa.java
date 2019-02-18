package com.cartuche.joel.corredorturistico.Fragmentos;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cartuche.joel.corredorturistico.Interfaces.BaseBackPressedListener;
import com.cartuche.joel.corredorturistico.Interfaces.OnBackPressedListener;
import com.cartuche.joel.corredorturistico.Principal;
import com.cartuche.joel.corredorturistico.R;
import com.cartuche.joel.corredorturistico.adapter.CustomInfoWindowAdapter;
import com.cartuche.joel.corredorturistico.adapter.adaptador;
import com.cartuche.joel.corredorturistico.adapter.adaptadorCelebraciones;
import com.cartuche.joel.corredorturistico.modelo.Celebraciones;
import com.cartuche.joel.corredorturistico.modelo.ZonasTuristicas;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MuestraMapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MuestraMapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MuestraMapa extends Fragment implements OnMapReadyCallback,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap map;
    private RequestQueue request;
    private ProgressDialog progreso;
    private FloatingActionButton seleccionarSitios,vistaSatelite;
    private View rootView;
    private String nombreRecibido;

    private OnFragmentInteractionListener mListener;

    private  ArrayList<ZonasTuristicas> zonasTuristicas;
    //instaciamos el materia adapter
    private adaptador zonaAdapter ;

    private com.getbase.floatingactionbutton.FloatingActionButton vistaSateliteMenu,vistaNormalMenu,celebraciones;



    private FloatingActionsMenu botonesMenu;

    private ListView list;
    private View fab;
    private NavigationView detalle;


    public MuestraMapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MuestraMapa.
     */
    // TODO: Rename and change types and number of parameters
    public static MuestraMapa newInstance(String param1, String param2) {
        MuestraMapa fragment = new MuestraMapa();
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
        // Inflate the layout for this fragment}
        rootView=inflater.inflate(R.layout.fragment_muestra_mapa, container, false);







        //////////////////////////////////////////////////////////////////////////////////////////7
        //inicio control del menu boton

        vistaSateliteMenu = (com.getbase.floatingactionbutton.FloatingActionButton) rootView.findViewById(R.id.btn_vistaSatelite2);
        vistaSateliteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        vistaNormalMenu= (com.getbase.floatingactionbutton.FloatingActionButton) rootView.findViewById(R.id.btn_vistaNormal);
        celebraciones= (com.getbase.floatingactionbutton.FloatingActionButton) rootView.findViewById(R.id.btn_celebraciones);

        vistaNormalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });





        //fin del control
        //////////////////////////////////////////////////////////////////////////////////////////7




        return rootView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment= (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }


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

    //json para recojer la ruta
    JSONObject jso;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        //inicio verificacion permisos

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }




            return;
        }

        //fin verificacion permisos





        //LatLng lat= new LatLng(-3.99313, -79.2042236);
        //map.addMarker(new MarkerOptions().position(lat).title("loja"));
        //map.moveCamera(CameraUpdateFactory.zoomBy(14));
        map.setMyLocationEnabled(true);

        String codigo= getArguments().getString("codigo");

        if(codigo.equalsIgnoreCase("mostrarParcial")){
            nombreRecibido= getArguments().getString("nombre");
            cargarSitios();
            celebraciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargarCelebracionesParcial();
                }
            });
        }else{
            cargarTodo();
            celebraciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargarCelebracionesTodo();
                }
            });
        }

    }


    private void cargarCelebracionesParcial(){
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();
        request = Volley.newRequestQueue(getActivity());

        String url = "https://sitiosversion1.herokuapp.com/sw/BusquedaCelebracionesNombre/?format=json&nombre="+nombreRecibido;


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              serverResp.setText("String Response : "+ response.toString());
                        progreso.hide();
                        JSONArray array = null;
                        ArrayList<Celebraciones> listaCelebraciones = new ArrayList<>();



                        try {
                            array = new JSONArray(response);
                            for (int i=0;i<array.length();i++){


                                JSONObject objecti = array.getJSONObject(i);
                                String codigo = objecti.getString("codigo");
                                String nombre = objecti.getString("nombre");
                                String fecha = objecti.getString("fecha");
                                JSONObject zona = objecti.getJSONObject("zona");
                                String nombreZona = zona.getString("nombre");

                                Celebraciones celebracionAux = new Celebraciones(codigo,nombre,fecha,nombreZona);
                                listaCelebraciones.add(celebracionAux);

                            }

                            final Dialog dlgCelebraciones = new Dialog(getContext());
                            dlgCelebraciones.setContentView(R.layout.dlg_celebraciones);

                            //control de los inputs del dialogo


                            final ListView dataCelebraciones = (ListView) dlgCelebraciones.findViewById(R.id.list_celebraciones);
                            adaptadorCelebraciones adaptadorCelebracionesAux = new adaptadorCelebraciones(getActivity(),listaCelebraciones);
                            dataCelebraciones.setAdapter(adaptadorCelebracionesAux);
                            adaptadorCelebracionesAux.notifyDataSetChanged();

                            dlgCelebraciones.show();





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        request.add(jsonObjectRequest);


    }

    private void cargarSitios(){
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();
        request = Volley.newRequestQueue(getActivity());
        zonasTuristicas = new ArrayList<>();
        String imagenRecibido= getArguments().getString("imagen");

        String url = "https://sitiosversion1.herokuapp.com/sw/BusquedaRutasNombre/?format=json&nombre="+nombreRecibido;


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              serverResp.setText("String Response : "+ response.toString());
                        progreso.hide();

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i=0;i<array.length()-1;i++){

                                JSONObject objecti = array.getJSONObject(i);
                                final JSONObject objectj = array.getJSONObject(i);
                                double lat0 = objecti.getDouble("latitud");
                                double lng0 = objecti.getDouble("longitud");

                                int codigo = objecti.getInt("codigo");

                                JSONObject objecti1 = array.getJSONObject(i+1);
                                final JSONObject objectj1 = array.getJSONObject(i);
                                double lat1 = objecti1.getDouble("latitud");
                                double lng1 = objecti1.getDouble("longitud");

                                int codigo1 = objecti1.getInt("codigo");



                                if (i==0){
                                    LatLng latLng= new LatLng(lat0,lng0);
                                    MarkerOptions option = new MarkerOptions();


                                    option.position(latLng).title("INICIO");
                                    map.addMarker(option);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.5f));


                                }

                                if(i== array.length()-2){
                                    LatLng latLng= new LatLng(lat1,lng1);
                                    MarkerOptions option = new MarkerOptions();
                                    option.position(latLng).title("FIN");
                                    map.addMarker(option);

                                }


                                map.addPolyline(new PolylineOptions()
                                        .add(new LatLng(lat0, lng0), new LatLng(lat1, lng1))
                                        .width(5)
                                        .color(Color.RED));


                            }



                        } catch (JSONException e) {
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
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
        hotelesRestaurantesCargar();

    }

    private void hotelesRestaurantesCargar(){
        /*progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();*/
        request = Volley.newRequestQueue(getActivity());

        String url = "https://sitiosversion1.herokuapp.com/sw/BusquedaHostalesNombre/?format=json&nombre="+nombreRecibido;


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
                                double lat = objecti.getDouble("latitud");
                                double lng = objecti.getDouble("longitud");

                                if(objecti.getString("identificador").equalsIgnoreCase("restaurante")){
                                    LatLng latLng= new LatLng(lat,lng);
                                    MarkerOptions option = new MarkerOptions();
                                    option.title(objecti.getString("nombre")).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurante)).position(latLng);
                                    //option.title(objecti.getString("nombre")).icon(bitmapDescriptorFromVector(getActivity(),R.mipmap.ic_resta)).position(latLng);
                                    map.addMarker(option);
                                }
                                if(objecti.getString("identificador").equalsIgnoreCase("Alojamiento")){
                                    LatLng latLng= new LatLng(lat,lng);
                                    MarkerOptions option = new MarkerOptions();
                                    option.title(objecti.getString("nombre")).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hotel)).position(latLng);
                                    //option.title(objecti.getString("nombre")).icon(bitmapDescriptorFromVector(getActivity(),R.mipmap.ic_resta)).position(latLng);
                                    map.addMarker(option);
                                }
                                if(objecti.getString("identificador").equalsIgnoreCase("mercado")){
                                    LatLng latLng= new LatLng(lat,lng);
                                    MarkerOptions option = new MarkerOptions();
                                    option.title(objecti.getString("nombre")).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mercado)).position(latLng);
                                    //option.title(objecti.getString("nombre")).icon(bitmapDescriptorFromVector(getActivity(),R.mipmap.ic_resta)).position(latLng);
                                    map.addMarker(option);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

        atractivosCargar();
    }


    private void atractivosCargar(){

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
                                double lat = objecti.getDouble("latitud");
                                double lng = objecti.getDouble("longitud");
                                String imagen = objecti.getString("imagenSitio");
                                String imagenAdvertencia = objecti.getString("imagenAdvertencia");
                                String nombre = objecti.getString("nombre");


                                //.title(objecti.getString("nombre"))
                                LatLng latLng= new LatLng(lat,lng);
                                MarkerOptions option = new MarkerOptions();
                                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atractivos)).position(latLng);
                                option.title(objecti.getString("nombre")).icon(bitmapDescriptorFromVector(getActivity(),R.mipmap.ic_resta)).position(latLng);
                                option.infoWindowAnchor(14f,14f);


                                map.addMarker(option);
                                map.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity()),nombre,"dd",imagen,imagenAdvertencia));


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    private void cargarTodo(){

        String [] rutas = new String[5];
        rutas[0]="Parroquia Taquil";
        rutas[1]="Parroquia Gualel";
        rutas[2]="Parroquia Chantaco";
        rutas[3]="Parroquia Chuquiribamba";
        rutas[4]="Parroquia el Cisne";


        request = Volley.newRequestQueue(getActivity());
        zonasTuristicas = new ArrayList<>();

        for (int i =0;i<rutas.length;i++){
            nombreRecibido = rutas[i];
            cargarSitios();


        }
        progreso.hide();



        /*
        String imagenRecibido= getArguments().getString("imagen");

        String url = "https://sitiosversion1.herokuapp.com/sw/listaRutas/?format=json";


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              serverResp.setText("String Response : "+ response.toString());
                        //progreso.hide();

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i=0;i<array.length()-1;i++){

                                JSONObject objecti = array.getJSONObject(i);
                                final JSONObject objectj = array.getJSONObject(i);
                                double lat0 = objecti.getDouble("latitud");
                                double lng0 = objecti.getDouble("longitud");

                                int codigo = objecti.getInt("codigo");

                                JSONObject objecti1 = array.getJSONObject(i+1);
                                final JSONObject objectj1 = array.getJSONObject(i);
                                double lat1 = objecti1.getDouble("latitud");
                                double lng1 = objecti1.getDouble("longitud");

                                int codigo1 = objecti1.getInt("codigo");



                                if (i==0){
                                    LatLng latLng= new LatLng(lat0,lng0);
                                    MarkerOptions option = new MarkerOptions();


                                    option.position(latLng).title("INICIO");
                                    map.addMarker(option);
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.5f));


                                }

                                if(i== array.length()-2){
                                    LatLng latLng= new LatLng(lat1,lng1);
                                    MarkerOptions option = new MarkerOptions();
                                    option.position(latLng).title("FIN");
                                    map.addMarker(option);

                                }


                                map.addPolyline(new PolylineOptions()
                                        .add(new LatLng(lat0, lng0), new LatLng(lat1, lng1))
                                        .width(5)
                                        .color(Color.RED));


                            }



                        } catch (JSONException e) {
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        request.add(jsonObjectRequest);
        hotelesRestaurantesCargarTodo();
*/
    }






    public void cargarCelebracionesTodo(){
        progreso = new ProgressDialog(getActivity());
        progreso.setMessage("cargando datos.....");
        progreso.show();
        request = Volley.newRequestQueue(getActivity());

        String url = "https://sitiosversion1.herokuapp.com/sw/listaCelebraciones/?format=json";


        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              serverResp.setText("String Response : "+ response.toString());
                        progreso.hide();
                        JSONArray array = null;
                        ArrayList<Celebraciones> listaCelebraciones = new ArrayList<>();



                        try {
                            array = new JSONArray(response);
                            for (int i=0;i<array.length();i++){


                                JSONObject objecti = array.getJSONObject(i);
                                String codigo = objecti.getString("codigo");
                                String nombre = objecti.getString("nombre");
                                String fecha = objecti.getString("fecha");
                                JSONObject zona = objecti.getJSONObject("zona");
                                String nombreZona = zona.getString("nombre");






                                Celebraciones celebracionAux = new Celebraciones(codigo,nombre,fecha,nombreZona);
                                listaCelebraciones.add(celebracionAux);

                            }

                            final Dialog dlgCelebraciones = new Dialog(getContext());
                            dlgCelebraciones.setContentView(R.layout.dlg_celebraciones);

                            //control de los inputs del dialogo


                            final ListView dataCelebraciones = (ListView) dlgCelebraciones.findViewById(R.id.list_celebraciones);
                            adaptadorCelebraciones adaptadorCelebracionesAux = new adaptadorCelebraciones(getActivity(),listaCelebraciones);
                            dataCelebraciones.setAdapter(adaptadorCelebracionesAux);
                            adaptadorCelebracionesAux.notifyDataSetChanged();

                            dlgCelebraciones.show();





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        request.add(jsonObjectRequest);


    }



    //trazado de la ruta en el mapa


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




    @Override
    public void onClick(View v) {

    }

}
