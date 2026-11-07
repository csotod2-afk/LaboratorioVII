package com.laboratorio.proveedores.service;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.dto.ProveedorResponse;
import com.laboratorio.proveedores.exception.ProveedorNoEncontradoException;
import com.laboratorio.proveedores.model.Estado;
import com.laboratorio.proveedores.model.Proveedor;
import com.laboratorio.proveedores.repository.ProveedorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public ProveedorResponse crear(ProveedorRequest request) {
        Estado estado = request.estado() != null ? request.estado() : Estado.ACTIVO;
        Proveedor proveedor = new Proveedor(
                null,
                request.nombre(),
                request.apellido(),
                request.correo(),
                request.categoria(),
                estado);
        return ProveedorResponse.desde(proveedorRepository.guardar(proveedor));
    }

    public List<ProveedorResponse> listar(String categoria) {
        return proveedorRepository.buscarTodos().stream()
                .filter(proveedor -> categoria == null || categoria.isBlank()
                        || proveedor.getCategoria().equalsIgnoreCase(categoria.trim()))
                .map(ProveedorResponse::desde)
                .toList();
    }

    public ProveedorResponse obtenerPorId(Long id) {
        return proveedorRepository.buscarPorId(id)
                .map(ProveedorResponse::desde)
                .orElseThrow(() -> new ProveedorNoEncontradoException(id));
    }
}
