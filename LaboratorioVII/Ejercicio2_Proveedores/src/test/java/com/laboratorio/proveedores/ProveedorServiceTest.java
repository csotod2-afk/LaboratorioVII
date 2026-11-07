package com.laboratorio.proveedores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.dto.ProveedorResponse;
import com.laboratorio.proveedores.exception.ProveedorNoEncontradoException;
import com.laboratorio.proveedores.model.Estado;
import com.laboratorio.proveedores.model.Proveedor;
import com.laboratorio.proveedores.repository.ProveedorRepository;
import com.laboratorio.proveedores.service.ProveedorService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void crearProveedorAsignaIdYEstadoActivoPorDefecto() {
        when(proveedorRepository.guardar(any(Proveedor.class))).thenAnswer(invocacion -> {
            Proveedor proveedor = invocacion.getArgument(0);
            proveedor.setId(1L);
            return proveedor;
        });
        ProveedorRequest request = new ProveedorRequest("Ana", "Lopez", "ana@correo.com", "Papeleria", null);

        ProveedorResponse creado = proveedorService.crear(request);

        assertEquals(1L, creado.id());
        assertEquals("Ana", creado.nombre());
        assertEquals("Papeleria", creado.categoria());
        assertEquals(Estado.ACTIVO, creado.estado());
        verify(proveedorRepository).guardar(any(Proveedor.class));
    }

    @Test
    void obtenerProveedorExistenteRetornaSusDatos() {
        Proveedor proveedor = new Proveedor(5L, "Luis", "Perez", "luis@correo.com", "Limpieza", Estado.INACTIVO);
        when(proveedorRepository.buscarPorId(5L)).thenReturn(Optional.of(proveedor));

        ProveedorResponse encontrado = proveedorService.obtenerPorId(5L);

        assertEquals(5L, encontrado.id());
        assertEquals("luis@correo.com", encontrado.correo());
        assertEquals(Estado.INACTIVO, encontrado.estado());
    }

    @Test
    void obtenerProveedorInexistenteLanzaExcepcion() {
        when(proveedorRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        ProveedorNoEncontradoException excepcion = assertThrows(ProveedorNoEncontradoException.class,
                () -> proveedorService.obtenerPorId(99L));

        assertEquals("No existe un proveedor con id 99", excepcion.getMessage());
    }
}
