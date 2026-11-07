package com.laboratorio.profesores.dto;

import com.laboratorio.profesores.model.Estado;
import com.laboratorio.profesores.model.Profesor;

public record ProfesorResponse(Long id, String nombre, String apellido, String correo, String especialidad,
		Estado estado) {

	public static ProfesorResponse desde(Profesor profesor) {
		return new ProfesorResponse(profesor.getId(), profesor.getNombre(), profesor.getApellido(),
				profesor.getCorreo(), profesor.getEspecialidad(), profesor.getEstado());
	}
}
