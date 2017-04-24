package com.example.admin.observav1;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by INE on 01/07/2016.
 */


public class c_fun {

    static ArrayList<String> jsonStringToArray(String jsonString){// throws JSONException {
        ArrayList<String> stringArray = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                stringArray.add(jsonArray.getString(i));
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringArray;
    }

    static boolean la_cadena_es_numero(String cadena1){
        String cadena = cadena1;
        cadena = cadena.trim();
        cadena = cadena.replace(".","");//quita punto decimal, si lo hay
        if ( cadena==null || cadena.isEmpty() ){
            //Log.i("Cadena","vacia o nula");
            return false;
        }
        int i = 0;
        if ( cadena.charAt(0)=='-'){
            if ( cadena.length() > 1 ){
                i++;
            }else{
                //Log.i("Cadena ","solo signo menos"+cadena)
                return false;
            }
        }
        char c;
        for ( ; i< cadena.length();i++){
           c = cadena.charAt(i);
            if ( Character.isDigit(c)==false ){

                return false;
            }
        }
        return true;
    }




}

