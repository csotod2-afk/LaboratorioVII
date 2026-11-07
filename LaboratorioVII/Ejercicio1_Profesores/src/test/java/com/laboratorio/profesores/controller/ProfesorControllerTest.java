package com.laboratorio.profesores.controller;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "app.api-key=clave-de-prueba")
@AutoConfigureMockMvc
class ProfesorControllerTest {

	private static final String CABECERA = "X-API-KEY";
	private static final String CLAVE = "clave-de-prueba";
	private static final String RUTA = "/api/profesores";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void sinApiKeyRetorna401() throws Exception {
		mockMvc.perform(get(RUTA)).andExpect(status().isUnauthorized());
	}

	@Test
	void conApiKeyIncorrectaRetorna401() throws Exception {
		mockMvc.perform(get(RUTA).header(CABECERA, "clave-incorrecta")).andExpect(status().isUnauthorized());
	}

	@Test
	void crearProfesorValidoRetorna201() throws Exception {
		String cuerpo = """
				{"nombre":"Ana","apellido":"Lopez","correo":"ana@correo.com","especialidad":"Matematica"}""";

		mockMvc.perform(post(RUTA).header(CABECERA, CLAVE).contentType(MediaType.APPLICATION_JSON).content(cuerpo))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", startsWith(RUTA + "/")))
				.andExpect(jsonPath("$.estado").value("ACTIVO"));
	}

	@Test
	void crearProfesorConCorreoInvalidoRetorna400() throws Exception {
		String cuerpo = """
				{"nombre":"Ana","apellido":"Lopez","correo":"correo-invalido","especialidad":"Matematica"}""";

		mockMvc.perform(post(RUTA).header(CABECERA, CLAVE).contentType(MediaType.APPLICATION_JSON).content(cuerpo))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detalles.correo").exists());
	}

	@Test
	void crearProfesorConNombreVacioRetorna400() throws Exception {
		String cuerpo = """
				{"nombre":"  ","apellido":"Lopez","correo":"ana@correo.com","especialidad":"Matematica"}""";

		mockMvc.perform(post(RUTA).header(CABECERA, CLAVE).contentType(MediaType.APPLICATION_JSON).content(cuerpo))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detalles.nombre").exists());
	}

	@Test
	void consultarProfesorInexistenteRetorna404() throws Exception {
		mockMvc.perform(get(RUTA + "/9999").header(CABECERA, CLAVE)).andExpect(status().isNotFound());
	}
}
