package com.example.admin.observav1;


/**
 * Created by INE on 17/05/2017.
 */
// Clase personalizada para los listview de filtros
public class c_filtro {
    String clave = null;                        // Clave del catalogo , es el texto del check-box
    String descripcion = null;                  // Descripción de la clave , esta asociada al textview tv_descripcion
    boolean seleccionado = false;               // True cuando este seleccionado


    public c_filtro(String clave, String descripcion, boolean seleccionado) {
        super();
        this.clave = clave;
        this.descripcion = descripcion;
        this.seleccionado = seleccionado;
    }

    //==========================================================
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    //==========================================================
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    //==========================================================
    public boolean estaSeleccionado() {
        return seleccionado;
    }
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    //==========================================================
}
