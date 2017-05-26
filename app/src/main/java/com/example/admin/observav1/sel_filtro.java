package com.example.admin.observav1;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import static com.example.admin.observav1.MainActivity.g_contexto;


/**
 * Created by INE on 17/03/2017.
 */

public class sel_filtro {

    public LinearLayout _ly_filtro, _ly_Cata, _ly_tbl;
    public int _id;
    //public SearchView _sv;
    private ListView lista;
    Button _btn;
    public _lv_Cata filtro;


    public sel_filtro(View v1, int nPos, final ListView lista, _lv_Cata filtro, final LinearLayout _ly_tbl) {

        this.lista = lista;
        this.filtro = filtro;

        _ly_filtro = (LinearLayout) v1.findViewById(R.id._sel_filtro);

        _ly_filtro.setId(nPos);
        _btn = (Button) v1.findViewById(R.id._btn_sel12345);
        //_sv         = (SearchView)(v.findViewById(R.id._sv_));
        _btn.setTag(nPos);
        this._id = nPos;

        lista.setVisibility(View.GONE);
        lista.setVisibility(View.INVISIBLE);
        _ly_filtro.setVisibility(View.INVISIBLE);
        _ly_filtro.setVisibility(View.GONE);

        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( _ly_tbl != null ) {
                    _ly_tbl.setVisibility(View.VISIBLE);
                }
                lista.setVisibility(View.GONE);
                //lista.setVisibility(View.INVISIBLE);
                //_ly_filtro.setVisibility(View.INVISIBLE);
                _ly_filtro.setVisibility(View.GONE);


            }
        });


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

