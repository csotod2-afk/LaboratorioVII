package com.laboratorio.proveedores.controller;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.dto.ProveedorResponse;
import com.laboratorio.proveedores.service.ProveedorService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(@Valid @RequestBody ProveedorRequest request) {
        ProveedorResponse creado = proveedorService.crear(request);
        return ResponseEntity.created(URI.create("/api/proveedores/" + creado.id())).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> listar(@RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(proveedorService.listar(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }
}
