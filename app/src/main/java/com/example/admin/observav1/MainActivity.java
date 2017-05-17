package com.example.admin.observav1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import static com.example.admin.observav1.ConfiguracionFragment._btn_rep;

public class MainActivity extends AppCompatActivity implements MiComunicacion{


    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    private Toolbar appbar;
    public static boolean l_EdoEjer=true;     // c_mabg1
    Fragment finf,fconf,frepo,detf;
    FloatingActionButton fabGrafica;
    FloatingActionsMenu famb;
    String pro="";
    public static Context g_contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agrgarFragmentos();
        ocultarFragmentos();

        fabGrafica=(FloatingActionButton)findViewById(R.id.accion_grafica);
        famb=(FloatingActionsMenu)findViewById(R.id.menu_fab);


        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        mBottomNav = (BottomNavigationView)getWindow().findViewById(R.id.navigation11);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 1);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);

        } else {
            selectedItem = mBottomNav.getMenu().getItem(1);
        }
        selectFragment(selectedItem);


        fabGrafica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), GraficaActivity.class);
                startActivity(i);
            }
        });




    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_appbar, menu);

        //return super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_buscar:
               Toast.makeText(this, "prueba boton1", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_buscar_uno:
                //Toast.makeText(this, "prueba buscar", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }

    private void selectFragment(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_reporte:
                //muetraBotonesFlotante();
                ocultarFragmentos();
                muestraFragmento(frepo);
                if ( l_EdoEjer ) {                  // c_mabg_1
                    _btn_rep[0].performClick();
                    l_EdoEjer = false;
                }
                break;

            /*case R.id.menu_informe:
                ocultaBotonesFlotante();
                ocultarFragmentos();
                muestraFragmento(finf);
                break;*/

            case R.id.menu_configuracion:

                if (pro.equals("")) {
                    ocultaBotonesFlotante();
                    ocultarFragmentos();
                    muestraFragmento(fconf);
                }else {
                    ocultaBotonesFlotante();
                    ocultarFragmentos();
                    muestraFragmento(detf);
                }

                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());
    }

    private void updateToolbarText(CharSequence text) {
        if ( appbar != null) {
            appbar.setTitle(text);
        }
    }

    public void agrgarFragmentos(){
        finf = new InformeFragment();
        fconf = new ConfiguracionFragment();
        frepo = new ReporteFragment();
        detf = new DetalleSesionFragment();
        FragmentTransaction ftagregar = getSupportFragmentManager().beginTransaction();
        ftagregar.add(R.id.container, finf, finf.getTag());
        ftagregar.add(R.id.container, fconf,fconf.getTag());
        ftagregar.add(R.id.container, frepo,frepo.getTag());
        ftagregar.add(R.id.container, detf,detf.getTag());
        ftagregar.commit();
        
    }
    public void ocultarFragmentos(){

        FragmentTransaction ftocultar = getSupportFragmentManager().beginTransaction();
        ftocultar.hide(finf);
        ftocultar.hide(fconf);
        ftocultar.hide(frepo);
        ftocultar.hide(detf);
        ftocultar.commit();

    }
    public void muestraFragmento(Fragment f1){
        FragmentTransaction ftm = getSupportFragmentManager().beginTransaction();
        ftm.show(f1);
        ftm.commit();

    }
    public void ocultaBotonesFlotante(){
        famb.setVisibility(View.GONE);
    }
    public void muetraBotonesFlotante(){
       famb.setVisibility(View.VISIBLE);
    }


    @Override
    public void miRespuesta(String data) {
       pro=data;
    }

    public Fragment retornaFra(int opc){
        if (opc==1)
            return fconf;
        else
            return detf;
    }

    public void cierra_filtro(View btn) {
        ConfiguracionFragment cf = new ConfiguracionFragment();
        cf.cierra_catalogo();
    }
}
