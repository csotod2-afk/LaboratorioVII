package com.laboratorio.empleados.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.dto.EmpleadoResponse;
import com.laboratorio.empleados.service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

	private final EmpleadoService empleadoService;

	public EmpleadoController(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@PostMapping
	public ResponseEntity<EmpleadoResponse> crear(@Valid @RequestBody EmpleadoRequest request) {
		EmpleadoResponse creado = empleadoService.crear(request);
		URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(creado.id())
				.toUri();
		return ResponseEntity.created(ubicacion).body(creado);
	}

	@GetMapping
	public List<EmpleadoResponse> listar(@RequestParam(required = false) String especialidad) {
		return empleadoService.listar(especialidad);
	}

	@GetMapping("/{id}")
	public EmpleadoResponse obtenerPorId(@PathVariable Long id) {
		return empleadoService.obtenerPorId(id);
	}
}
