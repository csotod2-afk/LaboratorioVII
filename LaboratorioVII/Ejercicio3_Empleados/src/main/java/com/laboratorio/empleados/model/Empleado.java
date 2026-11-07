package com.laboratorio.empleados.model;

public class Empleado {

	private Long id;
	private String nombre;
	private String apellido;
	private String correo;
	private String especialidad;
	private Estado estado;

	public Empleado(Long id, String nombre, String apellido, String correo, String especialidad, Estado estado) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.especialidad = especialidad;
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

	public String getEspecialidad() {
		return especialidad;
	}

	public Estado getEstado() {
		return estado;
	}
}
