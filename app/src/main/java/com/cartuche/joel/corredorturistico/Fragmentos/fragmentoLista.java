package com.cartuche.joel.corredorturistico.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cartuche.joel.corredorturistico.Principal;
import com.cartuche.joel.corredorturistico.R;
import com.cartuche.joel.corredorturistico.adapter.adaptador;
import com.cartuche.joel.corredorturistico.adapter.adapterNuevo;
import com.cartuche.joel.corredorturistico.modelo.ZonasTuristicas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmentoLista.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmentoLista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentoLista extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listaData;
    private ProgressDialog progreso;
    private RequestQueue request;
    private adapterNuevo zonaAdapter;
    private ArrayList<ZonasTuristicas> zonasTuristicas;



    private String nombre;
    private OnFragmentInteractionListener mListener;

    public fragmentoLista() {
        // Required empty public constructor
    }
    public String getNombre() {
        return nombre;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentoLista.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentoLista newInstance(String param1, String param2) {
        fragmentoLista fragment = new fragmentoLista();
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
        View vista = inflater.inflate(R.layout.fragment_fragmento_lista, container, false);

        listaData = (ListView) vista.findViewById(R.id.list_dataLisPri);
        crearLista();

        listaData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nombre = zonasTuristicas.get(position).getNombre();
                //Intent intent = new Intent(getActivity(),MuestraMapa.class);
                //intent.putExtra("nombre", );
                //startActivity(intent);
                Fragment miFragmento = new MuestraMapa();
                Bundle args = new Bundle();
                args.putString("nombre", nombre);
                args.putString("imagen", zonasTuristicas.get(position).getImagen());
                args.putString("codigo", "mostrarParcial");
                miFragmento.setArguments(args);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,miFragmento).commit();
                //getSupportFragmentManager()



            }
        });

        return vista;
    }

    private void crearLista(){
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("cargando datos.....");
        progreso.show();
        request = Volley.newRequestQueue(getContext());
        zonasTuristicas = new ArrayList<>();

        String url = "https://sitiosversion1.herokuapp.com/sw/listaZonas/?format=json";

        final StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              serverResp.setText("String Response : "+ response.toString());
                        progreso.hide();

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i=0;i<array.length();i++){

                                JSONObject objecti = array.getJSONObject(i);
                                final JSONObject objectj = array.getJSONObject(i);
                                double lat = objecti.getDouble("latitud");
                                double lng = objecti.getDouble("longitud");
                                String nombre = objecti.getString("nombre");
                                String descripcion = objecti.getString("descripcion");
                                String imagen = objecti.getString("imagen");
                                int codigo = objecti.getInt("codigo");
                                ZonasTuristicas zonaNueva = new ZonasTuristicas(codigo,nombre,lat,lng,descripcion,imagen);


                                zonasTuristicas.add(zonaNueva);


                            }

                            zonaAdapter = new adapterNuevo(getActivity(),zonasTuristicas);
                            listaData.setAdapter(zonaAdapter);
                            zonaAdapter.notifyDataSetChanged();





                        } catch (JSONException e) {
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        //Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        request.add(jsonObjectRequest);
        ///fin carga

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
