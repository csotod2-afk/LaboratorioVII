package com.laboratorio.profesores.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.dto.ProfesorResponse;
import com.laboratorio.profesores.exception.ProfesorNoEncontradoException;
import com.laboratorio.profesores.model.Estado;
import com.laboratorio.profesores.model.Profesor;
import com.laboratorio.profesores.repository.ProfesorRepository;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

	@Mock
	private ProfesorRepository profesorRepository;

	@InjectMocks
	private ProfesorService profesorService;

	@Test
	void crearProfesorAsignaIdYEstadoActivoPorDefecto() {
		ProfesorRequest request = new ProfesorRequest("Ana", "López", "ana@correo.com", "Matemática", null);
		when(profesorRepository.guardar(any(Profesor.class))).thenAnswer(invocacion -> {
			Profesor profesor = invocacion.getArgument(0);
			profesor.setId(1L);
			return profesor;
		});

		ProfesorResponse creado = profesorService.crear(request);

		assertEquals(1L, creado.id());
		assertEquals("Ana", creado.nombre());
		assertEquals(Estado.ACTIVO, creado.estado());
		verify(profesorRepository).guardar(any(Profesor.class));
	}

	@Test
	void obtenerProfesorExistenteRetornaSusDatos() {
		Profesor profesor = new Profesor(1L, "Ana", "López", "ana@correo.com", "Matemática", Estado.ACTIVO);
		when(profesorRepository.buscarPorId(1L)).thenReturn(Optional.of(profesor));

		ProfesorResponse encontrado = profesorService.obtenerPorId(1L);

		assertEquals(1L, encontrado.id());
		assertEquals("ana@correo.com", encontrado.correo());
	}

	@Test
	void obtenerProfesorInexistenteLanzaExcepcion() {
		when(profesorRepository.buscarPorId(99L)).thenReturn(Optional.empty());

		assertThrows(ProfesorNoEncontradoException.class, () -> profesorService.obtenerPorId(99L));
	}
}
