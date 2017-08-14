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

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;

import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import static com.example.admin.observav1.ConfiguracionFragment._btn_rep;
import static com.example.admin.observav1.ConfiguracionFragment.l_Entrada;
import static com.example.admin.observav1.ConfiguracionFragment.l_Responsabilidad;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements MiComunicacion {


    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    private Toolbar appbar;
    public static boolean l_EdoEjer = true;     // c_mabg1
    Fragment finf, fconf, frepo, detf;
    FloatingActionButton fabGrafica;
    FloatingActionsMenu famb;
    String pro = "";
    public static Context g_contexto;
    static public float nDensidad;
    static public boolean lEstableta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        resolucion();
//        int width = this.getResources().getConfiguration().screenWidthDp;
//        int height = this.getResources().getConfiguration().screenHeightDp;
//        Toast.makeText(this,"W="+width+" H="+height,Toast.LENGTH_LONG).show();
//
        agregarFragmentos();
        ocultarFragmentos();

        fabGrafica = (FloatingActionButton) findViewById(R.id.accion_grafica);
        famb = (FloatingActionsMenu) findViewById(R.id.menu_fab);


        appbar = (Toolbar) findViewById(R.id.appbar); // Declarada en activity_main.xml
        setSupportActionBar(appbar);

        mBottomNav = (BottomNavigationView) getWindow().findViewById(R.id.navigation11);
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

    public void resolucion(){
        String sT1,sT2,sT3,sT4,sT5;
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();


        sT1 = "Ancho de la Pantalla " + Integer.toString(display.getWidth());
        sT2 = "Alto de la pantalla " + Integer.toString(display.getHeight());
        nDensidad = getResources().getDisplayMetrics().densityDpi;
        sT3 = "Densidad de la pantalla (dpi) " + nDensidad;

//        if(getResources().getBoolean(R.bool.isTablet)) {
//            lEstableta = true;
//        } else {
//            lEstableta = false;
//        }

//        TelephonyManager manager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
//            lEstableta = true;;// En una tablet no hay telefono ?
//        }else{
//            lEstableta = false;//Esta asignado un telefono
//        }
//      ________________________________________________________________________
        String ua=new WebView(this).getSettings().getUserAgentString();
        if(ua.contains("Mobile")){
            lEstableta = false;
        }else{
            lEstableta = true;
        }
//      ________________________________________________________________________

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        sT4 = "Escala " + Float.toString(getApplicationContext().getResources().getDisplayMetrics().density);
        sT5 = "";
        // buscando los pixeles a partir de dips con la densidad
        int dips = 200;
        float pixelBoton = 0;
        float scaleDensity = 0;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        sT5 = "Density "+metrics.densityDpi+ " DH="+DisplayMetrics.DENSITY_HIGH + " DM="+DisplayMetrics.DENSITY_MEDIUM +
              " DL="+DisplayMetrics.DENSITY_LOW;
        switch(metrics.densityDpi)
        {
            case DisplayMetrics.DENSITY_XXXHIGH: //HDPI
                sT5 = "Alta Densidad";
                scaleDensity = scale * 240;
                pixelBoton = dips * (scaleDensity / 240);
                break;
            case DisplayMetrics.DENSITY_XXHIGH: //MDPI
                sT5 = "mediana Densidad";
                scaleDensity = scale * 160;
                pixelBoton = dips * (scaleDensity / 160);
                break;

            case DisplayMetrics.DENSITY_XHIGH:  //LDPI
                sT5 = "baja Densidad";
                scaleDensity = scale * 120;
                pixelBoton = dips * (scaleDensity / 120);
                break;
        }
        sT5 = sT5+ " Es tableta ?"+lEstableta;
        Toast.makeText(this,sT1+" "+sT2+" "+sT3+" "+sT4+" "+sT5+" PixelBoton="+Float.toString(pixelBoton),Toast.LENGTH_LONG).show();
        Log.d(getClass().getSimpleName(), sT1+" "+sT2+" "+sT3+" "+sT4+" "+sT5+" PixelBoton="+pixelBoton);

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
//  Selecciona el fragmento ( Reportes / informes / Configuración )
    private void selectFragment(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_reporte:
                if (l_Responsabilidad && l_Entrada ) {
                    // muestraBotonesFlotante();
                    ocultarFragmentos();
                    muestraFragmento(frepo);
                    if (l_EdoEjer) {                  // c_mabg_1
                        _btn_rep[0].performClick();
                        l_EdoEjer = false;
                    }
                } else {
                    String cMensaje="";
                    if ( !l_Entrada ){
                        cMensaje = "Intruduzca usuario. ";
                    }
                    if ( !l_Responsabilidad){
                        cMensaje = cMensaje + "Seleccione responsabilidad.";
                    }
                    Toast.makeText(this, cMensaje, Toast.LENGTH_SHORT).show();
                }
                break;

            /*case R.id.menu_informe:
                ocultaBotonesFlotante();
                ocultarFragmentos();
                muestraFragmento(finf);
                break;*/

            case R.id.menu_configuracion:

                if (pro.equals("")) {
                    // ocultaBotonesFlotante();
                    ocultarFragmentos();
                    muestraFragmento(fconf);
                } else {
                    // ocultaBotonesFlotante();
                    ocultarFragmentos();
                    muestraFragmento(detf);
                }

                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());
    }

    private void updateToolbarText(CharSequence text) {
        if (appbar != null) {
            appbar.setTitle(text);
        }
    }

    public void agregarFragmentos() {
        finf = new InformeFragment();
        fconf = new ConfiguracionFragment();
        frepo = new ReporteFragment();
        detf = new DetalleSesionFragment();
        FragmentTransaction ftagregar = getSupportFragmentManager().beginTransaction();
        ftagregar.add(R.id.container, finf, finf.getTag());
        ftagregar.add(R.id.container, fconf, fconf.getTag());
        ftagregar.add(R.id.container, frepo, frepo.getTag());
        ftagregar.add(R.id.container, detf, detf.getTag());
        ftagregar.commit();

    }

    public void ocultarFragmentos() {

        FragmentTransaction ftocultar = getSupportFragmentManager().beginTransaction();
        ftocultar.hide(finf);
        ftocultar.hide(fconf);
        ftocultar.hide(frepo);
        ftocultar.hide(detf);
        ftocultar.commit();

    }

    public void muestraFragmento(Fragment f1) {
        FragmentTransaction ftm = getSupportFragmentManager().beginTransaction();
        ftm.show(f1);
        ftm.commit();

    }

//    public void ocultaBotonesFlotante() {
//        famb.setVisibility(View.GONE);
//    }
//
//    public void muestraBotonesFlotante() {
//        famb.setVisibility(View.VISIBLE);
//    }


    @Override
    public void miRespuesta(String data) {
        pro = data;
    }

    public Fragment retornaFra(int opc) {
        if (opc == 1)
            return fconf;
        else
            return detf;
    }


}
