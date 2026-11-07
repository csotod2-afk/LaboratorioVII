package com.laboratorio.empleados.dto;

import com.laboratorio.empleados.model.Empleado;
import com.laboratorio.empleados.model.Estado;

public record EmpleadoResponse(Long id, String nombre, String apellido, String correo, String especialidad,
		Estado estado) {

	public static EmpleadoResponse desde(Empleado empleado) {
		return new EmpleadoResponse(empleado.getId(), empleado.getNombre(), empleado.getApellido(),
				empleado.getCorreo(), empleado.getEspecialidad(), empleado.getEstado());
	}
}
