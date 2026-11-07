package com.laboratorio.profesores.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.laboratorio.profesores.model.Profesor;

@Repository
public class ProfesorRepository {

	private final List<Profesor> profesores = new CopyOnWriteArrayList<>();
	private final AtomicLong secuenciaId = new AtomicLong();

	public Profesor guardar(Profesor profesor) {
		profesor.setId(secuenciaId.incrementAndGet());
		profesores.add(profesor);
		return profesor;
	}

	public List<Profesor> listar() {
		return List.copyOf(profesores);
	}

	public Optional<Profesor> buscarPorId(Long id) {
		return profesores.stream().filter(profesor -> profesor.getId().equals(id)).findFirst();
	}
}
