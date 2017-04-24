package com.example.admin.observav1;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.admin.observav1.MainActivity.g_contexto;

public class ConfiguracionFragment extends Fragment {
    Button botonSe2,botonCancelar,botonCerrarSesion,boton_sel1;
    EditText  g_usr,g_cve;
    public c_php_jason o_ldap = new c_php_jason();
    public c_usuario o_usuario;
    ListView lv_respo;
    String  v_Responsa;
    TextView  _tv_rol, otxt_fecha;

    public String[] a_reportes = new String[10];
    public String[] idReportGe = new String[10];
    public LinearLayout _ly_reporte , _ly_agrupa , _ly_filtros;
    public LinearLayout.LayoutParams lv_param;

    public static Button[] _btn_rep;
    public static Button[] _btn_filtro;
    public static Button[] _btn_agrupa;

    private _lv_Cata[] _lv_filtro;
    private LinearLayout _ly_Cata;

    private c_valor observatorio = new c_valor();
    public String[] a_Agrupa_Des , a_Filtros, a_Agrupa_Campo , a_Cam_Json;
    public String g_reporte="1";
    private int nCatalogo_Visible=-1;
    public static String g_agrupa;

    ImageButton botongene;
    private ObservableScrollView scrollView1 = null;
    private ObservableScrollView scrollView2 = null;
    private HorizontalScrollView HsV_B = null;
    private HorizontalScrollView HsV_D = null;
    private Integer[] a_Ancho , a_Alto , a_Tamano;
    public int nCol_Fijas=-1;
    ArrayAdapter adp=null;
    LinearLayout _ly_tbl;
     ArrayList<String> a_id_responsabilidad;
    private String[] aFilDat;
    private sel_filtro[] _sel_filtro;
    public Boolean l_Entrada = false , l_Responsabilidad = false;

    LinearLayout linerlista;
    MenuItem fav;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lv_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        setHasOptionsMenu(true);
        /*Button menuboton =(Button)getActivity().findViewById(R.id.action_buscar);
        menuboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "prueba 124", Toast.LENGTH_SHORT).show();
            }
        });*/
        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_appbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar:
               // Toast.makeText(getActivity(), "action buscar", Toast.LENGTH_SHORT).show();
                ReporteFragment fr1=new ReporteFragment();
                fr1.pruebaBoton();
                break;

            case R.id.action_buscar_uno:
                Toast.makeText(getActivity(), "action bsucar uno", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;

    }

   @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
       // Estas dos líneas deben ir por que si no marca error en  urlConnection.connect();
       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
       botonCancelar=(Button)getActivity().findViewById(R.id.botonCancelar1);
       botonCerrarSesion=(Button)getActivity().findViewById(R.id.botomCS);

       lv_respo = (ListView)getActivity().findViewById(R.id.listViewResponsabilidades);
       g_usr = (EditText)getActivity().findViewById(R.id.oet_Usuario);
       g_cve = (EditText)getActivity().findViewById(R.id.oet_Clave);
       otxt_fecha = (TextView)getActivity().findViewById(R.id.txt_consulta);
       _ly_agrupa = (LinearLayout)getActivity().findViewById(R.id._ly_agrupa);
       _ly_filtros = (LinearLayout)getActivity().findViewById(R.id._ly_filtro);
       _ly_tbl = (LinearLayout)getActivity().findViewById(R.id.tbl_Consulta);

       linerlista =(LinearLayout)getActivity().findViewById(R.id.layoutlista);
       linerlista.setVisibility(View.GONE);




       botonSe2=(Button)getActivity().findViewById(R.id.botonse);
       botonSe2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // Toast.makeText(getActivity(), "este es el boton", Toast.LENGTH_SHORT).show();
               String v_url , v_par ;


               g_contexto  = getActivity();
               v_url       = "activaSesion.php";
               v_par       = "?usuario="+g_usr.getText().toString()+"&clave="+g_cve.getText();

               final TextView _tv_usu = (TextView)getActivity().findViewById(R.id._textUsu);
               final TextView _tv_dur = (TextView)getActivity().findViewById(R.id._textUr);

               ArrayList<String> a_responsa = new ArrayList<>();
               a_id_responsabilidad = new ArrayList<>();
               o_usuario = new c_usuario();
               o_usuario.c_usuario(g_usr.getText().toString(),g_cve.getText().toString());
               if ( o_ldap.m_php_ldap(v_url,v_par,o_usuario)){
                   //Log.d("regreso de ","m_php_ldap");
                   //Log.i("Entro el ldap","valor "+o_ldap.m_php_ldap(v_url,v_par,o_usuario));
                   if ( o_ldap.m_php_jason("consulta_responsabilidades.php","?usuario="+g_usr.getText().toString())){

                       try {
                           JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                           for ( int i=0 ; i < data_array.length() ; i++ ){
                               JSONObject obj = new JSONObject(data_array.get(i).toString());
                               a_responsa.add(obj.getString("descripcion"));
                               a_id_responsabilidad.add(obj.getString("responsabilidad_id"));
                           }
                           adp = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, a_responsa);
                           lv_respo.setAdapter(adp);

                           _tv_usu.setText(o_usuario.getF_nombre());
                           _tv_dur.setText(o_usuario.getF_dur());

                       }catch (JSONException e){
                           // e.printStackTrace();
                           Log.w("Catch JSOn","responsabilidad");
                       }
                   }else{

                   }
               }else{
                   //fgResponsabilidad.setVisibility(View.INVISIBLE);
                   Toast.makeText(getActivity(),"Datos usuario incorrectos",Toast.LENGTH_SHORT).show();
               }

               // Ocultar teclado virtual
               InputMethodManager imm =
                       (InputMethodManager)getActivity(). getSystemService(INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
               linerlista.setVisibility(View.VISIBLE);
           }
       });

       lv_respo = (ListView)getActivity().findViewById(R.id.listViewResponsabilidades);
       _tv_rol = (TextView)getActivity().findViewById(R.id._textRol);
       lv_respo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               v_Responsa = lv_respo.getItemAtPosition(position).toString();
               o_usuario.setF_responsabilidad(v_Responsa);
               o_usuario.setF_id_responsabilidad(a_id_responsabilidad.get(position));

               _tv_rol.setText(v_Responsa);

               MainActivity act=(MainActivity)getActivity();
               act.ocultarFragmentos();
               act.muestraFragmento(act.retornaFra(2));
               MiComunicacion mc1;
               mc1=(MiComunicacion)getActivity();
               mc1.miRespuesta(v_Responsa);

               llena_pantalla_genera(o_usuario.getF_usuario(),o_ldap);

           }
       });


       botongene=(ImageButton)getActivity().findViewById(R.id.boton_consulta_informacion);
       botongene.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                       R.style.AppTheme_Dark_Dialog);
               progressDialog.setIndeterminate(true);
               progressDialog.setMessage("Procesando...");
               progressDialog.show();


               // TODO: Implementacion del progressDialog.
               new android.os.Handler().postDelayed(
                       new Runnable() {
                           public void run() {

                               String cFiltro;
                               DecimalFormat formato = new DecimalFormat("###,###,###,##0.00");// 04 abril 2017
                               cFiltro=trae_filtros();
                               String cPar = "?fecha="          + observatorio.getFecha()    +
                                             "&tipo="           + g_agrupa                   +
                                             "&idReportGenera=" + g_reporte                  +
                                             "&responsabilidad="+ o_usuario.getF_id_responsabilidad()+
                                             cFiltro+
                                             "&anio="           + observatorio.getPeriodo();

                               //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                               View v_tbl = LayoutInflater.from(getActivity()).inflate(R.layout.tbl_consulta,null);
                               _ly_tbl.addView(v_tbl);
// ===================================================================================================================
// ===================================================================================================================
                               scrollView1 = (ObservableScrollView)getActivity().findViewById(R.id.SV_C);
                               // el this se puede poner por que se implemento la interface implements ScrollViewListener
                               scrollView1.setScrollViewListener(new ScrollViewListener() {
                                   @Override
                                   public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                                       if(scrollView == scrollView1) {
                                           scrollView2.scrollTo(x, y);
                                       } else if(scrollView == scrollView2) {
                                           scrollView1.scrollTo(x, y);
                                       }

                                   }
                               });
                               scrollView2 = (ObservableScrollView)getActivity().findViewById(R.id.SV_D);
                               scrollView2.setScrollViewListener(new ScrollViewListener() {
                                   @Override
                                   public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                                       if(scrollView == scrollView1) {
                                           scrollView2.scrollTo(x, y);
                                       } else if(scrollView == scrollView2) {
                                           scrollView1.scrollTo(x, y);
                                       }

                                   }
                               });

                               HsV_B       = (HorizontalScrollView)getActivity().findViewById(R.id.HSV_B);
                               HsV_D       = (HorizontalScrollView)getActivity().findViewById(R.id.HSV_D) ;
                               syncScrolls(HsV_B, HsV_D);
                               syncScrolls(HsV_D, HsV_B);
// =================================================== Leo el pres_reports_column para saber que columnas necesito traer
                               llena_encabezados();
// ========================================================================================================
                              // Log.i("Genera consulta","genera_consultaResp.php"+cPar);

                               if (o_ldap.m_php_jason("genera_consultaResp.php",cPar)){

                                   try {
                                       TableLayout _tab_C , _tab_D ;
                                       TableLayout.LayoutParams layOutRenglon;

                                       a_Cam_Json[0] = g_agrupa;

                                       _tab_C = (TableLayout)getActivity().findViewById(R.id._TablaC);
                                       _tab_D= (TableLayout)getActivity().findViewById(R.id._TablaD);

                                       _tab_C.removeAllViews();
                                       _tab_D.removeAllViews();

                                       JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                                       for ( int i=0 ; i < data_array.length() ; i++ ){    // Renglones

                                           TableRow fila_C = new TableRow(getActivity());
                                           TableRow fila_D = new TableRow(getActivity());
                                           TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                                           fila_C.setLayoutParams(layoutFila);
                                           fila_D.setLayoutParams(layoutFila);

                                           //backround para las filas del reporte
                                           if ((i+1)%2==0){

                                           }else {
                                               fila_C.setBackgroundResource(R.color.grisine);
                                               fila_D.setBackgroundResource(R.color.grisine);
                                           }


                                           JSONObject obj = new JSONObject(data_array.get(i).toString());
                                           //Log.e("===","=====================================================================");
                                           for ( int j=0 ; j < a_Cam_Json.length ; j++ ) {       // Columnas
                                               TextView texto = new TextView(getActivity());
                                               texto.setText(obj.getString(a_Cam_Json[j]));
                                               texto.setWidth(  a_Ancho[j] );
                                               texto.setHeight( a_Alto[j] +30);
                                               texto.setTextSize( a_Tamano[j]-2);
                                               //Log.e(a_Cam_Json[j],obj.getString(a_Cam_Json[j]));
                                               if ( j<=nCol_Fijas ){
                                                   fila_C.addView(texto);
                                               }else{
                                                   if ( c_fun.la_cadena_es_numero(texto.getText().toString())) { // 05 abril 2017
                                                       texto.setGravity(Gravity.RIGHT);
                                                       String Numero = formato.format(Double.parseDouble(texto.getText().toString()));
                                                       Numero = Numero.replace(",", "*");
                                                       Numero = Numero.replace(".", ",");
                                                       Numero = Numero.replace("*", ".");
                                                       texto.setText(Numero);
                                                   }else{
                                                       texto.setGravity(Gravity.LEFT);
                                                   }
                                                   fila_D.addView(texto);
                                               }

                                           }
                                           _tab_C.addView(fila_C);
                                           _tab_D.addView(fila_D);
                                           _ly_tbl.bringToFront();
                                           //TODO
                                           // _lv_filtro[nCatalogo_Visible]._lv.setVisibility(View.GONE);

                                       }
                                       oculta_filtro_visible();
                                       //this.lv_respo.setAdapter(new ArrayAdapter(this,R.layout.responsabilidades_item,a_responsa));
                                       Toast.makeText(getActivity(),"Termino consulta..",Toast.LENGTH_SHORT).show();
                                   }catch (JSONException e){
                                       //Log.e("***","=====================================================================\n");
                                       //e.printStackTrace();
                                       Log.w("Catch JSOn","boton_consulta_información"+e);
                                       // Log.e("****","=====================================================================\n");
                                   }
                               }else{
                                   Toast.makeText(getActivity(),"No hay información "+cPar,Toast.LENGTH_SHORT).show();
                               }
                               cierra_filtro();
                               progressDialog.dismiss();
                           }
                       }, 7500);


           }
       });

       // Todo: boton de cancela la session
       botonCancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       g_usr.setText("");
                       g_cve.setText("");
                       if (adp!=null)
                           adp.clear();
                       if(getActivity().findViewById(R.id.oet_Clave).isFocusable())
                           getActivity().findViewById(R.id.oet_Usuario).requestFocus();
                   }
               });
           }
       });

       // Todo: boton de cerrar la session
       botonCerrarSesion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               g_usr.setText("");
               g_cve.setText("");
               adp.clear();
               if(getActivity().findViewById(R.id.oet_Clave).isFocusable())
                   getActivity().findViewById(R.id.oet_Usuario).requestFocus();
               MiComunicacion mc1s;
               mc1s=(MiComunicacion)getActivity();
               mc1s.miRespuesta("");

               _ly_reporte.removeAllViews();
               _ly_agrupa.removeAllViews();
               _ly_filtros.removeAllViews();
               _ly_tbl.removeAllViews();

               observatorio.setFecha("");
               observatorio.setAnio("");
               observatorio.setPeriodo("");
               otxt_fecha.setText("Fecha");

               MainActivity act1=(MainActivity)getActivity();
               act1.ocultarFragmentos();
               act1.muestraFragmento(act1.retornaFra(1));


           }
       });


    }


    void llena_pantalla_genera(String cUsu,c_php_jason o_ldap){

        String cSql;
        Integer nRep=0;
        Boolean lPaso=false;


        cSql =  "?sql=select-nombre,idreportgenera-from-pres_reports-a-where-a.fi_estatus=1-and-" +
                "-strpos(-(-select-reportes-from-usuarios-where-usuario_id='" + cUsu +"'-)-,a.reportes)>0--order-by-idreportgenera";


        if ( o_ldap.m_php_jason("genera_json.php",cSql) ){
            //Toast.makeText(g_contexto,cSql,Toast.LENGTH_LONG).show();
            try {
                JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                nRep = data_array.length();
                for ( int i=0 ; i < nRep ; i++ ){
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    a_reportes[i] = (obj.getString("nombre"));
                    idReportGe[i] = (obj.getString("idreportgenera"));
                    //Log.d("Nombre:",a_reportes[i]);
                   // Toast.makeText(getActivity(),a_reportes[i],Toast.LENGTH_SHORT).show();
                }
                lPaso=true;
                if ( lPaso ){
                    _btn_rep = new Button[nRep];
                    _ly_reporte         = (LinearLayout)getActivity().findViewById(R.id._ly_reportes);
                    _ly_reporte.removeAllViews();
                    for (int i=0 ; i < nRep ; i++){
                        adiciona_boton(_ly_reporte,i,lv_param,"R",a_reportes);
                    }
                }

            }catch (JSONException e){
                //e.printStackTrace();
                Log.w("Catch JSOn","reportes");
            }
        }else{
            Toast.makeText(getActivity(),"El usuario no tiene asignado reportes",Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(g_contexto,cSql,Toast.LENGTH_SHORT).show();
    }

    public void adiciona_boton(LinearLayout _ly , int pos , LinearLayout.LayoutParams lv_param ,
                               final String cFiltro, String[] aTitulo) {
            Button btn_;
            String cVal;
            Button btn1;
            int nPos;

            lv_param.width = 160;
            //lv_param.height = 63;
            lv_param.height = 63;
            lv_param.rightMargin = 0;
            lv_param.topMargin = 0;
            nPos = 0;
            switch (cFiltro){
                case "R":{
                    nPos = pos;
                    break;
                }
                case "G":{
                    nPos = pos + _btn_rep.length;
                    break;
                }
                case "F":{
                    //Log.v("Rep long=",Integer.toString(_btn_rep.length));
                    //Log.v("Rep long=",Integer.toString(_btn_filtro.length));
                    nPos = pos + _btn_rep.length + _btn_filtro.length;
                    break;
                }
            }
            //nPos = pos; // (cFiltro == "R") ? pos : pos ;
            btn_ = new Button(getActivity());
            btn_.setId(nPos);
            final int id_ = btn_.getId();
            cVal = aTitulo[pos]; // a_Cves3.get(pos).toString();
            //Log.d("Titulo=",cVal);

            btn_.setText(cVal);
            btn_.setTextSize((float) 10);
            //Log.d("Texto",btn_.getText().toString());

            //_ly.addView(btn_);
            _ly.addView(btn_, lv_param);

            btn1 = (Button)getActivity().findViewById(id_);
            if (cFiltro == "R") {   // Reportes
                _btn_rep[pos] = btn1;
    //            tablas[pos].setcTabla(aVal[2]);
            } else if (cFiltro == "F"){ // Filtros
                _btn_filtro[pos] = btn1;
            }else{                      // Grupos
                _btn_agrupa[pos] = btn1;
            }
            final String cTxt = cVal;
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(final View view ) {
                    if ( cFiltro=="R" ) {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Procesando...");
                        progressDialog.show();

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        boton_reporte_click(view);
                                        progressDialog.dismiss();
                                    }
                                }, 3000);

                    }
                    else if( cFiltro=="F")
                        boton_filtro_click(view);
                    else
                        boton_agrupa_click(view);

                }
            });


    }

    //===================================================================================================
// Al dar click en algun boton de reportes, tiene que actualizar los botones que se veran en filtros y agrupaciones
    public void boton_reporte_click(View btn) {
        //Toast.makeText(this,"Reporte"+ btn.getX()+","+btn.getY()+","+btn.getId(),Toast.LENGTH_SHORT).show();
        Integer nFil, nGrp = 0;


        String cSql, cW;
        // =====================================================================================================
        //      *******************************************************************************

        cSql = "?sql=select-field,descripcion,tableCatalogo,fieldDescrip,tablemain-from-pres_reports_rows-" +
                "where-idreportGenera=" + idReportGe[btn.getId()] + "-order-by-idrow";
        // Toast.makeText(this, cSql , Toast.LENGTH_SHORT).show();

        if (o_ldap.m_php_jason("genera_json.php", cSql)) {
            try {
                JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                nGrp = data_array.length();
                a_Agrupa_Des = new String[nGrp];
                a_Agrupa_Campo = new String[nGrp];
                for (int i = 0; i < nGrp; i++) {
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    a_Agrupa_Des[i] = (obj.getString("descripcion"));
                    a_Agrupa_Campo[i] = (obj.getString("field"));
                    //idReportGe[i] = (obj.getString("idreportgenera"));
                    observatorio.setTabla(obj.getString("tablemain"));
                }


                _btn_agrupa = new Button[nGrp];
                _ly_agrupa = (LinearLayout)getActivity(). findViewById(R.id._ly_agrupa);
                _ly_agrupa.removeAllViews();
                for (int i = 0; i < nGrp; i++) {
                    adiciona_boton(_ly_agrupa, i, lv_param, "G", a_Agrupa_Des);
                }

            } catch (JSONException e) {
                //e.printStackTrace();
                Log.w("Catch JSOn", "Agrupaciones");
            }
        } else {
            Toast.makeText(g_contexto, "Regresa falso " + cSql, Toast.LENGTH_SHORT).show();
            return;
        }
//      ********************************************************************************************
        //  Voy por la última fecha del reporte.
        TextView otxt_fecha = (TextView) getActivity().findViewById(R.id.txt_consulta);
        observatorio.setIdreporte(idReportGe[btn.getId()]);
        cSql = "?sql=select-fc_nombre,fc_descripcion,anio-from-archivos-where-" +
                "idreportgenera=" + observatorio.getIdreporte() + "-and-fi_estatus='2'-and-" +
                "select_table='" + observatorio.getTabla() + "'-order-by-fc_nombre-desc-limit-1";
        if (o_ldap.m_php_jason("genera_json.php", cSql)) {
            try {
                String cFecha, cPeriodo, cAnio;
                cFecha = "";
                cPeriodo = "";
                JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                for (int i = 0; i < data_array.length(); i++) {
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    cFecha = obj.getString("fc_nombre");
                    cPeriodo = obj.getString("anio");
                }
                cFecha = cFecha.substring(15);
                cAnio = cPeriodo.substring(4);
                Log.e("********* cFecha=", cFecha + " cPeriodo=" + cPeriodo + " Año=" + cAnio);
                observatorio.setFecha(cFecha);
                observatorio.setAnio(cAnio);
                observatorio.setPeriodo(cPeriodo);
                if (cFecha != "") {

                    otxt_fecha.setText(cFecha);
                }
            } catch (JSONException e) {
                //e.printStackTrace();
                Log.w("Fechas:->", cSql);
            }
        } else {
            otxt_fecha.setText("Sin Fecha");
            Log.w("fechas:>>>", cSql);
            Toast.makeText(g_contexto, "Regresa falso " + cSql, Toast.LENGTH_SHORT).show();
        }
// ======================================================================================================
//      *******************************************************************************
        cSql = "?sql=select-namefilter,field,fielddescrip,tablecatalog,tablemain-from-pres_reports_secondary_filters-" +
                "where-idreportGenera=" + idReportGe[btn.getId()] + "-order-by-idsecondaryfilter";

        g_reporte = idReportGe[btn.getId()];
        if (o_ldap.m_php_jason("genera_json.php", cSql)) {
            try {
                //Log.d("Filtros","paso sql");
                JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                nFil = data_array.length();
                a_Filtros = new String[nFil];
                aFilDat = new String[nFil];
                _sel_filtro = new sel_filtro[nFil];


                for (int i = 0; i < nFil; i++) {
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    a_Filtros[i] = (obj.getString("namefilter"));
                    cW = obj.getString("field") + "-" + obj.getString("fielddescrip") + "-" + obj.getString("tablecatalog") + "-" + obj.getString("tablemain") + "-";
                    //Log.d("Cadena=",cW);
                    aFilDat[i] = (cW);

                }


                _btn_filtro = new Button[nFil];
                //Log.v("Filtro long=",Integer.toString(_btn_filtro.length));
                _ly_filtros = (LinearLayout)getActivity(). findViewById(R.id._ly_filtro);
                _ly_filtros.removeAllViews();
                _lv_filtro = new _lv_Cata[nFil];

                for (int i = 0; i < nFil; i++) {
                    adiciona_boton(_ly_filtros, i, lv_param, "F", a_Filtros);
                    adiciona_filtro(aFilDat[i], i);
                }

            } catch (JSONException e) {
                //e.printStackTrace();
                Log.w("Filtros ", cSql);
            }
        } else {
            Toast.makeText(g_contexto, "Regresa falso " + cSql, Toast.LENGTH_SHORT).show();
        }
//      *********************************************************************************************


    }

    //  ===============================================================================
    public void boton_filtro_click(View btn){
        Toast.makeText(getActivity(),"Filtro",Toast.LENGTH_SHORT).show();
        Button btn_ = (Button)btn;
        //     Log.e("Boton id=",Integer.toString(btn.getId())+" "+Integer.toString(_btn_filtro.length)+"***********");
        int nPos = btn.getId()-_btn_rep.length - _btn_agrupa.length;
        for ( int i=0 ; i < _lv_filtro.length ; i++) {
            _lv_filtro[i]._lv.setVisibility(View.GONE);
            _sel_filtro[i]._ly_filtro.setVisibility(View.GONE);
        }
        if(_ly_tbl!=null){
            _ly_tbl.setVisibility(View.GONE);
        }
        for ( int i=0 ; i < _lv_filtro.length ; i++){

            btn_ = (Button)getActivity().findViewById(i+_btn_rep.length + _btn_agrupa.length);
            btn_.setTextColor(Color.BLACK);
            if ( nPos== i ){
                _ly_Cata.setVisibility(View.VISIBLE);
                _lv_filtro[nPos]._lv.setVisibility(View.VISIBLE);
                _lv_filtro[nPos]._lv.bringToFront();
                Log.i("Filtro ("+i+")= ","  "+_btn_rep.length+" "+_btn_agrupa.length+"  "+btn_.getId());
                btn_.setTextColor(Color.RED);
                nCatalogo_Visible=nPos;
                _sel_filtro[i]._ly_filtro.setVisibility(View.VISIBLE);
            }
        }
    }
    //  ==============================================================================
    public void boton_agrupa_click(View btn){
        Button btn_;
        int nPos = btn.getId() - _btn_rep.length;  // - _btn_filtro.length;
        g_agrupa = a_Agrupa_Campo[nPos];// Guarda el campo de agrupación
       Toast.makeText(getActivity(),"Agrupa "+g_agrupa,Toast.LENGTH_SHORT).show();
        for (int i=0; i<_ly_agrupa.getChildCount() ; i++){
            btn_ = (Button)getActivity().findViewById(i+_btn_rep.length ) ; // + _btn_filtro.length);
            btn_.setTextColor(Color.BLACK);
            if ( nPos == i){
              btn_.setTextColor(Color.rgb(213,0,127));


            }
        }
    }

    // ==========================================================================================================
    public void adiciona_filtro(String cValores, int pos) {


        String[] aValores = cValores.split("-");
        //Log.i("Catalogo Filtros",cValores+" "+aValores[2]+" "+aValores[0]+" "+aValores[1]);
        ArrayList<String> aCat = Trae_Cves(aValores[2], aValores[0], aValores[1], observatorio.getAnio());
        _ly_Cata = (LinearLayout)getActivity(). findViewById(R.id._ly_cata);


        _lv_filtro[pos] = new _lv_Cata(aCat);
        // adiciono el listview al Linearlayout
        _ly_Cata.addView((View) _lv_filtro[pos]._lv, pos);
        _lv_filtro[pos]._lv.setBackgroundColor(Color.WHITE);
        _lv_filtro[pos]._lv.setVisibility(View.GONE);


        LayoutInflater _ly_infla = LayoutInflater.from(g_contexto);

        _sel_filtro[pos] = new sel_filtro(_ly_infla.inflate(R.layout.sel_filtro, null), pos, _lv_filtro[pos]._lv, _lv_filtro[pos]);
        _ly_Cata.addView(_sel_filtro[pos]._ly_filtro);


    }


    public ArrayList<String> Trae_Cves(String v_Tabla, String v_Campo, String v_Descripcion, String cAnio) {
        int nVal = 0;
        String cCampo, cDescripcion;
        String cPar;
        ArrayList<String> a_Cves = new ArrayList<String>();
        String cSql = "?sql=select-" + v_Campo + "," + v_Descripcion + "-from-" + v_Tabla + "-where-anio='" + cAnio + "'-order-by-" + v_Campo;
        cPar = "?tableMain=noimporta" +
                "&tableCatalog=" + v_Tabla +
                "&field=" + v_Campo +
                "&fieldDescrip=" + v_Descripcion +
                "&idReportGenera=" + g_reporte +
                "&anio=" + observatorio.getAnio() +
                "&periodo=" + observatorio.getPeriodo() +
                "&responsabilidad=" + o_usuario.getF_id_responsabilidad() +
                "&fecha=" + observatorio.getFecha();
        //Log.i("**** Parametros *****",cPar);


        //Log.e("Sql:",cSql);
        //       if ( o_ldap.m_php_jason("genera_json.php",cSql) ){
        if (o_ldap.m_php_jason("genera_catalogoResp.php", cPar)) {
            try {
                JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                nVal = data_array.length();
                for (int i = 0; i < nVal; i++) {
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    cCampo = (obj.getString(v_Campo));
                    cDescripcion = (obj.getString(v_Descripcion));
                    a_Cves.add(cCampo + "-" + cDescripcion);
                }


            } catch (JSONException e) {
                //e.printStackTrace();
                Log.w("catch error", "JSON trae cves");
            }
        } else {
            Toast.makeText(g_contexto, "Regresa falso " + cSql, Toast.LENGTH_SHORT).show();
        }
        return a_Cves;
    }



    // ==========================================================================================================
    void llena_encabezados(){
        String cSql,cMovible,cNo,cSegundo,cColor;
        Boolean l_segundo_renglon = false;
        cSql = "?sql=select-*-from-pres_reports_column-where-idreportgenera="+g_reporte+"order-by-idcolumnrepor";
        cNo = "NO";
        nCol_Fijas = -1;
        if ( o_ldap.m_php_jason("genera_json.php",cSql) ){
            try{
                JSONArray data_array = new JSONArray(o_ldap.getV_cadena_json());
                //  idcolumnrepor	idreportgenera	nametitle	field	                namejson	            type	movable	formula	width	height	sizefont	backcolor	highcolor	fontcolor	sizeline	typedata	secondarytextbutton	colorbutton	    nameshort
                //      1	            1	        Clave	    NO	                    clave	                3	    NO	    NO	    50	    50	    12	        CLEAR	    0x360A3D	#2f2e2e	    0.4	        Clave	 	                    373737
                //      2	            1	        Descripción	NO	                    descripcion	            3	    NO	    NO	    190	    50	    12	        CLEAR	    0x360A3D	#2f2e2e	    0.4	        Clave	 	                    373737
                //      3	            1	        Aprobado	presupuesto_aprobado	presupuesto_aprobado	2	    YES	    sum	    110	    50	    11	        CLEAR	    0x360A3D	#2f2e2e	    0.4	        Cantidad	Anual	            950054	        Aprob.


                TableLayout _tab_A , _tab_B ;
                TableLayout.LayoutParams layOutRenglon;

                _tab_A = (TableLayout)getActivity().findViewById(R.id._TablaA);
                _tab_B = (TableLayout)getActivity().findViewById(R.id._TablaB);

                _tab_A.removeAllViews();
                _tab_B.removeAllViews();

                cSql = "";

                TableRow fila_A = new TableRow(getActivity());
                TableRow fila_B = new TableRow(getActivity());
                TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                fila_A.setLayoutParams(layoutFila);
                fila_B.setLayoutParams(layoutFila);
                a_Cam_Json  = new String[data_array.length()];
                a_Alto      = new Integer[data_array.length()];
                a_Ancho     = new Integer[data_array.length()];
                a_Tamano    = new Integer[data_array.length()];
                for ( int i=0 ; i < data_array.length() ; i++ ){
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    a_Cam_Json[i] = obj.getString("namejson");
                    a_Alto[i]     = obj.getInt("height")+5;
                    a_Ancho[i]    = obj.getInt("width")+45;
                    a_Tamano[i]   = obj.getInt("sizefont");
                    TextView texto = new TextView(getActivity());
                    cColor= obj.getString("colorbutton");
                    cSegundo=obj.getString("secondarytextbutton");
                    cSegundo=cSegundo.trim();
                    if (cSegundo!="")l_segundo_renglon = true;

                    texto.setBackgroundColor(Color.parseColor("#"+cColor));
                    texto.setTextColor(Color.WHITE);
                    texto.setText(obj.getString("nametitle"));
                    texto.setWidth(  a_Ancho[i] );
                    texto.setHeight( a_Alto[i] );
                    texto.setTextSize( a_Tamano[i]);
                    texto.setGravity(Gravity.CENTER_HORIZONTAL);

                    cMovible = obj.getString("movable");

                    if ( cMovible.equals(cNo)){
                        nCol_Fijas++;
                        fila_A.addView(texto);

                        // Log.e("movable A","["+cMovible+"]");
                    }else{
                        fila_B.addView(texto);
                        // Log.e("movable B","["+cMovible+"]");
                    }
                }
                _tab_A.addView(fila_A);
                _tab_B.addView(fila_B);
                //              =================================================== 04 abril 2017 agrego 2do renglon del encabezado
                if ( l_segundo_renglon ){
                    TableRow fila_A1 = new TableRow(getActivity());
                    TableRow fila_B1 = new TableRow(getActivity());
                    for (int i = 0; i < data_array.length(); i++) {
                        JSONObject obj = new JSONObject(data_array.get(i).toString());
                        TextView texto = new TextView(getActivity());
                        texto.setText(obj.getString("secondarytextbutton"));
                        texto.setBackgroundColor(Color.parseColor("#"+obj.getString("colorbutton"))); // 05 abril 2017
                        texto.setTextColor(Color.WHITE);    // 06 Abril 2017
                        texto.setTextSize(a_Tamano[i]-3);
                        texto.setHeight(a_Alto[i]-15);
                        texto.setGravity(Gravity.RIGHT);

                        cMovible = obj.getString("movable");
                        if (cMovible.equals(cNo)) {
                            fila_A1.addView(texto);
                        } else {
                            fila_B1.addView(texto);
                        }
                    }
                    _tab_A.addView(fila_A1);
                    _tab_B.addView(fila_B1);
                }
//              ============================================================= 04 abril 2017

            }catch (JSONException e){
                e.printStackTrace();
               // Log.w("Catch JSOn","llena_encabezados "+cSql);
            }
        }else{
            Toast.makeText(getActivity(),"No hay información "+cSql,Toast.LENGTH_SHORT).show();
        }

    }

    private void syncScrolls(final HorizontalScrollView currentView, final HorizontalScrollView otherView) {

        //This create the Gesture Listener where we override the methods we need
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //Here onfling just return true, this way doesnt happens
                return true;
            }
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //On scroll we sync the movement of the both views
                otherView.scrollTo(currentView.getScrollX(), 0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        };
        //This create the gesture detectors that implements the previous custom gesture listener
        final GestureDetector gestureDetector = new GestureDetector(getActivity(), gestureListener);
        //And finally we set everything to the views we need
        currentView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }
       public void cierra_filtro() {
            //Toast.makeText( g_contexto,"Boton :" + btn.getTag() ,Toast.LENGTH_LONG).show();
            _ly_Cata.setVisibility(View.GONE);
            if (_ly_tbl != null) {
                _ly_tbl.setVisibility(View.VISIBLE);
            }
            //_lv_filtro[(int)btn.getTag()]._lv.setVisibility(View.GONE);
        }

    // ==========================================================================================================
    public void oculta_filtro_visible() {
        _ly_Cata.setVisibility(View.GONE);
        if (nCatalogo_Visible > 0) {

            _sel_filtro[nCatalogo_Visible]._ly_filtro.setVisibility(View.INVISIBLE);
            _sel_filtro[nCatalogo_Visible]._ly_filtro.setVisibility(View.GONE);
            _lv_filtro[nCatalogo_Visible]._lv.setVisibility(View.INVISIBLE);
            _lv_filtro[nCatalogo_Visible]._lv.setVisibility(View.GONE);
        }
    }

    public String trae_filtros() {

        String cFiltro = "", cValor, cCampo, cW;
        _lv_Cata _lv_;
        ListView _lvw_;
        //_lv_filtro[pos]._lv
        for (int i = 0; i < _lv_filtro.length; i++) {
            cW = "";
            _lvw_ = _lv_filtro[i]._lv;
            _lv_ = _lv_filtro[i];
            if (_lvw_.getCheckedItemCount() > 0) {
                cCampo = aFilDat[i];
                cCampo = cCampo.substring(0, cCampo.indexOf('-'));
                Log.e("!!!!! Filtro " + i, "Numero=" + _lvw_.getCheckedItemCount() + " " + cCampo);
                SparseBooleanArray sp = _lvw_.getCheckedItemPositions();
                //sp.clear(); NOOOOOOOOOOOO

                Log.i("Elegidos ", ":" + sp.size());
                //Log.i("Long"," "+_lvw_.getCheckedItemCount());
                cW = "";
                cValor = "";
                for (int j = 0; j < sp.size(); j++) {
                    //for (int j=0; j<= _lvw_.getCheckedItemCount() ; j++ ){
                    //Log.e("filtro " + j, "Valor " + _lvw_.getItemAtPosition(sp.keyAt(j)).toString()+ " " + _lvw_.isItemChecked(sp.keyAt(j)) );
                    int posicion = sp.keyAt(j);
                    //Log.e("Posicion="," "+posicion+" "+sp.valueAt(j)+" "+_lv_.a_Cat.get(posicion));
                    //if ( sp.get(sp.keyAt(j))) {
                    //if ( _lvw_.isItemChecked(sp.keyAt(j)) ) {
                    if (sp.valueAt(j)) {

                        //Log.e("Selecciono "+j,"Valor:"+_lvw_.getItemAtPosition(sp.keyAt(j)).toString());
                        //Log.e("filtro " + j , "Valor " + _lv_.a_Cat.get(sp.keyAt(j)));

                        cValor = _lv_.a_Cat.get(posicion);
                        cValor = cValor.substring(0, cValor.indexOf('-'));
                        cValor = "'" + cValor +"'";
                        if (cW == "") {
                            cW = cValor ;
                        } else {
                            cW = cW + "," + cValor ;
                        }

                        Log.i("     Selecciono " + j, "Valor " + cValor);

                    }
                }
                // &filtro1=proyecto&valorFiltro1='B000000'&filtro2=unidad&valorFiltro2='OF16'
                cFiltro = cFiltro + "&filtro" + (i + 1) + "=" + cCampo + "&valorFiltro" + (i + 1) + "=" + cW;
            }

        }
        Log.e("                *****", "***** cFiltro = " + cFiltro);
        return cFiltro;
    }

    public interface empleaMetodos{
    void metodo1();

    }
    public void metodo1(){
        Toast.makeText(getActivity(), "metodo1", Toast.LENGTH_SHORT).show();
    }
    public void metodo2(){
        Toast.makeText(getActivity(), "metodo2", Toast.LENGTH_SHORT).show();
    }

}
