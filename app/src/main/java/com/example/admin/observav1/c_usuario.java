package com.example.admin.observav1;

/**
 * Created by INE on 30/06/2016.
 */
public class c_usuario {
    private String f_usuario;
    private String f_clave;

    private String f_nombre;
    private String f_ur;
    private String f_dur;
    private String f_responsabilidad;
    private String f_correo;
    private String f_id_responsabilidad;

    void c_usuario(String p_usu , String p_cve){
        this.f_usuario  = p_usu;
        this.f_clave    = p_cve;
    }

    void m_ldap(String p_nombre , String p_ur , String p_dUr ){
        this.f_nombre = p_nombre;
        this.f_ur = p_ur;
        this.f_dur = p_dUr ;
    }

    void m_set_datos(String p_correo,String p_resp) {
        this.f_correo = p_correo;
        this.f_responsabilidad = p_resp;
    }

    public String getF_usuario() {
        return f_usuario;
    }

    public void setF_usuario(String f_usuario) {
        this.f_usuario = f_usuario;
    }

    public String getF_clave() {
        return f_clave;
    }

    public void setF_clave(String f_clave) {
        this.f_clave = f_clave;
    }

    public String getF_nombre() {
        return f_nombre;
    }

    public void setF_nombre(String f_nombre) {
        this.f_nombre = f_nombre;
    }

    public String getF_ur() {
        return f_ur;
    }

    public void setF_ur(String f_ur) {
        this.f_ur = f_ur;
    }

    public String getF_dur() {
        return f_dur;
    }

    public void setF_dur(String f_dur) {
        this.f_dur = f_dur;
    }

    public String getF_responsabilidad() {
        return f_responsabilidad;
    }

    public void setF_responsabilidad(String f_responsabilidad) {
        this.f_responsabilidad = f_responsabilidad;
    }

    public String getF_correo() {
        return f_correo;
    }

    public void setF_correo(String f_correo) {
        this.f_correo = f_correo;
    }

    public String getF_id_responsabilidad() {
        return f_id_responsabilidad;
    }

    public void setF_id_responsabilidad(String f_id_responsabilidad) {
        this.f_id_responsabilidad = f_id_responsabilidad;
    }
}
