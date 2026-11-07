package com.laboratorio.empleados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.dto.EmpleadoResponse;
import com.laboratorio.empleados.exception.EmpleadoNoEncontradoException;
import com.laboratorio.empleados.model.Empleado;
import com.laboratorio.empleados.model.Estado;
import com.laboratorio.empleados.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

	private final EmpleadoRepository empleadoRepository;

	public EmpleadoService(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public EmpleadoResponse crear(EmpleadoRequest request) {
		Estado estado = request.estado() != null ? request.estado() : Estado.ACTIVO;
		Empleado empleado = new Empleado(null, request.nombre(), request.apellido(), request.correo(),
				request.especialidad(), estado);
		return EmpleadoResponse.desde(empleadoRepository.guardar(empleado));
	}

	public List<EmpleadoResponse> listar(String especialidad) {
		return empleadoRepository.listar().stream()
				.filter(empleado -> coincideEspecialidad(empleado, especialidad))
				.map(EmpleadoResponse::desde)
				.toList();
	}

	public EmpleadoResponse obtenerPorId(Long id) {
		return empleadoRepository.buscarPorId(id)
				.map(EmpleadoResponse::desde)
				.orElseThrow(() -> new EmpleadoNoEncontradoException(id));
	}

	private boolean coincideEspecialidad(Empleado empleado, String especialidad) {
		return especialidad == null || especialidad.isBlank()
				|| empleado.getEspecialidad().equalsIgnoreCase(especialidad.trim());
	}
}
