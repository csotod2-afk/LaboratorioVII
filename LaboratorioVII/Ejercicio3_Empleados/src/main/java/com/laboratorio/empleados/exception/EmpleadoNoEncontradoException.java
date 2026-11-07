package com.laboratorio.empleados.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

	public EmpleadoNoEncontradoException(Long id) {
		super("No existe un empleado con id " + id);
	}
}
