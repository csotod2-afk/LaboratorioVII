package com.laboratorio.profesores.controller;

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

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.dto.ProfesorResponse;
import com.laboratorio.profesores.service.ProfesorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

	private final ProfesorService profesorService;

	public ProfesorController(ProfesorService profesorService) {
		this.profesorService = profesorService;
	}

	@PostMapping
	public ResponseEntity<ProfesorResponse> crear(@Valid @RequestBody ProfesorRequest request) {
		ProfesorResponse creado = profesorService.crear(request);
		return ResponseEntity.created(URI.create("/api/profesores/" + creado.id())).body(creado);
	}

	@GetMapping
	public List<ProfesorResponse> listar(@RequestParam(required = false) String especialidad) {
		return profesorService.listar(especialidad);
	}

	@GetMapping("/{id}")
	public ProfesorResponse obtenerPorId(@PathVariable Long id) {
		return profesorService.obtenerPorId(id);
	}
}
