package com.laboratorio.proveedores.model;

public class Proveedor {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String categoria;
    private Estado estado;

    public Proveedor(Long id, String nombre, String apellido, String correo, String categoria, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.categoria = categoria;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Estado getEstado() {
        return estado;
    }
}
