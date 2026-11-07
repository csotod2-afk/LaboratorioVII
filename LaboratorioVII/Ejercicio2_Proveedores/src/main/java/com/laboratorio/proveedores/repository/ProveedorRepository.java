package com.laboratorio.proveedores.repository;

import com.laboratorio.proveedores.model.Proveedor;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ProveedorRepository {

    private final List<Proveedor> proveedores = new CopyOnWriteArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong();

    public Proveedor guardar(Proveedor proveedor) {
        proveedor.setId(secuenciaId.incrementAndGet());
        proveedores.add(proveedor);
        return proveedor;
    }

    public List<Proveedor> buscarTodos() {
        return List.copyOf(proveedores);
    }

    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedores.stream()
                .filter(proveedor -> proveedor.getId().equals(id))
                .findFirst();
    }
}
