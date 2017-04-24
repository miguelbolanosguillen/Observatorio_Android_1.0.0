package com.example.admin.observav1;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.admin.observav1.MainActivity.g_contexto;

/**
 * Created by INE on 01/03/2017.
 */

public class _lv_Cata {
    ArrayList<String> a_Cat;
    ArrayAdapter<String> ad_Cat;

    //private BD_Pto db;
    private Context contexto;
    public ListView _lv;

    public _lv_Cata(ArrayList<String> a_Cat) {
        this.contexto   = g_contexto;
        this.a_Cat      = a_Cat;
        this._cursor();
    }

    public void _cursor(){

        ad_Cat = new ArrayAdapter<String>(contexto,android.R.layout.select_dialog_multichoice,a_Cat);
        _lv    = new ListView(this.contexto);

        _lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        _lv.setBackgroundColor(Color.LTGRAY);
        _lv.setAdapter(ad_Cat);
        _lv.setTextFilterEnabled(true);
    }

}
