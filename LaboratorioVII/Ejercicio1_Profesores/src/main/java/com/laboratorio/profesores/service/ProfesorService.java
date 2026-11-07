package com.laboratorio.profesores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.dto.ProfesorResponse;
import com.laboratorio.profesores.exception.ProfesorNoEncontradoException;
import com.laboratorio.profesores.model.Estado;
import com.laboratorio.profesores.model.Profesor;
import com.laboratorio.profesores.repository.ProfesorRepository;

@Service
public class ProfesorService {

	private final ProfesorRepository profesorRepository;

	public ProfesorService(ProfesorRepository profesorRepository) {
		this.profesorRepository = profesorRepository;
	}

	public ProfesorResponse crear(ProfesorRequest request) {
		Estado estado = request.estado() != null ? request.estado() : Estado.ACTIVO;
		Profesor profesor = new Profesor(null, request.nombre(), request.apellido(), request.correo(),
				request.especialidad(), estado);
		return ProfesorResponse.desde(profesorRepository.guardar(profesor));
	}

	public List<ProfesorResponse> listar(String especialidad) {
		return profesorRepository.listar().stream()
				.filter(profesor -> especialidad == null || especialidad.isBlank()
						|| profesor.getEspecialidad().equalsIgnoreCase(especialidad.trim()))
				.map(ProfesorResponse::desde)
				.toList();
	}

	public ProfesorResponse obtenerPorId(Long id) {
		return profesorRepository.buscarPorId(id)
				.map(ProfesorResponse::desde)
				.orElseThrow(() -> new ProfesorNoEncontradoException(id));
	}
}
