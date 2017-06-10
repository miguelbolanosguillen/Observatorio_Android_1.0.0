package com.example.admin.observav1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by INE on 30/06/2016.
 */
public class c_php_jason {
    private String o_pagina;
    private String o_parametros;
    JSONObject o_json;
    private String v_cadena_json;
    private String v_sesion;
    private String v_estatus;
    private HttpURLConnection urlConnection;
    //   private final String s_home = "https://observatorio.ine.mx/";
    private final String s_home = "http://10.0.15.104/observatorio/";

    private void c_php_jason(String p_pag, String p_par) {
        this.o_pagina = p_pag;
        this.o_parametros = p_par;
    }

    // [{"estatus":"1",
// "usuario":"miguel.bolanos",
// "nombre":"MIGUEL ANGEL","apellidos":"BOLA\u00d1OS GUILLEN",
// "unidad":"OF16",
// "descripcion_unidad":"DIRECCI\u00d3N EJECUTIVA DE ADMINISTRACI\u00d3N",
// "sesion_id":"85qpo020agcsdih3kjq3g8ckt7"}]
    boolean m_php_ldap(String p_pag, String p_par, c_usuario o_usu) {
        boolean l_si_hay_datos = false;
        String v_ur = "", v_nombre = "", v_apellido = "", v_d_ur = "";
//      this.v_ur = ""; this.v_nombre = "" ; this.v_d_ur = "";
//      "activaSesion.php?usuario="++"&clave="+v_contra.getText().toString())

        //Log.i("Valores :" ,"val "+this.s_home+p_pag+p_par );
        if ((urlConnection = regresa_conexion(this.s_home + p_pag + p_par, false)) != null) {

            try {
                l_si_hay_datos = true;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String next;
                next = bufferedReader.readLine();
                JSONArray ja = new JSONArray(next);
//              Log.d("Next=",next);
//              Log.i("lnext=",String.valueOf(ja.length()));
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    v_estatus = jo.getString("estatus");
                }
//                Log.d("Estatus=",v_estatus);
                if (Integer.parseInt(v_estatus) == 1) {
                    String[] aaa = urlConnection.getHeaderField("Set-Cookie").split(";");
                    v_sesion = aaa[0];
                    ja = new JSONArray(next);
//                  Log.d("lnext=",String.valueOf(ja.length()));
//                  ja.length no son las columnas , sino lor renglones del JSON
//                  es decir en cada renglon , debe haber una unidad,nombre,apellidos,etc
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);
                        v_ur = jo.getString("unidad");
                        v_nombre = jo.getString("nombre");
                        v_apellido = jo.getString("apellidos");
                        v_d_ur = jo.getString("descripcion_unidad");
                    }
                    o_usu.m_ldap(v_nombre + " " + v_apellido, v_ur, v_d_ur);
//                  Log.w("Estatus:", o_usu.getF_nombre());
//                  conecta_catalogo(v_url.getText().toString(), "genera_catalogoResp.php?fecha=2016-06-28&tableMain=Consulta&tableCatalog=Cat_Proyectos&field=proyecto&fieldDescrip=descripcion&anio=2016&periodo=JUN-2016&responsabilidad=1&idReportGenera=0");
                } else {
//                  Log.d("Mal Estatus", "[" + v_estatus + "]");
                    l_si_hay_datos = false;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return l_si_hay_datos;
    }

    //  ************************************************************************
    boolean m_php_jason(String p_pag, String p_par) {
        boolean l_si_hay_datos = false;
        this.v_cadena_json = "";
//      Log.d("pagina",this.s_home+p_pag+p_par);
        if ((urlConnection = regresa_conexion(this.s_home + p_pag + p_par, true)) != null) {
            l_si_hay_datos = true;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String s_dato = "";
                String s_linea = "";
                while ((s_linea = br.readLine()) != null) {
                    s_dato = s_dato + s_linea;
//                    Log.e("Linea sql",s_linea);
                }
//              Log.d("Cadena=",s_dato+"\n");
                this.v_cadena_json = s_dato;
                this.o_json = new JSONObject(this.v_cadena_json);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                Log.d("Catch IOException", " c_php_jason");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                Log.d("Catch JSONException", " c_php_jason");
            }
        }
        return l_si_hay_datos;
    }

    // ******************************************************
    private HttpURLConnection regresa_conexion(String v_url, Boolean l_sesion) {

        try {
            URL url = new URL(v_url);
            urlConnection = (HttpURLConnection) url.openConnection();
//      urlConnection.setReadTimeout(2592000 /* milliseconds */); // 2592 segundos
//      urlConnection.setConnectTimeout(2592000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
//      urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//      urlConnection.setRequestProperty("Accept", "text/html,application/xml,");
//      urlConnection.setDoInput(true);
            if (l_sesion) {
                urlConnection.addRequestProperty("Cookie", v_sesion);
            }
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200) {
                // no hubo conexión a la pagina
                Log.w("!!!!Error!!!:", "Página no respondió");
                urlConnection.disconnect();
                urlConnection = null;
            } else {
                Log.w("Ok:", "!!!!Página respondió!!!!");
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            Log.d("MalformedURLException2", " c_php_jason");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            Log.d("IOException2", " c_php_jason");
        }
        return urlConnection;
    }
// ******************************************************

    public String getV_cadena_json() {
        return v_cadena_json;
    }

    public JSONObject getO_json() {
        return o_json;
    }
}
