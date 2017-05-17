package com.example.admin.observav1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by INE on 17/05/2017.
 */

public class filtro_adaptador extends ArrayAdapter<c_filtro> {


    private ArrayList<c_filtro> ListaFiltro;
    private Context contexto;
    public int numSel = 0;                  // Cuantos items se han seleccionado

    public filtro_adaptador(Context context, int textViewResourceId, ArrayList<c_filtro> ListaFiltro) {
        super(context, textViewResourceId, ListaFiltro);
        this.ListaFiltro = new ArrayList<c_filtro>();
        this.ListaFiltro.addAll(ListaFiltro);
        this.contexto = context;
    }

    private class ViewHolder {
        CheckBox clave;         // El Texto del CheckBox sera la clave del catalogo del filtro
        TextView descripcion;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.filtro_info, null);

            holder = new ViewHolder();
            holder.descripcion = (TextView) convertView.findViewById(R.id.tv_descripcion);
            holder.clave = (CheckBox) convertView.findViewById(R.id.cb_seleccionado);
            convertView.setTag(holder);

            holder.clave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    c_filtro filtro = (c_filtro) cb.getTag();
//                  Toast.makeText(contexto.getApplicationContext(),"Clicked on Checkbox: " + cb.getText() +" is " + cb.isChecked(),Toast.LENGTH_SHORT).show();
                    if (cb.isChecked()) {
                        numSel++;
                    } else {
                        numSel--;
                        if (numSel < 0) {
                            numSel = 0;
                        }
                    }
                    filtro.setSeleccionado(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        c_filtro filtro = ListaFiltro.get(position);
        holder.descripcion.setText(filtro.getDescripcion());
        holder.clave.setText(" [" + filtro.getClave() + "]-");

        holder.clave.setChecked(filtro.estaSeleccionado());
        holder.clave.setTag(filtro);

        return convertView;

    }

}
