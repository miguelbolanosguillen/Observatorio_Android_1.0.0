package com.example.admin.observav1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by INE on 24/04/2017.
 */


public class lv_responsa extends BaseAdapter {

    private Context contexto;
    private int layout;
    private List<String> aResponsa;


    public lv_responsa(Context contexto , int layout , List<String> aResponsa){
        this.contexto   = contexto;
        this.layout     = layout;
        this.aResponsa  = aResponsa;
    }

    @Override
    public int getCount() {
        return this.aResponsa.size();
    }

    @Override
    public Object getItem(int posicion) {
        return this.aResponsa.get(posicion);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup viewGroup) {

        Holder holder;

        if ( convertView==null ){
            // Se infla la vista que llega , con el XML personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(this.contexto);
            convertView = layoutInflater.inflate(R.layout.respo_item,null);
            holder = new Holder();
            // Se referencia el objeto a modificar y lo enlazamos
            holder.txvResponsa = (TextView)convertView.findViewById(R.id.txtv_responsabilidad);
            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }


        // Obtengo el dato segun la posicion
        String cResponsa = aResponsa.get(posicion);
        // Se le asigna al textview Personalizado
        holder.txvResponsa.setText(cResponsa);
        // Se devuelve la vista inflada y modificada
        return convertView;
    }

    static class Holder{
        private TextView txvResponsa;
    }
}