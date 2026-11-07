package com.laboratorio.empleados.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.laboratorio.empleados.model.Empleado;

@Repository
public class EmpleadoRepository {

	private final List<Empleado> empleados = new CopyOnWriteArrayList<>();
	private final AtomicLong secuenciaId = new AtomicLong();

	public Empleado guardar(Empleado empleado) {
		empleado.setId(secuenciaId.incrementAndGet());
		empleados.add(empleado);
		return empleado;
	}

	public List<Empleado> listar() {
		return List.copyOf(empleados);
	}

	public Optional<Empleado> buscarPorId(Long id) {
		return empleados.stream().filter(empleado -> empleado.getId().equals(id)).findFirst();
	}
}
