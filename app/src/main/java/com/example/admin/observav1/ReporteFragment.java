package com.example.admin.observav1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.admin.observav1.MainActivity.g_contexto;

/**
 * Fragment class for each nav menu item
 */
public class ReporteFragment extends Fragment {
    Button botonCon;

    private ObservableScrollView scrollView1 = null;
    private ObservableScrollView scrollView2 = null;
    private HorizontalScrollView HsV_B = null;
    private HorizontalScrollView HsV_D = null;

    public String g_reporte="1";


    public String[] a_Agrupa_Des , a_Filtros, a_Agrupa_Campo , a_Cam_Json;
    private Integer[] a_Ancho , a_Alto , a_Tamano;


    public c_php_jason o_ldap = new c_php_jason();
    public int nCol_Fijas=-1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reporte, container, false);
    }


    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        /* botonCon = (Button)getActivity().findViewById(R.id.boton_consulta_informacion);
        botonCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cCampo;
                MainActivity act=(MainActivity)getActivity();
                String cPar = "?fecha="+act.varDatos("f")+"&tipo="+act.varDatos("t")+"&idReportGenera="+g_reporte+"&responsabilidad=1&anio="+act.varDatos("a");

                LinearLayout _ly_tbl = (LinearLayout)getActivity().findViewById(R.id.tbl_Consulta);
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
                Log.i("Genera consulta","genera_consultaResp.php"+cPar);

                System.out.println("valor de la varibale boleana "+o_ldap.m_php_jason("genera_consultaResp.php",cPar));
                if (act.sesi().m_php_jason("genera_consultaResp.php",cPar)){

                    try {
                        TableLayout _tab_C , _tab_D ;
                        TableLayout.LayoutParams layOutRenglon;

                        a_Cam_Json[0] = act.varDatos("t");
                        System.out.println("variable de la consulta "+ act.varDatos("t"));

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

                            JSONObject obj = new JSONObject(data_array.get(i).toString());
                            //Log.e("===","=====================================================================");
                            for ( int j=0 ; j < a_Cam_Json.length ; j++ ) {       // Columnas
                                TextView texto = new TextView(getActivity());
                                texto.setText(obj.getString(a_Cam_Json[j]));
                                System.out.println("arreglo de la consulta generada "+a_Cam_Json[j]);
                                texto.setWidth(  a_Ancho[j] );
                                texto.setHeight( a_Alto[j] );
                                texto.setTextSize( a_Tamano[j]);
                                //Log.e(a_Cam_Json[j],obj.getString(a_Cam_Json[j]));
                                if ( j<=nCol_Fijas ){
                                    fila_C.addView(texto);
                                }else{
                                    fila_D.addView(texto);
                                }

                            }
                            _tab_C.addView(fila_C);
                            _tab_D.addView(fila_D);
                            _ly_tbl.bringToFront();
                            //TODO
                            // _lv_filtro[nCatalogo_Visible]._lv.setVisibility(View.GONE);

                        }
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

            }
        });*/

    }
    public void pruebaBoton(){
        Toast.makeText(g_contexto, "metodo reporte", Toast.LENGTH_SHORT).show();
       // Log.i("metodo ","metodto pruebaBoton......");
    }

    // ==========================================================================================================
    void llena_encabezados(){
        String cSql,cMovible,cNo;
        cSql = "?sql=select-*-from-pres_reports_column-where-idreportgenera="+g_reporte+"order-by-idcolumnrepor";
        cNo = "NO";
        nCol_Fijas = -1;
        // Toast.makeText(this, cSql , Toast.LENGTH_SHORT).show();

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
                    a_Alto[i]     = obj.getInt("height")+20;
                    a_Ancho[i]    = obj.getInt("width")+25;
                    a_Tamano[i]   = obj.getInt("sizefont");
                    TextView texto = new TextView(getActivity());
                    texto.setText(obj.getString("nametitle"));
                    System.out.println("Prueba de encabezados "+a_Cam_Json[i]);
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
            }catch (JSONException e){
                // e.printStackTrace();
                Log.w("Catch JSOn","llena_encabezados "+cSql);
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


}
