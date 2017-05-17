package com.example.admin.observav1;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.admin.observav1.MainActivity.g_contexto;

/**
 * Created by INE on 01/03/2017.
 */

public class _lv_Cata {
    ArrayList<c_filtro> a_Cat;              // 17-mayo-2017
    filtro_adaptador ad_Cat = null;

    private Context contexto;
    public ListView _lv;

    public _lv_Cata(ArrayList<c_filtro> a_Cat) {
        this.contexto = g_contexto;
        this.a_Cat = a_Cat;
        this._cursor();
    }

    public void _cursor() {
        ad_Cat = new filtro_adaptador(contexto, R.layout.filtro_info, a_Cat);
        _lv = new ListView(contexto);
        _lv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400));
        _lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        _lv.setBackgroundColor(Color.TRANSPARENT);
        _lv.setAdapter(ad_Cat);
        _lv.setTextFilterEnabled(true);
    }

    // Obtienen la clave del catalogo, en el adaptador en la posicion solicitada
    public String cValor(int posicion) {
        return ad_Cat.getItem(posicion).getClave();
    }

}
