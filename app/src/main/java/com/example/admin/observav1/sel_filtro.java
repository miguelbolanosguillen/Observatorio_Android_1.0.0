package com.example.admin.observav1;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * Created by INE on 17/03/2017.
 */

public class sel_filtro  {

    public LinearLayout _ly_filtro;
    public int  _id;
    //public SearchView _sv;
    private ListView lista;
    Button _btn;
    public _lv_Cata filtros;


    public sel_filtro(View v , int nPos, ListView lista , _lv_Cata filtros ){
        this.lista = lista;
        this.filtros = filtros;
        _ly_filtro  = (LinearLayout)v.findViewById(R.id._sel_filtro);
        _ly_filtro.setId(nPos);
        _btn        = (Button)v.findViewById(R.id._btn_sel12345);
        //_sv         = (SearchView)(v.findViewById(R.id._sv_));

        _btn.setTag(nPos);
        this._id    = nPos;
        _ly_filtro.setVisibility(View.INVISIBLE);
        _ly_filtro.setVisibility(View.GONE);



        //_sv.setIconifiedByDefault(false);
        //_sv.setOnQueryTextListener(this);
        //_sv.setSubmitButtonEnabled(false);
        //_sv.setQueryHint("Seleccione aquí");
    }

//    @Override
//
//    public boolean onQueryTextChange(String newText) {
//        if (TextUtils.isEmpty(newText)){
//            lista.clearTextFilter();
//
//        }else{
//            filtro.ad_Cat.getFilter().filter(newText);
//            lista.setTextFilterEnabled(true);
//            lista.setFilterText(newText);
//            lista.refreshDrawableState();
//
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }

}

