package com.cartuche.joel.corredorturistico;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cartuche.joel.corredorturistico.Fragmentos.MuestraMapa;
import com.cartuche.joel.corredorturistico.Fragmentos.fragmenoMapas;
import com.cartuche.joel.corredorturistico.Fragmentos.fragmentoLista;
import com.cartuche.joel.corredorturistico.Interfaces.OnBackPressedListener;
import com.cartuche.joel.corredorturistico.Interfaces.interfaces;

import java.util.List;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,interfaces {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //cargamos el fragmento con el mapa

        Fragment miFragmento = new MuestraMapa();
        Bundle args = new Bundle();
        args.putString("codigo", "mostrarTodo");

        miFragmento.setArguments(args);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,miFragmento).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment miFragmento = null;
        boolean fragmetoSeleccionado = false;//

        if (id == R.id.opc_mapa) {
            miFragmento = new MuestraMapa();
            Bundle args = new Bundle();
            args.putString("codigo", "mostrarTodo");
            miFragmento.setArguments(args);
            fragmetoSeleccionado = true;

            // Handle the camera action
        }else if (id == R.id.nav_slideshow) {
            miFragmento = new fragmentoLista();
            fragmetoSeleccionado = true;

        }else if (id == R.id.opc_galeria) {
            miFragmento = new fragment_galeria();
            fragmetoSeleccionado = true;

        }
        if (fragmetoSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,miFragmento).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
