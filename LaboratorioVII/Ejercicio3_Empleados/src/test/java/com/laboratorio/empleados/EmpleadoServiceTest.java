package com.laboratorio.empleados;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.dto.EmpleadoResponse;
import com.laboratorio.empleados.exception.EmpleadoNoEncontradoException;
import com.laboratorio.empleados.model.Empleado;
import com.laboratorio.empleados.model.Estado;
import com.laboratorio.empleados.repository.EmpleadoRepository;
import com.laboratorio.empleados.service.EmpleadoService;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

	@Mock
	private EmpleadoRepository empleadoRepository;

	@InjectMocks
	private EmpleadoService empleadoService;

	@Test
	void crearEmpleadoAsignaIdYEstadoActivoPorDefecto() {
		EmpleadoRequest request = new EmpleadoRequest("Ana", "López", "ana@empresa.com", "Contabilidad", null);
		when(empleadoRepository.guardar(any(Empleado.class))).thenAnswer(invocacion -> {
			Empleado empleado = invocacion.getArgument(0);
			empleado.setId(1L);
			return empleado;
		});

		EmpleadoResponse creado = empleadoService.crear(request);

		assertThat(creado.id()).isEqualTo(1L);
		assertThat(creado.nombre()).isEqualTo("Ana");
		assertThat(creado.estado()).isEqualTo(Estado.ACTIVO);
		verify(empleadoRepository).guardar(any(Empleado.class));
	}

	@Test
	void obtenerEmpleadoExistenteRetornaSusDatos() {
		Empleado empleado = new Empleado(7L, "Luis", "Pérez", "luis@empresa.com", "Sistemas", Estado.INACTIVO);
		when(empleadoRepository.buscarPorId(7L)).thenReturn(Optional.of(empleado));

		EmpleadoResponse encontrado = empleadoService.obtenerPorId(7L);

		assertThat(encontrado.id()).isEqualTo(7L);
		assertThat(encontrado.correo()).isEqualTo("luis@empresa.com");
		assertThat(encontrado.estado()).isEqualTo(Estado.INACTIVO);
	}

	@Test
	void obtenerEmpleadoInexistenteLanzaExcepcion() {
		when(empleadoRepository.buscarPorId(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> empleadoService.obtenerPorId(99L))
				.isInstanceOf(EmpleadoNoEncontradoException.class)
				.hasMessageContaining("99");
	}
}
