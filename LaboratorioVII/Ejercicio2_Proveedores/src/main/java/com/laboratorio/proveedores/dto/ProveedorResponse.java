package com.laboratorio.proveedores.dto;

import com.laboratorio.proveedores.model.Estado;
import com.laboratorio.proveedores.model.Proveedor;

public record ProveedorResponse(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String categoria,
        Estado estado) {

    public static ProveedorResponse desde(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getApellido(),
                proveedor.getCorreo(),
                proveedor.getCategoria(),
                proveedor.getEstado());
    }
}
